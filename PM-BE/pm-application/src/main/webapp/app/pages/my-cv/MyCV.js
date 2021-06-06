import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { myCVStyle } from './my-cv-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/Footer/Footer';
import '../../components/Button/Button';
import '../../components/PostCard/PostCard';
import '../../components/Modal/UploadPost/UploadPost';
import getProfile from '../../api/getProfile';

class MyCV extends MaleficComponent {
    static get styles() {
        return [myCVStyle];
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
    }

    static get properties() {
        return {
            showModal: { type: Boolean }
        };
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
            <div class="container">
	            <h1>Choose a template to make your own CV</h1> 
                <div>
                <a href="cv-builder/myCV1.html"><img src="/content/images/cv1.png"></a>
                <a href="cv-builder/myCV.html"><img src="/content/images/cv2.png"></a>
                </div>    
            </div>  
        `;
    }
}

customElements.define('app-my-cv', MyCV);
