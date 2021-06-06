import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { personalInfoStyle } from './personal-info-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import getProfile from '../../api/getProfile';
import patchPersonalProfile from '../../api/patchPersonalProfile';
import '../Alert/AlertSuccess';
import '../Alert/AlertFail';

class PersonalInfo extends MaleficComponent {
    static get styles() {
        return [personalInfoStyle];
    }

    static get properties() {
        return {
            tabShow: { type: Int16Array },
            showModal: { type: Boolean },
            about: { type: String },
            address: { type: String },
            bgImageUrl: { type: String },
            birthday: { type: Date },
            country: { type: String },
            headline: { type: String },
            industry: { type: String },
            location: { type: String },
            phoneNumber: { type: String },
            showAlert: { type: Boolean }
        };
    }

    constructor() {
        super();
        this.showAlert = false;
        this.about = '';
        this.address = '';
        this.bgImageUrl = '';
        this.birthday = '';
        this.country = '';
        this.headline = '';
        this.industry = '';
        this.location = '';
        this.phoneNumber = '';
    }
    connectedCallback() {
        super.connectedCallback()
        getProfile()
            .then(res => {
                this.about = res.about;
                this.address = res.address;
                this.bgImageUrl = res.bgImageUrl;
                this.birthday = new Date(res.birthday);
                this.country = res.country;
                this.headline = res.headline;
                this.industry = res.industry;
                this.location = res.location;
                this.phoneNumber = res.phoneNumber;
                this.shadowRoot.querySelector('#day').value = this.birthday.getDate();
                this.shadowRoot.querySelector('#month').value = this.birthday.getMonth() + 1;
                this.shadowRoot.querySelector('#year').value = this.birthday.getFullYear();
            })
            .catch(e => console.log(e));
        this.showAlert = false;
    }

    submitForm() {
        const personalForm = this.shadowRoot.querySelector("#personalForm");
        personalForm.addEventListener("submit", (e) => e.preventDefault());
        const formData = new FormData(personalForm);

        const day = this.shadowRoot.querySelector('#day');
        const month = this.shadowRoot.querySelector('#month');
        const year = this.shadowRoot.querySelector('#year');
        const birthday = new Date(year.value, month.value - 1, day.value);
        const ISODate = new Date(birthday.getTime() - (birthday.getTimezoneOffset() * 60000)).toISOString();
        formData.append('birthday', ISODate);

        // Convert formData to a query string
        const data = [...formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');

        patchPersonalProfile(asString)
            .then(data => {
                if (data) {
                    const alertBox = this.shadowRoot.querySelector('.show-alert');
                    alertBox.classList.add('active');
                    setTimeout(function () {
                        alertBox.classList.remove('active')
                    }, 2000);
                }
            })
            .catch(() => {
                const alertBox = this.shadowRoot.querySelector('.show-alert-fail');
                alertBox.classList.add('active');
                setTimeout(function () {
                    alertBox.classList.remove('active')
                }, 2000);
            });
    }

    render() {
        const days = [];
        const months = [];
        const years = [];
        for (var i = 1; i <= 31; i++) {
            days.push(i);
        }
        for (var i = 1; i <= 12; i++) {
            months.push(i);
        }
        for (var i = 1980; i <= 2004; i++) {
            years.push(i);
        }
        return html`
            ${commonStyles}
            <h1>Personal Information</h1>
            <form id="personalForm">
                <div class="row">
                <div class="col span-1-of-4 title">
                    <h5 class="about-title">About</h5>
                    <h5>Address</h5>
                    <h5>Date Of Birth</h5>
                    <h5>Country</h5>
                    <h5>Headline</h5>
                    <h5>Industry</h5>
                    <h5>Location</h5>
                    <h5>Phone Number</h5>
                </div>
                <div class="col span-3-of-4 info">
                    <div class="post__edit__text">
                        <textarea id="about" name="about" value="${this.about}"></textarea>
                    </div>
                    <input type="text" class="input" id="address" name="address" value="${this.address}">
                    <div class="dob" data-="selectors">
                        <select class="selector" aria-label="Day" id="day">
                            <option value="0">Day</option>
                            ${days.map((day) => html`<option value="${day}">${day}</option>`)}
                        </select>
                        <select class="selector" aria-label="Month" id="month">
                            <option value="0">Month</option>
                            ${months.map((month) => html`<option value="${month}">${month}</option>`)}
                        </select>
                        <select class="selector" aria-label="Year" id="year">
                            <option value="0">Year</option>
                            ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                        </select>
                    </div>
                    <input type="text" class="input" id="country" name="country" value="${this.country}">
                    <input type="text" class="input" id="headline" name="headline" value="${this.headline}">
                    <input type="text" class="input" id="address" name="industry" value="${this.industry}">
                    <input type="text" class="input" id="location" name="location" value="${this.location}">
                    <input type="text" class="input" id="phoneNumber" name="phoneNumber" value="${this.phoneNumber}">
                    <div class="update-btn">
                        <button class="btn-save" @click="${this.submitForm}">Save</button>
                        <button type="reset" class="btn-cancel">Cancel</button>
                    </div>
                </div>
            </div>
            </form>
            <div class="show-alert">
                <app-alert-success></app-alert-success>
            </div>
            <div class="show-alert-fail">
                <app-alert-fail></app-alert-fail>
            </div>
        `;
    }
}

customElements.define('personal-info', PersonalInfo);