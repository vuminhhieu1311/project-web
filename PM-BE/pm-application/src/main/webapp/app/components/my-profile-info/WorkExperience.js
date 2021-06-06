import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { workExperienceStyle } from './work-experience-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../Modal/UploadWorkExperience/UploadWork'
import '../WorkCard/WorkCard';
import getWorkExperience from '../../api/getWorkExperience';

class WorkExperience extends MaleficComponent {
    static get styles() {
        return [workExperienceStyle];
    }

    static get properties() {
        return {
            tabShow: { type: Int16Array },
            showModal: { type: Boolean },

            workList: { type: Array },
            showAlert: { type: Boolean }
        };
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
        this.workList = [];
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    connectedCallback() {
        super.connectedCallback()
        getWorkExperience()
            .then(res => {
                this.workList = res._embedded ? res._embedded.workExperienceList : [];
            })
            .catch(e => console.log(e));
    }

    render() {
        return html`
            ${commonStyles}
            <app-upload-work .show="${this.showModal}" @close-modal="${this.closeModal}"></app-upload-work>
            <div class="header-row">
                <h1>Work Experience</h1>
                <app-button btnclass="custom-btn" @click="${this.handleToggleModal}"><i class="fas fa-plus"></i>Add Experience</app-button>
            </div>
            ${this.workList.map((work) =>
            html`<work-card id=${work.id}></work-card>`
        )}
        `;
    }
}

customElements.define('work-experience', WorkExperience);

