import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { UploadLogoStyle } from './upload-logo-style';

import '../Modal';
import { commonStyles } from '../../../shared/styles/common-styles';
import patchPersonalProfile from '../../../api/patchPersonalProfile';
import postCompanyLogo from '../../../api/postCompanyLogo';
import global from '../../global';
import patchCompany from '../../../api/patchCompany';

class UploadLogo extends MaleficComponent {
    static get properties() {
        return {
            show: { type: Boolean },
            id: {type: Int16Array},
        };
    }

    static get styles() {
        return [UploadLogoStyle];
    }

    constructor() {
        super();
        this.show = false;
        this.id = 0;
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
        });
    }

    reloadImage() {
        const img = this.shadowRoot.querySelector(".image img");
        img.src = "";
    }

    saveImage() {
        const uploadForm = this.shadowRoot.querySelector("#uploadForm");
        uploadForm.addEventListener("submit", (e) => e.preventDefault());
        const uploadFile = this.shadowRoot.querySelector(".uploadFile");
        var formData = new FormData();
        formData.append("logo", uploadFile.files[0]);

        // show spinner while loading
        const spinner = this.shadowRoot.querySelector('.sk-chase');
        const avtWrapper = this.shadowRoot.querySelector('.wrapper');
        spinner.classList.add('showSpinner');
        avtWrapper.classList.add('loading');

        postCompanyLogo(this.id, formData)
            .then(res => {
                spinner.classList.remove('showSpinner');
                avtWrapper.classList.remove('loading');
                this.show= false;
                global.changeLogoImage(res.secure_url);
                    
            })
            .catch(e => console.log(e));
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
                    <form class="uploadForm" id="uploadForm">
                        <input type="file" id="default-btn" name="uploadFile" class="uploadFile" hidden>
                        <div @click="${this.clickDefaultUpload}" class="custom-btn">CHOOSE A FILE</div>
                        <button type="reset" class="cancel-btn" @click="${this.reloadImage}">CANCEL</button>
                        <button type="submit" @click="${() => { this.saveImage() }}" class="save-btn">SAVE</button>
                    </form>
                    
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

customElements.define('app-upload-logo', UploadLogo);


