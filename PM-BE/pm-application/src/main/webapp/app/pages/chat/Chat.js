import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { chatStyle } from './chat-style';
import { RSocketClient, JsonSerializer, IdentitySerializer } from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';
import store from '../../store/store';
import { repeat } from '../../core/components/directives/repeat';

import '../../components/layouts/Header/Header';
import { getConversations } from '../../store/actions/conversations';
import { getParticipantsData } from '../../api/participants';
import { getMessageHistoryData } from '../../api/messages';
import { transformImage } from '../../shared/utils/url-utils';

class Chat extends MaleficComponent {
    static get properties() {
        return {
            messages: {type: Array},
            textInput: {type: String},
            rsocket: {type: Object},
            toggleMenu: {type: Boolean},
            activeUser: {type: Object},
            participants: {type: Array},
            conversationId: {type: Number}
        };
    }
    
    static get styles() {
        return [chatStyle];
    }
    
    constructor() {
        super();
        this.auth = {};
        this.conversations = [];
        this.paging = {};
        this.participants = [];
        this.messages = [];
        this.textInput = '';
        this.conversationId = '';
        this.toggleMenu = false;
    }
    
    async connectedCallback() {
        super.connectedCallback();
        await store.dispatch(getConversations());
        const state = await store.getState();
        this.auth = state.auth;
        this.conversations = state.conversations._embedded.conversationList;
        this.paging = state.conversations.page;
        
        for (const conversation of this.conversations) {
            const body = await getParticipantsData(conversation.id);
            this.participants = [
                ...this.participants,
                {
                    conversationId: conversation.id,
                    user: body._embedded.users.filter(p => p.id !== this.auth.userId)[0]
                }
            ];
        }
    }
    
    disconnectedCallback() {
        super.disconnectedCallback();
        if (this.client) {
            this.client.close();
        }
    }
    
    sendTo(route) {
        return String.fromCharCode(route.length) + route;
    }
    
    addMessage(newMessage) {
        console.log('add message:' + JSON.stringify(newMessage))
        this.messages = [...this.messages, newMessage];
    }
    
    sendMessage() {
        if (this.rsocket && this.textInput !== '') {
            const newMessage = {
                senderId: this.auth.userId,
                content: this.textInput
            };
            this.rsocket.fireAndForget({
                data: newMessage,
                metadata: this.sendTo(`channels.${this.conversationId}`)
            });
            this.textInput = '';
        }
    }
    
    handleOnChangeInput(e) {
        this.textInput = e.target.value;
    }
    
    checkSenderIsMe(userId) {
        return this.auth.userId === userId;
    }
    
    toggleActionMenu() {
        this.toggleMenu = !this.toggleMenu;
    }
    
    async handleSelect(conversationId, activeUser) {
        this.conversationId = conversationId;
        this.activeUser = activeUser;
        const msgList = await getMessageHistoryData(conversationId);
        if (msgList._embedded) {
            this.messages = [
                ...this.messages,
                ...msgList._embedded.messageList
            ];
        }
        
        
        this.client = new RSocketClient({
            serializers: {
                data: JsonSerializer,
                metadata: IdentitySerializer
            },
            setup: {
                payload: {
                    data: this.auth.userId,
                    metadata: this.sendTo('user-id')
                },
                keepAlive: 60000,
                lifetime: 180000,
                dataMimeType: 'application/json',
                metadataMimeType: 'message/x.rsocket.routing.v0',
            },
            transport: new RSocketWebSocketClient({
                url: 'ws://localhost:8000/rsocket'
            }),
        });
        
        this.client.connect().subscribe({
            onComplete: (socket) => {
                this.rsocket = socket;
                this.rsocket
                    .requestStream({
                        data: null,
                        metadata: this.sendTo(`channels.${this.conversationId}`)
                    })
                    .subscribe({
                        onComplete: (data) => {
                            console.log(data);
                            console.log('complete');
                        },
                        onError: (error) => {
                            console.log('Connection has been closed due to:: ' + error);
                        },
                        onNext: (payload) => {
                            console.log(payload);
                            this.addMessage(payload.data);
                        },
                        onSubscribe: (subscription) => {
                            subscription.request(0x7fffffff);
                        },
                    });
            },
            onError: (error) => {
                console.log('Connection has been refused due to:: ' + error);
            },
            onSubscribe: (cancel) => {
            
            }
        });
    }
    
