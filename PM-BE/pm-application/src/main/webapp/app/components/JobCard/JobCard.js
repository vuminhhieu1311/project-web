import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { jobCardStyle } from './job-card-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';
import '../PostCard/commentCard';
import deleteJob from '../../api/deleteJob';
import '../Modal/EditJob/EditJob';
import global from '../global'
class JobCard extends MaleficComponent {
    static get styles() {
        return [jobCardStyle];
    }

    static get properties() {
        return {
            accountImg: {type: String},
            title: {type: String},
            company: {type: String},
            location: {type: String},
            jobType: {type: String},
            description: {type: String},
            postId: {type: Int16Array},
            showEdit: {type: Boolean},
            showDropdownEdit: {type: String},
            attachment: {type: Object},
            contact: {type: String},
            id: {type: Int16Array},
            showModal: { type: Boolean },
        };
    }

    constructor() {
        super();
        this.showDropdownEdit="none";
        this.showPost="block";
        this.attachment={}; 
        this.showModal = false; 
       
    }

    handleToggleShowComment(){
        this.showComment = (this.showComment=="block")?"none":"block";
    }

    handleToggleDropdown(){
        //let dropdownIcon = this.shadowRoot.querySelector(".edit-icon");
        //dropdownIcon.stopPropagation();
        this.showDropdownEdit = (this.showDropdownEdit=="none")?"block":"none";
        /*window.addEventListener("click",()=>{
            if(this.showDropdownEdit=="block") this.showDropdownEdit="none";
            console.log("test");
        })*/
    }

    closeEdit(){
        this.showEdit=false;
    }
    
    handleDeletePost(){
        if (confirm("Are you sure you want to delete")) {
            deleteJob(this.id)
                .then(res => {
                    if (res == 204) {
                        const jobCard = this.shadowRoot.querySelector('.news-card');
                        jobCard.classList.add('delete');
                    } else {
                        alert("Unsuccessfully! Error occurs");
                    }
            });
        }
    }

    connectedCallback() {
        super.connectedCallback();
    }



    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    render() {
        return html`
            ${commonStyles}
        <app-edit-job .show="${this.showModal}" @close-modal="${this.closeModal}" id=${this.id} company="${this.company}" location="${this.location}" title="${this.title}" jobType="${this.jobType}" description="${this.description}" contact="${this.contact}"></app-edit-job>
        <div style="display:${this.showPost};">
            <div class="news-card">
                <div class="news-header">
                    <div class="poster">
                        <img src="${this.accountImg}" alt="">
                        <div class="poster-info">
                            <div style="font-weight: bold; font-size: 18px;">${this.title}</div>
                            <div style="font-size: 13px;">${this.company}</div>
                            <div style="font-size: 13px;">${this.location}</div>
                        </div>
                    </div>

                    <div class="edit" >
                        <div class="edit-icon" @click="${this.handleToggleDropdown}">
                            <i class="fas fa-ellipsis-h"></i>
                        </div>

                        <div id="dropdown-edit" style="display:${this.showDropdownEdit}">
                            <div id="edit-post" @click="${this.handleToggleModal}">Edit Job</div>
                            <div id="delete-post" @click="${this.handleDeletePost}">Delete Job</div>
                        </div>
                    </div>

                </div>

                <div class="recruit-info">
                    <div>Employment Type: ${this.jobType}</div>
                    <div>Job Description: ${this.description}</div>
                    <div>Contact Email: ${this.contact}</div>

                </div>
            </div>
        </div>
        `;
    }
}

customElements.define('job-card', JobCard);
