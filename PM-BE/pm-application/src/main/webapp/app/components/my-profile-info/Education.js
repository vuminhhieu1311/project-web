import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { educationStyle } from './education-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../Modal/UploadEducation/UploadEducation'
import '../EducationCard/EducationCard';
import getEducation from '../../api/getEducation';

class Education extends MaleficComponent {
    static get styles() {
        return [educationStyle];
    }

    static get properties() {
        return {
            tabShow: { type: Int16Array },
            showModal: { type: Boolean },

            educationList: { type: Array },
            showAlert: { type: Boolean }
        };
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
        this.educationList = [];
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    connectedCallback() {
        super.connectedCallback()
        getEducation()
            .then(res => {
                this.educationList = res._embedded.educationList;
            })
            .catch(e => console.log(e));
    }

    render() {
        return html`
            ${commonStyles}
            <app-upload-education .show="${this.showModal}" @close-modal="${this.closeModal}"></app-upload-education>
            <div class="header-row">
                <h1>Education</h1>
                <app-button btnclass="custom-btn" @click="${this.handleToggleModal}"><i class="fas fa-plus"></i>Add Education</app-button>
            </div>
            ${this.educationList.map((education) =>
                html`<education-card id=${education.id}></education-card>`
            )}
        
        `;
    }
}

customElements.define('app-education', Education);


