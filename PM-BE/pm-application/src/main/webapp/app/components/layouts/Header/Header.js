import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { commonStyles } from '../../../shared/styles/common-styles';
import { headerStyle } from './header-style';

import '../../Dropdown/Dropdown';
import '../../Modal/SearchBar/SearchBar';
import '../../Dropdown/ProfileMenu/ProfileMenu';

class Header extends MaleficComponent {
    static get properties() {
        return {
            showModal: {type: Boolean}
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
                            <div class="material-icons md-24">
                                home
                            </div>
                            <h6>Home</h6>
                        </div>
                        <div class="menu-icons">
                            <div class="material-icons md-24">
                                people
                            </div>
                            <h6>My Network</h6>
                        </div>
                        <div class="menu-icons">
                            <div class="material-icons md-24">
                                work
                            </div>
                            <h6>Jobs</h6>
                        </div>
                        <div class="menu-icons">
                            <div class="material-icons md-24">
                                message
                            </div>
                            <h6>Messaging</h6>
                        </div>
                        <div class="menu-icons">
                            <div class="material-icons md-24">
                                notifications
                            </div>
                            <h6>Notifications</h6>
                        </div>
                        
                        <app-dropdown>
                            <div class="menu-icons" slot="toggle">
                                <div class="profile">
                                    <img src="content/images/avatar.png" alt="Avatar">
                                </div>
                                <div>
                                    <h6>Me <i class="fas fa-caret-down"></i></h6>
                                </div>
                            </div>
                            <app-profile-menu
                                username="admin"
                                title="Student at Hanoi University of Science and Technology"
                                slot="menu-item">
                            </app-profile-menu>
                        </app-dropdown>
                    </nav>
                </div>
            </header>
        `;
    }
}

customElements.define('app-header', Header);
