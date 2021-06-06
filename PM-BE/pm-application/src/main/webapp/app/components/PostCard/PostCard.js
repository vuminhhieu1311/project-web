import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { postCardStyle } from './post-card-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../PostCard/commentCard';
import getAttachment from '../../api/getAttachment';
import '../../routes/Link';

class PostCard extends MaleficComponent {
    static get styles() {
        return [postCardStyle];
    }

    static get properties() {
        return {
            accountImg: {type: String},
            accountName: {type: String},
            numFollowers: {type: Int32Array},
            time: {type: Date},
            postText: {type: String},
            postId: {type: Int16Array},
            showComment:{type:String},
            showEdit: {type: Boolean},
            showDropdownEdit: {type: String},
            attachment: {type: Object}
        };
    }

    handleToggleShowComment(){
        this.showComment = (this.showComment=="block")?"none":"block";
    }

    handleToggleEdit(){
        this.showEdit = !this.showEdit;
        this.placeHolderImage = (this.postImg==undefined)?"./content/images/avatar.png":this.postImg;
    }

    handleToggleDropdown(){
        //let dropdownIcon = this.shadowRoot.querySelector(".edit-icon");
        //dropdownIcon.stopPropagation();
        this.showDropdownEdit = (this.showDropdownEdit=="none")?"block":"none";
        /*window.addEventListener("click",()=>{
            if(this.showDropdownEdit=="block") this.showDropdownEdit="none";
            console.log("test");
        })*/
    }

    closeEdit(){
        this.showEdit=false;
    }

    handleDeletePost(){
        this.showPost="none";
    }

    connectedCallback() {
        super.connectedCallback();
        getAttachment(this.postId)
        .then(res => {
            if(res._embedded) {
                this.attachment = res._embedded.attachmentList[0]
            }
        })
        .catch( e => console.log(e));
    }

    constructor() {
        super();
        this.showDropdownEdit="none";
        this.showPost="block";
        this.attachment={};  
    }

    render() {
        return html`
            ${commonStyles}

        <div style="display:${this.showPost};">
            <div class="news-card">
                <div class="news-header">
                    <div class="poster">
                        <img src="${this.accountImg}" alt="">
                        <div class="poster-info">
                            <div style="font-weight: bold; font-size: 18px;">${this.accountName}</div>
                            <div style="font-size: 12px;">${this.numFollowers} followers</div>
                            <div style="font-size: 12px;">${this.time}</div>
                        </div>
                    </div>

                    <div class="edit" >
                        <div class="edit-icon" @click="${this.handleToggleDropdown}">
                            <i class="fas fa-ellipsis-h"></i>
                        </div>

                        <div id="dropdown-edit" style="display:${this.showDropdownEdit}">
                            <div id="edit-post" @click="${this.handleToggleEdit}">Edit post</div>
                            <div id="delete-post" @click="${this.handleDeletePost}">Delete post</div>
                        </div>
                    </div>

                    <app-upload-post
                        typePost="Edit your post"
                        editText="${this.postText}"
                        .show="${this.showEdit}"
                        @close-modal="${this.closeEdit}"
                        placeHolderImage=${this.placeHolderImage}>
                    </app-upload-post>
                </div>

                <div class="recruit-info">
                    <div>${this.postText}</div>
                    <img src="${this.attachment.thumbUrl}" alt="" style="display: ${this.attachment.thumbUrl==undefined?'none':'block'}">
                </div>

                <div class="news-react">
                    <div class="react">
                        <button class="react-icon"><i class="fas fa-thumbs-up"></i>Like</button>
                        <button class="react-icon" @click="${this.handleToggleShowComment}"><i class="fas fa-comment-dots"></i>Comment</button>
                    </div>
                </div>

                <div class="comment" id="comment" style="display:${this.showComment};">
                    <comment-card></comment-card>
                    <form>
                        <input type="text">
                    </form>
                </div>
            </div>
        </div>
        `;
    }
}

customElements.define('post-card', PostCard);
