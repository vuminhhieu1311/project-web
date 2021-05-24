import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { editProfileStyle } from './edit-profile-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/footer/SmallFooter';
import '../../components/my-account/MyAccount';
import '../../components/my-account/ChangePass';
import '../../components/Modal/UploadAvatar/UploadAvatar';
import '../../components/Button/Button';

class EditProfile extends MaleficComponent {
    static get properties() {
        return {
            tabShow: {type: Int16Array},
            showModal: {type: Boolean}
        };
    }

    static get styles() {
        return [editProfileStyle];
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.tabShow = 0;
        this.showModal = false;
    }

    showTab(panelIndex) {
        this.tabShow = panelIndex;
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }
    
    closeModal() {
        this.showModal = false;
    }

    render() {
        return html`
            ${commonStyles}
            <app-header></app-header>
            <section class="container">
                <app-upload-avt .show="${this.showModal}" @close-modal="${this.closeModal}"></app-upload-avt>

                <div class="row">
                    <div class="col span-1-of-5 sidemenu">
                        <nav>
                            <a class="tab" @click="${() => this.showTab(0)}" id="${this.tabShow == 0 ? 'tab-active' : ''}">
                                <i class="fas fa-user"></i>
                                <span class="setting-title">Personal Info</span>
                            </a>
                            <a @click="${() => this.showTab(1)}" class="tab" id="${this.tabShow == 1 ? 'tab-active' : ''}">
                                <i class="fas fa-lock"></i>
                                <span class="setting-title">Change Password</span>
                            </a>
                            <a @click="${() => this.showTab(2)}" class="tab" id="${this.tabShow == 2 ? 'tab-active' : ''}">
                                <i class="far fa-address-card"></i>
                                <span class="setting-title">My CV</span>
                            </a>
                            <a @click="${() => this.showTab(3)}" class="tab" id="${this.tabShow == 3 ? 'tab-active' : ''}">
                                <i class="fas fa-receipt"></i>
                                <span class="setting-title">My Application</span>
                            </a>
                        </nav>
                    </div>

                    <div class="col span-3-of-5 account-info">
                        <div class="profile tab-show" id="${this.tabShow == 0 ? 'tab-show-active' : ''}">
                            <my-account></my-account>
                        </div>
        
                        <div class="profile tab-show" id="${this.tabShow == 1 ? 'tab-show-active' : ''}">
                            <change-pass></change-pass>
                        </div>
        
                        <div class="profile tab-show" id="${this.tabShow == 2 ? 'tab-show-active' : ''}">
                            <h1>My CV</h1>
                            <h3>Fill these information to make your own CV</h3>
                        </div>
        
                        <div class="profile tab-show" id="${this.tabShow == 3 ? 'tab-show-active' : ''}">
                            <h1>My Application Forms</h1>
                            <h3>You have 0 application forms</h3>
                        </div>
                    </div>
                    
                    <div class="col span-1-of-5 avt-image">
                        <div id="main-avatar">
                            <img src="content/images/avatar.png">
                        </div>
                        <div>
                            <button class="btn-upload" @click="${this.handleToggleModal}">
                                <i class="fas fa-camera"></i>
                                Select Image
                            </button>
                        </div>
                    </div>
                </div>
            </section>
            <app-small-footer></app-small-footer>
        `;
    }
}

customElements.define('app-edit-profile', EditProfile);
