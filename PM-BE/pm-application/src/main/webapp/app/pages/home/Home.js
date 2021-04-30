import AbstractComponent from '../../components/AbstractComponent';
import { importCss } from '../../shared/utils/css-utils';
import store from '../../store/store';
import { getAccount, logout } from '../../store/actions/auth';

export default class Home extends AbstractComponent {
    constructor() {
        super();
        importCss([
            'content/css/style-intro.css'
        ]);
        this.loadPage('home.html');
        this.state = {};
    }
    
    logout() {
        store.dispatch(logout()).then(() => {
            const logout = store.getState().auth.logout;
            let logoutUrl = logout.logoutUrl;
            const redirectUri = `${location.origin}/`;
            
            // if Keycloak, uri has protocol/openid-connect/token
            if (logoutUrl.includes('/protocol')) {
                logoutUrl = logoutUrl + '?redirect_uri=' + redirectUri;
            } else {
                // Okta
                logoutUrl = logoutUrl + '?id_token_hint=' + logout.idToken + '&post_logout_redirect_uri=' + redirectUri;
            }
            
            window.location.href = logoutUrl;
        });
    }
    
    componentDidMount() {
        store.dispatch(getAccount()).then(() => {
            this.setState(store.getState());
        });
    }
    
    componentDidUpdate(prevState, state) {
        const navLinks = document.querySelector('.main-nav li');
        
        if (!state.auth.authenticated) {
            navLinks.innerHTML = `
                <a href="${location.origin}/oauth2/authorization/oidc">Sign In</a>
            `;
        } else {
            navLinks.innerHTML = `
                <a href="${location.origin}/profile">Profile</a>
                <a style="cursor: pointer" id="logout-link">Logout</a>
            `;
            document.querySelector('#logout-link').addEventListener('click', (e) => {
                this.logout();
            });
        }
    }
    
    componentDidUnmount() {
        document.querySelectorAll('.main-nav li a').forEach(elem => elem.remove());
    }
}

window.customElements.define('app-home', Home);
