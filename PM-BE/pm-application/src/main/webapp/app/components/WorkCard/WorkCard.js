import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { workCardStyle } from './word-card-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../../api/deleteWorkExperience';
import '../Modal/EditWorkExperience/EditWork';
import deleteWorkExperience from '../../api/deleteWorkExperience';
import getWorkExById from '../../api/getWorkExById';

class WorkCard extends MaleficComponent {
    static get styles() {
        return [workCardStyle];
    }

    static get properties() {
        return {
            showModal: { type: Boolean },
            id: { type: Int16Array },
            work: { type: Object },
        };
    }

    constructor() {
        super();
        this.showModal = false;
        this.work = {};
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    deleteWork() {
        if (confirm("Are you sure you want to delete")) {
            deleteWorkExperience(this.id)
                .then(res => {
                    if (res == 204) {
                        const workCard = this.shadowRoot.querySelector('.news-card');
                        workCard.classList.add('delete');
                    } else {
                        alert("Unsuccessfully! Error occurs");
                    }
                });
        }
    }

    connectedCallback() {
        super.connectedCallback()
        getWorkExById(this.id)
            .then(res => {
                this.work = res ? res : {};
            })
            .catch(e => console.log(e));
    }

    render() {
        const start = new Date(this.work.startDate);
        const end = new Date(this.work.endDate);
        const startMonth = start.getMonth() + 1;
        const endMonth = end.getMonth() + 1;
        const startYear = start.getFullYear();
        const endYear = end.getFullYear();
        const endText = endYear == 1970 ? 'Now' : `${endMonth}/${endYear}`;
        return html`
            ${commonStyles}
            <app-edit-work .show="${this.showModal}" @close-modal="${this.closeModal}" id=${this.id}></app-edit-work>
            <div class="news-card">
                <div class="news-header">
                    <div class="poster">
                        <img class="iconImg" src="content/images/portfolio.png">
                        <div class="poster-info">
                            <div style="font-weight: bold; font-size: 18px;">${this.work.title}</div>
                            <p>${this.work.company}</p>
                            <p>${this.work.employmentType}</p>
                            <p>${this.work.location}</p>
                            <p>${startMonth}/${startYear} - ${endText}</p>
                        </div>
                        <i class="fas fa-pen edit" @click="${this.handleToggleModal}"></i>
                        <i class="fas fa-trash edit" @click="${this.deleteWork}"></i>
                    </div>
                </div>
            </div>
        `;
    }
}

customElements.define('work-card', WorkCard);
