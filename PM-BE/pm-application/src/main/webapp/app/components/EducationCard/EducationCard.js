import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { educationCardStyle } from './education-card-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../../api/deleteWorkExperience';
import '../Modal/EditEducation/EditEducation';
import deleteEducation from '../../api/deleteEducation';
import getEducationById from '../../api/getEducationById';

class EducationCard extends MaleficComponent {
    static get styles() {
        return [educationCardStyle];
    }

    static get properties() {
        return {
            showModal: { type: Boolean },
            id: { type: Int16Array },
            education: { type: Object },
        };
    }

    constructor() {
        super();
        this.showModal = false;
        this.education = {};
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    deleteEducation() {
        if (confirm("Are you sure you want to delete")) {
            deleteEducation(this.id)
                .then(res => {
                    if (res == 204) {
                        const educationCard = this.shadowRoot.querySelector('.news-card');
                        educationCard.classList.add('delete');
                    } else {
                        alert("Unsuccessfully! Error occurs");
                    }
                });
        }
    }

    connectedCallback() {
        super.connectedCallback()
        getEducationById(this.id)
            .then(res => {
                this.education = res;
            })
            .catch(e => console.log(e));
    }

    render() {
        const start = new Date(this.education.startDate);
        const end = new Date(this.education.endDate);
        const startYear = start.getFullYear();
        const endYear = end.getFullYear();
        const endText = endYear == 1970 ? 'Now' : endYear;
        return html`
            ${commonStyles}
            <app-edit-education .show="${this.showModal}" @close-modal="${this.closeModal}" id=${this.id}></app-edit-education>
            <div class="news-card">
                <div class="news-header">
                    <div class="poster">
                        <img class="iconImg" src="content/images/school.png">
                        <div class="poster-info">
                            <div style="font-weight: bold; font-size: 18px;">${this.education.school}</div>
                            <p>${this.education.degree}</p>
                            <p>${this.education.fieldOfStudy}</p>
                            <p>${this.education.grade}</p>
                            <p>${startYear} - ${endText}</p>
                        </div>
                        <i class="fas fa-pen edit" @click="${this.handleToggleModal}"></i>
                        <i class="fas fa-trash edit" @click="${this.deleteEducation}"></i>
                    </div>
                </div>
            </div>
        `;
    }
}

customElements.define('education-card', EducationCard);