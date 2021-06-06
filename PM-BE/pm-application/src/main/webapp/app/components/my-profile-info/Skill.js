import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { skillStyle } from './skill-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../Modal/UploadCertification/UploadCertification';
import '../CertificationCard/CertificationCard';
import getSkill from '../../api/getSkill';
import deleteSkill from '../../api/deleteSkill';
import postSkill from '../../api/postSkill';

class Skill extends MaleficComponent {
    static get styles() {
        return [skillStyle];
    }

    static get properties() {
        return {
            tabShow: { type: Int16Array },
            showModal: { type: Boolean },

            skillList: { type: Array },
            showAlert: { type: Boolean }
        };
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
        this.skillList = [];
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    connectedCallback() {
        super.connectedCallback()
        getSkill()
            .then(res => {
                this.skillList = res._embedded.skillList;
            })
            .catch(e => console.log(e));
    }

    deleteSkill(id) {
        if (confirm("Are you sure you want to delete")) {
            deleteSkill(id)
                .then(res => {
                    if (res == 204) {
                        getSkill()
                            .then(res => {
                                this.skillList = res._embedded.skillList;
                            })
                            .catch(e => console.log(e));
                    } else {
                        alert("Unsuccessfully! Error occurs");
                    }
                });
        }
    }

    addSkill() {
        const skillForm = this.shadowRoot.querySelector("#skill-form");
        skillForm.addEventListener("submit", (e) => e.preventDefault());
        const formData = new FormData(skillForm);

        // Convert formData to a query string
        const data = [...formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');
        console.log(asString);

        postSkill(asString)
            .then(data => {
                console.log(data);
                if (data == 201) {
                    getSkill()
                        .then(res => {
                            this.skillList = res._embedded.skillList;
                        })
                        .catch(e => console.log(e));
                }
            })
            .catch(e => {
                console.log(e);
                alert("Fail to add a work experience!");
            })
    }

    render() {
        return html`
            ${commonStyles}
            <app-upload-cert .show="${this.showModal}" @close-modal="${this.closeModal}"></app-upload-cert>
            <h1>Skills</h1>
            <form id="skill-form">
                <div class="header-row">
                <input type="text" name="name" id="skill" placeholder="Add a new skill">
                    <button type="submit" class="custom-btn" @click="${this.addSkill}"><i class="fas fa-plus"></i></button>
                </div>
            </form>

            ${this.skillList.map((skill) =>
            html`<div class="skill-list">
                        <i class="fas fa-trash" @click="${() => this.deleteSkill(skill.id)}"></i>
                        <p>${skill.name}</p>
                    </div>`)}`;
    }
}

customElements.define('app-skill', Skill);

