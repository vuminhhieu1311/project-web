import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import {NetworkStyle } from '../../pages/network/network-style';


import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/nameCard_Contact/nameCard_Contact';


class NetWork extends MaleficComponent{
    static get properties(){
        return{
            
        }
    }

    static get styles(){
        return [NetworkStyle];
    }

    
    constructor(){
        super();
    }

    render(){
        return html`
            ${commonStyles}

            <app-header></app-header>
            <main>
            <div id="main__left">
                <div id="main__left__title">Contact</div>
                <div id="friends__list">
                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>

                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>

                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>

                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>

                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>

                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>

                    <app-name-card
                        image="../content/images/HUST_logo.png"
                        name="Friend"
                    ></app-name-card>
                </div>
            </div>
        
            <div id="main__right">
                <div id="invitation">
                    
                </div>
        
                <div id="add__contact">
                    <div id="add__contact__header">
                        <img src="../content/images/agenda.svg" height="60px" width="60px">
                        <h3>Add personal contacts</h3>
                    </div>
                    <form id="add__contact__info">
                        <label>We’ll periodically import and store your 
                            contacts to help you and others connect. You choose who to connect 
                            to and who to invite.</label></br>
        
                        <input type="email" id="contact__email" name="contact__email">
                        <button>Continue</button>
                    </form>
                </div>
            </div>
        </main>
        
        `;
    }
}

customElements.define('app-network', NetWork);