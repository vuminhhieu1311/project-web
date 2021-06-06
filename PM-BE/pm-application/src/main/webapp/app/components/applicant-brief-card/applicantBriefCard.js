import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { ApplicantBriefCardStyle } from '../../components/applicant-brief-card/applicantBriefCard-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';

class ApplicantBriefCard extends MaleficComponent{
    static get properties(){
        return{
            avatar: {type:String},
            name: {type:String},
            email: {type: String},
            location: {type:String},
            current: {type:String},
            currentStart: {type:String},
            currentEnd: {type:String},
            education: {type:String},
            educationStart: {type:String},
            educationEnd: {type:String},
            jobTitle: {type:String},
        }
    }

    static get styles(){
        return [ApplicantBriefCardStyle];
    }

    constructor(){
        super();
       
    }

    render(){
        return html`
            ${commonStyles}

            
                <div id="applicants__brief__card">
                    <div id="applicants__brief__card__header">
                        <div id="applicants__brief__card__header__avatar">
                            <img src="${this.avatar}" style="height: 50px; width: 50px;border-radius: 50%;">
                        </div>
                        <div id="applicants__brief__card__header__name">
                            <h3>${this.name}</h3>
                            <p id="email">${this.email}</p>
                            <p id="location">${this.location}</p>
                        </div>

                        <div id="applicants__brief__card__header__button">
                            <button>Save</button>
                            <button>Hide</button>
                        </div>
                    </div>

                    <div class="applicants__brief__card__info">
                        <div class="applicants__brief__card__info__title">
                            Current
                        </div>

                        <div class="applicants__brief__card__info__detail">
                            ${this.current} <span class="duration"><i class="fab fa-jira"></i>${this.currentStart} - ${this.currentEnd}</span>
                        </div>

                        <div class="applicants__brief__card__info__title">
                            Education
                        </div>

                        <div class="applicants__brief__card__info__detail">
                            ${this.education} <span class="duration"><i class="fab fa-jira"></i>${this.educationStart} - ${this.educationEnd}</span>
                        </div>

                        <div class="applicants__brief__card__info__title">
                            Job title
                        </div>

                        <div class="applicants__brief__card__info__detail">
                            ${this.jobTitle}
                        </div>
                    </div>

                </div>

        `;
    }
}

customElements.define('app-applicant-brief-card', ApplicantBriefCard);