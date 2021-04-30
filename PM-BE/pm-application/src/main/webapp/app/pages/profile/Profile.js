import AbstractComponent from '../../components/AbstractComponent';
import { importCss } from '../../shared/utils/css-utils';
import store from '../../store/store';
import { getAccount } from '../../store/actions/auth';

export default class Profile extends AbstractComponent {
    constructor() {
        super();
        importCss([
            'https://fonts.googleapis.com/icon?family=Material+Icons',
            'content/css/style_profile.css'
        ]);
        this.loadPage('profile.html');
    }
    
    componentDidMount() {
        let openContactInfo = document.getElementById('contact-info');
        let closeContactInfo = document.getElementById('contact-info-close');
        let overlay = document.getElementById('overlay');
        let contactInfoDiv = document.getElementById('contact-info-div');
        let body = Array.from(document.getElementsByTagName('body'));
        
        openContactInfo.addEventListener('click', () => {
            contactInfoDiv.style.display = 'block';
            body.forEach(ele => {
                ele.style.overflow = 'hidden';
            });
            overlay.style.display = 'block';
        });
        
        closeContactInfo.addEventListener('click', () => {
            contactInfoDiv.style.display = 'none';
            Array.prototype.forEach.call(body, ele => {
                ele.style.overflow = 'auto';
            });
            overlay.style.display = 'none';
        });
        
        store.dispatch(getAccount()).then(() => {
            this.setState(store.getState());
        });
    }
    
    componentDidUpdate(prevState, state) {
        if (state.auth.authenticated) {
            const account = state.auth;
            document.querySelector('#personal-name').innerHTML = '';
            document.querySelector('#personal-address').innerHTML = '';
            document.querySelector('#contact-info').innerHTML = ''
        }
    }
}

window.customElements.define('app-profile', Profile);
