import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { certificationStyle } from './cert-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../Modal/UploadCertification/UploadCertification';
import '../CertificationCard/CertificationCard';
import getCertification from '../../api/getCertification';

class Certification extends MaleficComponent {
    static get styles() {
        return [certificationStyle];
    }

    static get properties() {
        return {
            tabShow: { type: Int16Array },
            showModal: { type: Boolean },

            certList: { type: Array },
            showAlert: { type: Boolean }
        };
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
        this.certList = [];
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    connectedCallback() {
        super.connectedCallback()
        getCertification()
            .then(res => {
                this.certList = res._embedded.certificationList;
            })
            .catch(e => console.log(e));
    }

    render() {
        return html`
            ${commonStyles}
            <app-upload-cert .show="${this.showModal}" @close-modal="${this.closeModal}"></app-upload-cert>
            <div class="header-row">
                <h1>Certification</h1>
                <app-button btnclass="custom-btn" @click="${this.handleToggleModal}"><i class="fas fa-plus"></i>Add Certification</app-button>
            </div>
            ${this.certList.map((cert) =>
            html`<cert-card id=${cert.id}></cert-card>`
        )}
        `;
    }
}

customElements.define('app-cert', Certification);

