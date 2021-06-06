import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { editWorkStyle } from './edit-work-style';
import { commonStyles } from '../../../shared/styles/common-styles';

import '../Modal';
import '../../Button/Button';
import patchWorkExperience from '../../../api/patchWorkExperience';
import getWorkExById from '../../../api/getWorkExById';

class EditWork extends MaleficComponent {
    static get properties() {
        return {
            show: { type: Boolean },
            work: { type: Boolean },
            id: { type: Int16Array },
        };
    }

    static get styles() {
        return [editWorkStyle];
    }

    constructor() {
        super();
        this.show = false;
        this.work = {};
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
        const workForm = this.shadowRoot.querySelector("#work-form");
        workForm.addEventListener("submit", (e) => e.preventDefault());
        const formData = new FormData(workForm);

        // Convert formData to a query string
        const data = [...formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');

        patchWorkExperience(this.id, asString)
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
        getWorkExById(this.id)
            .then(res => {
                this.work = res;
                this.shadowRoot.querySelector('#employmentType').value = res.employmentType;
                const startDate = new Date(res.startDate);
                const endDate = new Date(res.endDate);
                const startMonth = startDate.getMonth() + 1;
                const endMonth = endDate.getMonth() + 1;
                const endYear = endDate.getFullYear();
                const startYear = startDate.getFullYear();
                if (endYear == 1970) {
                    this.shadowRoot.querySelector('#endMonth').value = 1;
                } else {
                    if (endMonth <= 9) {
                        this.shadowRoot.querySelector('#endMonth').value = `0${endMonth}`;
                    } else {
                        this.shadowRoot.querySelector('#endMonth').value = endMonth;
                    }
                }
                if (startMonth <= 9) {
                    this.shadowRoot.querySelector('#startMonth').value = `0${startMonth}`;
                } else {
                    this.shadowRoot.querySelector('#startMonth').value = startMonth;
                }
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
        for (var i = 1980; i <= 2021; i++) {
            years.push(i);
        }

        return html`
            ${commonStyles}
            <app-modal .show="${this.show}">
                <div class="avt-modal" id="avt-modal">
                    <div class="post__edit__header">
                        <h3>Edit Experience</h3>
                        <div class="post__edit__close" @click=${this.handleCloseModal}><i class="fas fa-times"></i></div>
                    </div>
                    <form id="work-form">
                    <div class="row">
                        <div class="col span-1-of-4 title">
                            <h5>Title *</h5>
                            <h5>Employment type</h5>
                            <h5>Company *</h5>
                            <h5>Location</h5>
                            <h5>Start Date *</h5>
                            <h5>End Date *</h5>
                            <h5>Description</h5>
                        </div>
                        <div class="col span-3-of-4 info">
                            <input type="text" class="input" id="title" name="title" value=${this.work.title} required>
                            <div class="dob" data-="selectors">
                                <select class="selector" aria-label="Employment Type" value=${this.work.employmentType} id="employmentType" name="employmentType">
                                    <option value="FULL_TIME">Full-time</option>
                                    <option value="PART_TIME">Part-time</option>
                                    <option value="SELF_EMPLOYED">Self-employed</option>
                                    <option value="FREE_LANCE">Freelance</option>
                                    <option value="INTERNSHIP">Internship</option>
                                </select>
                            </div>
                            <input type="text" class="input" value=${this.work.company} id="company" name="company" required>
                            <input type="text" class="input" value=${this.work.location} id="location" name="location">
                            <div class="dob" data-="selectors">
                                <select class="selector" aria-label="Month" id="startMonth" name="startMonth" required @change="${this.onChange}">
                                    <option value="">Month</option>
                                    <option value="01">01</option>
                                    <option value="02">02</option>
                                    <option value="03">03</option>
                                    <option value="04">04</option>
                                    <option value="05">05</option>
                                    <option value="06">06</option>
                                    <option value="07">07</option>
                                    <option value="08">08</option>
                                    <option value="09">09</option>
                                    ${months.map((month) => html`<option value="${month}">${month}</option>`)}
                                </select>
                                <select class="selector" aria-label="Year" id="startYear" name="startYear" required>
                                    <option value="">Year</option>
                                    ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                                </select>
                            </div>
                            <div class="dob" data-="selectors">
                                <select class="selector" aria-label="Month" id="endMonth" name="endMonth" required>
                                    <option value="">Month</option>
                                    <option value="1">Now</option>
                                    <option value="01">01</option>
                                    <option value="02">02</option>
                                    <option value="03">03</option>
                                    <option value="04">04</option>
                                    <option value="05">05</option>
                                    <option value="06">06</option>
                                    <option value="07">07</option>
                                    <option value="08">08</option>
                                    <option value="09">09</option>
                                    ${months.map((month) => html`<option value="${month}">${month}</option>`)}
                                </select>
                                <select class="selector" id="endYear" name="endYear" required>
                                    <option value="">Year</option>
                                    <option value="1970">Now</option>
                                    ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                                </select>
                            </div>
                            <div class="post__edit__text">
                                <textarea name="headline" value=${this.work.headline} placeholder="Type something"></textarea>
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

customElements.define('app-edit-work', EditWork);


