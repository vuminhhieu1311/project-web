import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { profileMenuStyle } from './profile-menu-style';
import { commonStyles } from '../../../shared/styles/common-styles';
import store from '../../../store/store';
import { logout } from '../../../store/actions/auth';
import '../../../routes/Link';

class ProfileMenu extends MaleficComponent {
    static get properties() {
        return {
            firstName: {type: String},
            lastName: {type: String},
            title: {type: String},
            avtImg: {type: String},
            id: {type: String},
            logout: {type: String}
        };
    }
    
    static get styles() {
        return [profileMenuStyle];
    }
    
    constructor() {
        super();
        this.firstName = '';
        this.title = '';
        this.lastName = '';
        this.avtImg = '';
        this.id = '';
    }
    
    async connectedCallback() {
        super.connectedCallback();
    }
    
    async signOut() {
        await store.dispatch(logout());
        const state = await store.getState();
        this.logout = state.auth.logout;
        const logoutUrl = this.logout.logoutUrl;
        const idToken = this.logout.idToken;
        if (logoutUrl) {
            // if Keycloak, logoutUrl has protocol/openid-connect in it
            window.location.href = logoutUrl.includes('/protocol')
                ? logoutUrl + '?redirect_uri=' + window.location.origin
                : logoutUrl + '?id_token_hint=' + idToken + '&post_logout_redirect_uri=' + window.location.origin;
        }
    }
    
    render() {
        return html`
            ${commonStyles}

            <div class="profile">
                <img src="${this.avtImg}" alt="Avatar">
                <h3>${this.firstName} ${this.lastName}<br> <span>${this.title}</span></h3>
            </div>
            <div class="setting-list">
                <ul>

                    <li>
                        <i class="fas fa-user-circle"></i>
                        <app-link href="profile/${this.id}"><div class="app-link">View Profile</div></app-link>
                    </li>

                    <li>
                        <i class="fas fa-edit"></i>
                        <app-link href="edit-profile/personal"><div class="app-link">Edit Profile</div></app-link>
                    </li>

                    <li>
                    <i class="fas fa-users-cog"></i>
                        <a href="account">Account Settings</a>
                    </li>
                    <li>
                        <i class="fas fa-address-card"></i>
                        <app-link href="my-cv"><div class="app-link">My CV</div></app-link>
                    </li>
                    <li>
                        <i class="fas fa-sign-out-alt"></i>
                        <a style="cursor: pointer" @click="${this.signOut}">Sign Out</a>
                    </li>

                </ul>
            </div>
        `;
    }
}

customElements.define('app-profile-menu', ProfileMenu);
