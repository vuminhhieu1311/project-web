import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { uploadPostStyle } from './upload-post-style';
import { commonStyles } from '../../../shared/styles/common-styles';

import '../Modal';
import '../../Button/Button';
import postUserPost from '../../../api/postUserPost';

class UploadPost extends MaleficComponent {
    static get properties() {
        return {
            show: { type: Boolean },
            typePost: {type: String},
            placeHolder: {type:  String},
            editText: {type:String},
            placeHolderImage: {type: String}
        };
    }

    static get styles() {
        return [uploadPostStyle];
    }

    handleCloseModal() {
        this.show = false;
        const event = new CustomEvent('close-modal', {
            detail: {},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(event);
    }

    clickDefaultUpload() {
        const defaultBtn = this.shadowRoot.querySelector('#default-btn');
        const img = this.shadowRoot.querySelector(".image img");
        let regExp = /[0-9a-zA-Z\^\&\'\@\{\}\[\]\,\$\=\!\-\#\(\)\.\%\+\~\_ ]+$/;
        defaultBtn.click();
        defaultBtn.addEventListener("change", function () {
            const file = this.files[0];
            this.placeHolderImage = URL.createObjectURL(file);
            if (file) {
                const reader = new FileReader();
                reader.onload = function () {
                    const result = reader.result;
                    img.src = result;
                }
                reader.readAsDataURL(file);
            }
            if (this.value) {
                let valueStore = this.value.match(regExp);
                console.log(valueStore);
            }
        });
    }

    reloadImage() {
        const img = this.shadowRoot.querySelector(".image img");
        img.src = "";
        this.placeHolderImage="";
    }

    saveImage() {
        const uploadForm = this.shadowRoot.querySelector("#post-form");
        uploadForm.addEventListener("submit", (e) => e.preventDefault());
        const uploadFile = this.shadowRoot.querySelector(".uploadFile");
        var formData = new FormData();
        formData.append("image", uploadFile.files[0]);
        formData.append('postDTO', new Blob([JSON.stringify({
            "content": this.shadowRoot.getElementById("content").value,
            "visionable": "PUBLIC",
        })], {
                type: "application/json"
            }));

        // show spinner while loading
        const spinner = this.shadowRoot.querySelector('.sk-chase');
        const avtWrapper = this.shadowRoot.querySelector('.wrapper');
        spinner.classList.add('showSpinner');
        avtWrapper.classList.add('loading');

        postUserPost(formData)
            .then(res => {
                spinner.classList.remove('showSpinner');
                avtWrapper.classList.remove('loading');
                console.log(res);
                this.show = false;
            })
            .catch(e => console.log(e));
    }

    render() {
        return html`
            ${commonStyles}
            <app-modal .show="${this.show}">
                <div class="avt-modal" id="avt-modal">
                    <div class="post__edit__header">
                        <h3>${this.typePost}</h3>
                        <div class="post__edit__close" @click=${this.handleCloseModal}><i class="fas fa-times"></i></div>
                    </div>
                    <form id="post-form">
                        <div class="post__edit__text">
                            <textarea name="content" id="content" placeholder="${this.placeHolder}">${this.editText}</textarea>
                        </div>
                        <div class="wrapper">
                            <div class="image" 
                                style="display:${this.placeHolderImage==""?'none':'block'}">
                                <img src="${this.placeHolderImage}" alt="">
                            </div>
                            <div class="content">
                                <div class="icon"><i class="fas fa-cloud-upload-alt"></i></div>
                                <div class="text">No file chosen</div>
                            </div>
                        </div>
                        <input type="file" id="default-btn" class="uploadFile" name="uploadFile" hidden>
                        <div class="button">
                            <div class="custom-btn" @click="${this.clickDefaultUpload}">
                                <i class="fas fa-images"></i>
                                Image
                            </div>
                            <button type="reset" class="cancel-btn" @click="${this.reloadImage}">
                                <i class="fas fa-undo"></i>Cancel
                            </button>
                            <button type="submit" @click="${() => { this.saveImage(); }}" class="custom-btn">
                                <i class="fas fa-forward"></i>Post
                            </button>
                        </div>
                    
                        <div class="sk-chase">
                            <div class="sk-chase-dot"></div>
                            <div class="sk-chase-dot"></div>
                            <div class="sk-chase-dot"></div>
                            <div class="sk-chase-dot"></div>
                            <div class="sk-chase-dot"></div>
                            <div class="sk-chase-dot"></div>
                        </div>
                    </form>
                   
                </div>   
            </app-modal>
        `;
    }
}

customElements.define('app-upload-post', UploadPost);
