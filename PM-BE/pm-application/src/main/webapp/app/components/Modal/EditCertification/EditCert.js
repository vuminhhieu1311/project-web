import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { editCertStyle } from './edit-cert-style';
import { commonStyles } from '../../../shared/styles/common-styles';

import '../Modal';
import '../../Button/Button';
import patchCertification from '../../../api/patchCertification';
import getCertById from '../../../api/getCertById';

class EditCert extends MaleficComponent {
    static get properties() {
        return {
            show: { type: Boolean },
            cert: { type: Boolean },
            id: { type: Int16Array },
        };
    }

    static get styles() {
        return [editCertStyle];
    }

    constructor() {
        super();
        this.show = false;
        this.cert = {};
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
        const certForm = this.shadowRoot.querySelector("#cert-form");
        certForm.addEventListener("submit", (e) => e.preventDefault());
        const formData = new FormData(certForm);
  
        // Convert formData to a query string
        const data = [...formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');

        patchCertification(this.id, asString)
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
        getCertById(this.id)
        .then(res => {
            this.cert = res;
            
            const issDate = new Date(res.issDate);
            const expDate = new Date(res.expDate);
            const issMonth = issDate.getMonth() + 1;
            const expMonth = expDate.getMonth() + 1;
            const issYear = issDate.getFullYear();
            const expYear = expDate.getFullYear();
            if(expYear == 1970) {
                this.shadowRoot.querySelector('#expMonth').value = 1;
            } else {
                if(expMonth <= 9) {
                    this.shadowRoot.querySelector('#expMonth').value = `0${expMonth}`;
                } else {
                    this.shadowRoot.querySelector('#expMonth').value = expMonth;
                }
            }
            if(issMonth <= 9) {
                this.shadowRoot.querySelector('#issMonth').value = `0${issMonth}`;
            } else {
                this.shadowRoot.querySelector('#issMonth').value = issMonth;
            }
            this.shadowRoot.querySelector('#issYear').value = issYear;
            this.shadowRoot.querySelector('#expYear').value = expYear;
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
                        <h3>Edit Certification</h3>
                        <div class="post__edit__close" @click=${this.handleCloseModal}><i class="fas fa-times"></i></div>
                    </div>
                    <form id="cert-form">
                    <div class="row">
                    <div class="col span-1-of-4 title">
                        <h5>Name *</h5>
                        <h5>Organization *</h5>
                        <h5>Issue Date *</h5>
                        <h5>Expiration Date *</h5>
                        <h5>Credential ID</h5>
                        <h5>Credential URL</h5>
                    </div>
                    <div class="col span-3-of-4 info">
                        <input type="text" class="input" id="name" name="name" value="${this.cert.name}" required>
                        <input type="text" class="input" id="issOrganization" name="issOrganization" value="${this.cert.issOrganization}" required>
                        <div class="dob" data-="selectors">
                            <select class="selector" aria-label="Month" id="issMonth" name="issMonth" required>
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
                            <select class="selector" aria-label="Year" id="issYear" name="issYear" required>
                                <option value="">Year</option>
                                ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                            </select>
                        </div>
                        <div class="dob" data-="selectors">
                            <select class="selector" aria-label="Month" id="expMonth" name="expMonth" required>
                                <option value="">Month</option>
                                <option value="1">Not yet</option>
                                <option value="01">1</option>
                                <option value="02">2</option>
                                <option value="03">3</option>
                                <option value="04">4</option>
                                <option value="05">5</option>
                                <option value="06">6</option>
                                <option value="07">7</option>
                                <option value="08">8</option>
                                <option value="09">9</option>
                                ${months.map((month) => html`<option value="${month}">${month}</option>`)}
                            </select>
                            <select class="selector" id="expYear" name="expYear" required>
                                <option value="">Year</option>
                                <option value="1970">Not yet</option>
                                ${years.map((year) => html`<option value="${year}">${year}</option>`)}
                            </select>
                        </div>
                        <input type="text" class="input" id="credentialID" name="credentialID" value="${this.cert.credentialID}">
                        <input type="text" class="input" id="credentialURL" name="credentialURL" value="${this.cert.credentialURL}">
                        <div class="update-btn">
                            <button type="submit" class="btn-save" @click="${this.submitForm}">Save</button>
                            <button type="reset"class="btn-cancel">Cancel</button>
                        </div>
                    </div>
                </div>
                    
                         
                    </div>
                </form>
                </div>   
            </app-modal>
        `;
    }
}

customElements.define('app-edit-cert', EditCert);


