import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { certCardStyle } from './cert-card-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../../api/deleteWorkExperience';
import '../Modal/EditCertification/EditCert';
import deleteCertification from '../../api/deleteCertification';
import getCertById from '../../api/getCertById';

class CertificationCard extends MaleficComponent {
    static get styles() {
        return [certCardStyle];
    }

    static get properties() {
        return {
            showModal: { type: Boolean },
            id: { type: Int16Array },
            cert: { type: Object },
        };
    }

    constructor() {
        super();
        this.showModal = false;
        this.cert = {};
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    deleteCert() {
        if (confirm("Are you sure you want to delete")) {
            deleteCertification(this.id)
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
        getCertById(this.id)
            .then(res => {
                this.cert = res;
            })
            .catch(e => console.log(e));
    }

    render() {
        const start = new Date(this.cert.issDate);
        const end = new Date(this.cert.expDate);
        const startMonth = start.getMonth() + 1;
        const endMonth = end.getMonth() + 1;
        const startYear = start.getFullYear();
        const endYear = end.getFullYear();
        const endText = endYear == 1970 ? 'Not yet' : `${endMonth}/${endYear}`;
        return html`
            ${commonStyles}
            <app-edit-cert .show="${this.showModal}" @close-modal="${this.closeModal}" id=${this.id}></app-edit-cert>
            <div class="news-card">
                <div class="news-header">
                    <div class="poster">
                    <img class="iconImg" src="content/images/medal.png">
                        <div class="poster-info">
                            <div style="font-weight: bold; font-size: 18px;">${this.cert.name}</div>
                            <p>${this.cert.issOrganization}</p>
                            <p>Issued: ${startMonth}/${startYear}</p>
                            <p>Expired: ${endText}</p>
                        </div>
                        <i class="fas fa-pen edit" @click="${this.handleToggleModal}"></i>
                        <i class="fas fa-trash edit" @click="${this.deleteCert}"></i>
                    </div>
                </div>
            </div>
        `;
    }
}

customElements.define('cert-card', CertificationCard);
