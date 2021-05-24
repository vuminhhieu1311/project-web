import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { UploadAvatarStyle } from './upload-avatar-style';

import '../Modal';
import { commonStyles } from '../../../shared/styles/common-styles';

class UploadAvatar extends MaleficComponent {
    static get properties() {
        return {
            show: { type: Boolean }
        };
    }

    static get styles() {
        return [UploadAvatarStyle];
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
    }

    saveImage() {
        // loading profile image 
        const spinner = this.shadowRoot.querySelector('.sk-chase');
        const avtWrapper = this.shadowRoot.querySelector('.wrapper');
        spinner.classList.add('showSpinner');
        avtWrapper.classList.add('loading');

        // When finish loading
        setTimeout(function () {
            spinner.classList.remove('showSpinner');
            avtWrapper.classList.remove('loading');
        }, 2000);
    }

    render() {
        return html`
            ${commonStyles}
            <app-modal .show="${this.show}">
                <div class="avt-modal" id="avt-modal">
                    <div data-close-button id="close-button" @click="${this.handleCloseModal}"><i class="fas fa-times"></i></div>
                    <div class="wrapper">
                        <div class="image"><img src="content/images/avatar.png" alt=""></div>
                        <div class="content">
                            <div class="icon"><i class="fas fa-cloud-upload-alt"></i></div>
                            <div class="text">No file chosen</div>
                        </div>
                    </div>
                    <input type="file" id="default-btn" hidden>
                    <button @click="${this.clickDefaultUpload}" class="custom-btn">CHOOSE A FILE</button>
                    <button class="cancel-btn" @click="${this.reloadImage}">CANCEL</button>
                    <button @click="${() => {
                this.saveImage();

            }}" class="save-btn">SAVE</button>
                    <div class="sk-chase">
                        <div class="sk-chase-dot"></div>
                        <div class="sk-chase-dot"></div>
                        <div class="sk-chase-dot"></div>
                        <div class="sk-chase-dot"></div>
                        <div class="sk-chase-dot"></div>
                        <div class="sk-chase-dot"></div>
                    </div>
                </div>   
            </app-modal>
        `;
    }
}

customElements.define('app-upload-avt', UploadAvatar);