    render() {
        return html`
            <style>
                @import url('https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css');
            </style>
            ${commonStyles}
            
            <app-header></app-header>
            
            <div class="container-fluid h-100" style="margin-top: 100px">
                <div class="row justify-content-center h-100">
                    <div class="col-md-4 col-xl-3 chat">
                        <div class="card mb-sm-3 mb-md-0 contacts_card">
                            <div class="card-header">
                                <div class="input-group">
                                    <input type="text" placeholder="Search..." name="" class="form-control search">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text search_btn"><i class="fas fa-search"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body contacts_body">
                                <ui class="contacts">
                                    ${repeat(this.participants, p => p.conversationId, (p, index) =>
                                        html`
                                            <li class="${p.conversationId === this.conversationId ? 'active' : ''}"
                                                @click=${() => this.handleSelect(p.conversationId, p.user)}
                                                style="cursor: pointer"
                                            >
                                                <div class="d-flex bd-highlight">
                                                    <div class="img_cont">
                                                        ${p.user.avatarUrl ?
                                                            html`<img
                                                                src=${transformImage(p.user.avatarUrl, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35')}
                                                                class="rounded-circle user_img">`
                                                            : html`<img src="content/images/avatar.png"
                                                                        class="rounded-circle user_img"/>`
                                                        }
                                                    </div>
                                                    <div class="user_info">
                                                        ${p.user.firstName || p.user.lastName ?
                                                            html`
                                                                <span>${p.user.firstName + ' ' + p.user.lastName}</span>
                                                            ` : null
                                                        }
                                                    </div>
                                                </div>
                                            </li>
                                        `
                                    )}
                                </ui>
                            </div>
                            <div class="card-footer"></div>
                        </div>
                    </div>
                    <div class="col-md-8 col-xl-6 chat">
                        ${this.activeUser ?
                            html`
                                <div class="card">
                                    <div class="card-header msg_head">
                                        <div class="d-flex bd-highlight">
                                            <div class="img_cont">
                                                ${this.activeUser.avatarUrl ?
                                                    html`
                                                        <img
                                                            src=${transformImage(this.activeUser.avatarUrl, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35')}
                                                            class="rounded-circle user_img">
                                                    ` : html`<img src="content/images/avatar.png"
                                                                  class="rounded-circle user_img" alt=""/>`
                                                }
                                            
                                            </div>
                                            <div class="user_info">
                                                <span>Chat with ${this.activeUser.firstName + ' ' + this.activeUser.lastName}</span>
                                            </div>
                                        </div>
                                        <span id="action_menu_btn" @click=${this.toggleActionMenu}><i
                                            class="fas fa-ellipsis-v"></i></span>
                                        ${this.toggleMenu ?
                                            html`
                                                <div class="action_menu">
                                                    <ul>
                                                        <li><i class="fas fa-user-circle"></i> View profile</li>
                                                        <li><i class="fas fa-plus"></i> Add to group</li>
                                                        <li><i class="fas fa-ban"></i> Block</li>
                                                    </ul>
                                                </div>
                                            ` : null
                                        }
                                    </div>
                                    <div class="card-body msg_card_body">
                                        ${this.messages.length > 0 ?
                                            html`
                                                ${repeat(this.messages, (m) => m.id, (m, index) => html`
                                                    ${!this.checkSenderIsMe(m.senderId) && this.activeUser ?
                                                        html`
                                                            <div class="d-flex justify-content-start mb-4">
                                                                <div class="img_cont_msg">
                                                                    ${this.activeUser.avatarUrl ?
                                                                        html`
                                                                            <img
                                                                                src="${transformImage(this.activeUser.avatarUrl, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35')}"
                                                                                class="rounded-circle user_img_msg">
                                                                        ` : html`<img src="content/images/avatar.png"
                                                                                      alt=""
                                                                                      class="rounded-circle user_img_msg">`
                                                                    }
                                                                </div>
                                                                <div class="msg_container">
                                                                    ${m.content}
                                                                    <span class="msg_time">8:40 AM, Today</span>
                                                                </div>
                                                            </div>
                                                        ` :
                                                        html`
                                                            <div class="d-flex justify-content-end mb-4">
                                                                <div class="msg_container_send">
                                                                    ${m.content}
                                                                    <span class="msg_time_send">8:55 AM, Today</span>
                                                                </div>
                                                            </div>
                                                        `
                                                    }
                                                `)}
                                            ` : null
                                        }
                                    </div>
                                    <div class="card-footer">
                                        <div class="input-group">
                                    <textarea
                                        id="msg-input"
                                        name="" class="form-control type_msg"
                                        placeholder="Type your message..."
                                        @input=${this.handleOnChangeInput}
                                        .value=${this.textInput}
                                    ></textarea>
                                            <div class="input-group-append">
                                        <span class="input-group-text send_btn" @click=${this.sendMessage}>
                                            <i class="fas fa-location-arrow"></i>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ` : null
                        }
                    
                    </div>
                </div>
            </div>
        `;
    }
}

customElements.define('app-chat', Chat);
