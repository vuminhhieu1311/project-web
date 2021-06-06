import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { updateCompanyStyle } from './update-company-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import getCompany from "../../api/getPublicCompany";
import {withRouter} from "../../core/router/malefic-router";
import patchCompany from "../../api/patchCompany";
import '../../components/Alert/AlertSuccess';
import '../../components/Alert/AlertFail';
import '../../components/Modal/UploadCompanyBackground/UploadCompanyBackground';
import global from '../../components/global';
import '../../components/Modal/UploadLogo/UploadLogo';

class UpdateCompany extends withRouter(MaleficComponent){

    static get properties(){
        return{
            tabShow: { type: Int16Array },
            showLogoModal: { type: Boolean },
            formData: { type: FormData},
            company: {type: JSON},
            showModalAvt: { type: Boolean },
            bgImage: { type: String},
            logoImage: {type: String},
        }
    }

    static get styles(){
        return [updateCompanyStyle];
    }

    constructor(){
        super();
        this.showModalAvt = false;
        global.changeBgImage = this.changeBgImage.bind(this);
        global.changeLogoImage = this.changeLogoImage.bind(this);
    }

    handleToggleModal() {
        this.showModalAvt = !this.showModalAvt;
    }

    closeModal() {
        this.showModalAvt = false;
        this.showLogoModal = false;
    }

    handleToggleLogoModal() {
        this.showLogoModal = !this.showLogoModal;
    }

    handleReviewName(){
        console.log("test");
        let reviewName = this.shadowRoot.querySelector("#company-info h1");
        let name = this.shadowRoot.getElementById("name");
        reviewName.innerHTML=name.value;
    }

    handleReviewSize(){
        let reviewSize = this.shadowRoot.querySelector("#company-info a");
        let size = this.shadowRoot.getElementById("companySize");
        reviewSize.innerHTML = size.value + " Employees";
    }

    handleReviewLogo(){
        let reviewLogo = this.shadowRoot.querySelector("#main-avatar img");
        let avatar = this.shadowRoot.getElementById("logo");

        let img = avatar.files[0];
        reviewLogo.src = URL.createObjectURL(img);
    }

    handleReviewBackground(){
        let reviewBackground = this.shadowRoot.querySelector("#background-avatar img");
        let background = this.shadowRoot.getElementById("background");

        let img = background.files[0];
        reviewBackground.src = URL.createObjectURL(img);
    }

    changeBgImage = (image) => {
        this.bgImage = image;
    }

    changeLogoImage = (image) => {
        this.logoImage = image;
    }

    connectedCallback() {
        super.connectedCallback();
        getCompany(this.params.id).then(res => {
            this.company = res;
            this.logoImage = res.logoUrl;
            this.bgImage = res.bgImageUrl;
        })
            .catch(e => console.log(e));
    }

    submitForm() {
        let companyForm = this.shadowRoot.getElementById("main__basic__info__form");
        companyForm.addEventListener("submit", (e) => e.preventDefault());
        this.formData = new FormData(companyForm);
        this.formData.append("bgImageUrl", this.bgImage);
        this.formData.append("logoUrl", this.logoImage);

        // Convert formData to a query string
        const data = [...this.formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');

        patchCompany(this.params.id, asString)
            .then(data => {
                if(data) {
                    console.log(data)
                    const alertBox = this.shadowRoot.querySelector('.show-alert');
                    alertBox.classList.add('active');
                    setTimeout(function () {
                        alertBox.classList.remove('active')
                    }, 2000);
                }
            })
            .catch(() => {
                const alertBox = this.shadowRoot.querySelector('.show-alert-fail');
                alertBox.classList.add('active');
                setTimeout(function () {
                    alertBox.classList.remove('active')
                }, 2000);
            });
    }

    render(){
        return html`
            ${commonStyles}

            <app-header></app-header>
            <app-upload-company-background .show="${this.showModalAvt}" @close-modal="${this.closeModal}" id=${this.company.id}></app-upload-company-background>
            <app-upload-logo .show="${this.showLogoModal}" @close-modal="${this.closeModal}" id=${this.company.id}></app-upload-logo>
            <main>
                <div id="main__left">
                    <div id="main__basic__info">
                        <h2>Change your company information</h2>
                        <form id="main__basic__info__form">
                            <label for="name" >Name *</label></br>
                            <input type="text" id="name" name="name" value="${this.company["name"]}"
                                @keyup=${this.handleReviewName} required></br>
                            
                            <label for="website">Website</label></br>
                            <input type="url" id="website" name="website" value="${this.company["website"]}"></br>
                            
                            <label for="companySize">Company size</label></br>
                            <input type="text" id="companySize" name="companySize" value="${this.company["companySize"]}"
                                @keyup=${this.handleReviewSize}></br>
                            
                            <label for="companyType">Company type *</label></br>
                            <div class="dob" data-="selectors">
                                <select class="selector" aria-label="Company Type" value="${this.company["companyType"]}" id="companyType" name="companyType" required>
                                    <option value="GOVERNMENT_AGENCY">Government agency</option>
                                    <option value="PUBLIC_COMPANY">Public company</option>
                                    <option value="SELF_EMPLOYED">Self-employed</option>
                                    <option value="PARTNERSHIP">Partnership</option>
                                    <option value="PRIVATE_HELD">Privately held</option>
                                </select>
                            </div>
 
                            <label for="industry">Industry</label></br>
                            <input type="text" id="industry" name="industry" value="${this.company["industry"]}"></br>
                            
                            <label for="tagLine">Tag line </label></br>
                            <input type="text" id="tagline" name="tagline" value="${this.company.tagline}"></br> 
                            <div class="upload-btn">
                                <div class="custom-btn" @click="${this.handleToggleLogoModal}"><i class="fas fa-cloud-upload-alt"></i>Upload Logo</div>
                                <div class="custom-btn" @click="${this.handleToggleModal}"><i class="fas fa-cloud-upload-alt"></i>Upload Cover Photo</div>
                            </div>
                            
                            <div class="update-button">
                            <button class="btn-save" @click="${this.submitForm}">Update</button>
                            <button type="reset" class="btn-cancel">Cancel</button>
                            </div>          
                        </form>
                        <div class="show-alert">
                        <app-alert-success></app-alert-success>
                    </div>
                    <div class="show-alert-fail">
                        <app-alert-fail></app-alert-fail>
                    </div>
                        <div id="filter__jobtitle">
                        </div>
                    </div>
                </div>

                <div id="main__right">
                    <div id="review">Review</div>
                    <div class="main-content-div" id="basic-info-div">
                        <div id="background-avatar">
                            <img src="${this.bgImage? this.bgImage: './content/images/4853433.jpg'}" style="height: 200px;width: 100%;">
                        </div>

                        <div id="main-avatar">
                            <img src="${this.logoImage? this.logoImage: './content/images/4853433.jpg'}" style="height: 90px;width: 90px;">
                        </div>

                        <div id="info">
                            <div id="company-info">
                                <h1>Name</h1>
                                <p>Slogan</p>
                                <span>Address</span>
                                <span>Followers</span>
                                <span>Employees</span>
                            </div>
                        </div>

                        <div id="basic-info-follow">
                            <i class="fas fa-plus"></i>Follow
                        </div>

                        <div id="basic-info-nav">
                            <div><a>Home</a></div>
                            <div><a>About</a></div>
                            <div><a>Posts</a></div>
                        </div>
                    </div>
                </div>
            </main>
            
        `;
    }
}

customElements.define("app-update-company", UpdateCompany);
