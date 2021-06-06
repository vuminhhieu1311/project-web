import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { commonStyles } from '../../../shared/styles/common-styles';
import { headerStyle } from './header-style';

import '../../Dropdown/Dropdown';
import '../../Modal/SearchBar/SearchBar';
import '../../Dropdown/ProfileMenu/ProfileMenu';
import getProfile from '../../../api/getProfile';
import '../../Dropdown/CompanyMenu/CompanyMenu';
import { transformImage } from '../../../shared/utils/url-utils';
import '../../../routes/Link';

class Header extends MaleficComponent {
    static get properties() {
        return {
            showModal: {type: Boolean},
            profile: {type: Object},
            imgAvt: {type: String}
        };
    }
    
    static get styles() {
        return [headerStyle];
    }
    
    constructor() {
        super();
        this.showModal = false;
    }
    
    handleToggleModal() {
        this.showModal = !this.showModal;
    }
    
    closeModal() {
        this.showModal = false;
    }

    async connectedCallback() {
        super.connectedCallback()
        getProfile()
        .then(res => {
            this.profile = res;
            this.imgAvt = res.user.imageUrl;
        })
        .catch(e => console.log(e));
    }

    render() {
        return html`
            ${commonStyles}
            <header id="header">
                <div id="left-header">
                    <a href="/"><img class="logo" src="content/images/Logo_official.png" alt="Logo" /></a>
                </div>
                
                <app-searchbar .show="${this.showModal}" @close-modal="${this.closeModal}"></app-searchbar>
                <div id="right-header">
                    <nav id="nav-menu">
                        <div class="menu-icons" @click="${this.handleToggleModal}">
                            <div class="material-icons md-24">
                                search
                            </div>
                            <h6>Search</h6>
                        </div>
            
                        <div class="menu-icons">
                            <app-link href="/">
                                <div class="material-icons md-24">
                                    home
                                </div>
                                <h6>Home</h6>
                            </app-link>
                        </div>

                        <div class="menu-icons">
                            <app-link>
                                <div class="material-icons md-24">
                                    work
                                </div>
                                <h6>Jobs</h6>
                            </app-link>
                        </div>
                        <div class="menu-icons">
                            <app-link href="/chat">
                                <div class="material-icons md-24">
                                    message
                                </div>
                                <h6>Messaging</h6>
                            </app-link>
                        </div>

                        <app-dropdown>
                            <div class="menu-icons" slot="toggle">
                                <div class="material-icons md-24">
                                <i class="fas fa-building"></i>
                                </div>
                                <div>
                                    <h6>Company <i class="fas fa-caret-down"></i></h6>
                                </div>
                            </div>
                            ${this.profile && this.profile.user ?
                                html`<app-company-menu
                                    avtImg="${this.imgAvt ? transformImage(this.imgAvt, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35') : 'content/images/avatar.png'}"
                                    firstName="${this.profile.user.firstName}"
                                    lastName="${this.profile.user.lastName}"
                                    title="${this.profile.headline}"
                                    id="${this.profile.userId}"
                                    slot="menu-item">
                                </app-company-menu>` : null
                            }
                        </app-dropdown>
                        
                        <app-dropdown>
                            <div class="menu-icons" slot="toggle">
                                <div class="profile">
                                    ${this.imgAvt ?
                                        html`
                                            <img src="${transformImage(this.imgAvt, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35')}" alt="Avatar">
                                        ` : html`<img src="content/images/avatar.png">`
                                    }
                                </div>
                                <div>
                                    <h6>Me <i class="fas fa-caret-down"></i></h6>
                                </div>
                            </div>
                            ${this.profile && this.profile.user ?
                                html`
                                    <app-profile-menu
                                        avtImg="${this.imgAvt ? transformImage(this.imgAvt, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35') : 'content/images/avatar.png'}"
                                        firstName="${this.profile.user.firstName}"
                                        lastName="${this.profile.user.lastName}"
                                        title="${this.profile.headline}"
                                        id="${this.profile.userId}"
                                        slot="menu-item">
                                    </app-profile-menu>
                                ` : null
                            }
                        </app-dropdown>
                    </nav>
                </div>
            </header>
        `;
    }
}

customElements.define('app-header', Header);
