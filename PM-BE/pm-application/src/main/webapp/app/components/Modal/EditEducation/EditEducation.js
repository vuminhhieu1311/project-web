import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { editEducationStyle } from './edit-education-style';
import { commonStyles } from '../../../shared/styles/common-styles';

import '../Modal';
import '../../Button/Button';
import patchEducation from '../../../api/patchEducation';
import getEducationById from '../../../api/getEducationById';

class EditEducation extends MaleficComponent {
    static get properties() {
        return {
            show: { type: Boolean },
            education: { type: Boolean },
            id: { type: Int16Array },
        };
    }

    static get styles() {
        return [editEducationStyle];
    }

    constructor() {
        super();
        this.show = false;
        this.education = {};
        this.id = 0;
    }

    handleCloseModal() {
        this.show = false;
        const event = new CustomEvent('close-modal', {
            detail: {},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(event);
    }

    submitForm() {
        const educationForm = this.shadowRoot.querySelector("#education-form");
        educationForm.addEventListener("submit", (e) => e.preventDefault());
        const formData = new FormData(educationForm);

        // Convert formData to a query string
        const data = [...formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');

        patchEducation(this.id, asString)
            .then(data => {
                if (data == 200) {
                    location.reload();
                }
            })
            .catch(e => {
                console.log(e);
                alert("Error occurs!");
            })
    }

    connectedCallback() {
        super.connectedCallback()
        getEducationById(this.id)
            .then(res => {
                this.education = res;
                const startDate = new Date(res.startDate);
                const endDate = new Date(res.endDate);
                const endYear = endDate.getFullYear();
                const startYear = startDate.getFullYear();
                this.shadowRoot.querySelector('#startYear').value = startYear;
                this.shadowRoot.querySelector('#endYear').value = endYear;
            });
    }

    render() {
        const months = [];
        const years = [];
        for (var i = 10; i <= 12; i++) {
            months.push(i);
        }
        for (var i = 1980; i <= 2030; i++) {
            years.push(i);
        }

        return html`
            ${commonStyles}
            <app-modal .show="${this.show}">
                <div class="avt-modal" id="avt-modal">
                    <div class="post__edit__header">
                        <h3>Edit Education</h3>
                        <div class="post__edit__close" @click=${this.handleCloseModal}><i class="fas fa-times"></i></div>
                    </div>
                    <form id="education-form">
                    <div class="row">
                        <div class="col span-1-of-4 title">
                            <h5>School *</h5>
                            <h5>Degree</h5>
                            <h5>Field of study</h5>
                            <h5>Start Year *</h5>
                            <h5>End Year *</h5>
                            <h5>Grade</h5>
                            <h5 class="activities">Activities</h5>
                            <h5>Description</h5>
                        </div>
                        <div class="col span-3-of-4 info">
                            <input type="text" class="input" id="school" name="school" value=${this.education.school} required>
                            <input type="text" class="input" id="degree" name="degree" value=${this.education.degree}>
                            <input type="text" class="input" value=${this.education.fieldOfStudy} id="fieldOfStudy" name="fieldOfStudy">
                            <div class="dob" data-="selectors">
                                <select class="selector" aria-label="Year" id="startYear" name="startYear" required>
                                    <option value="">Year</option>
                                    ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                                </select>
                            </div>
                            <div class="dob" data-="selectors">
                                <select class="selector" id="endYear" name="endYear" required>
                                    <option value="">Year</option>
                                    <option value="1970">Now</option>
                                    ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                                </select>
                            </div>
                            <input type="text" class="input" id="grade" name="grade" value=${this.education.grade}>
                            <div class="post__edit__text">
                                <textarea name="headline" value=${this.education.activities} placeholder="Ex: Alpha Phi Omega, Marching Band, Volleyball"></textarea>
                            </div>
                            <div class="post__edit__text">
                                <textarea name="headline" value=${this.education.description}></textarea>
                            </div>
                            <div class="update-btn">
                                <button type="submit" class="btn-save" @click="${this.submitForm}">Save</button>
                                <button type="reset"class="btn-cancel">Cancel</button>
                            </div>
                        </div>
                    </div>
                </form>
                </div>   
            </app-modal>
        `;
    }
}

customElements.define('app-edit-education', EditEducation);


