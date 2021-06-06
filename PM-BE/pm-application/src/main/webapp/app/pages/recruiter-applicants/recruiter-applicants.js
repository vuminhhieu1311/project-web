import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { RecruiterApplicantsStyle } from '../../pages/recruiter-applicants/recruiter-applicants-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';
import '../../components/applicant-brief-card/applicantBriefCard';

class ApplicantRecruiter extends MaleficComponent{
    static get properties(){
        return{

        }
    }

    static get styles(){
        return [RecruiterApplicantsStyle];
    }

    constructor(){
        super();
       
    }

    render(){
        return html`
            ${commonStyles}

            <app-header></app-header>

            <main>
        <div id="main__left">
            <div id="filter">
                <div id="custom__filter">
                    Custom filter 
                    <i class="fas fa-caret-down"></i> 
                </div>
                <div id="filter__jobtitle">
                    <form>
                        <label for="jobtitle">Job title</label></br>
                        <select id="jobtitle" name="jobtitle">
                            <option value="example1">Example 1</option>
                            <option value="example2">Example 2</option>
                        </select>
                    </form>
                </div>
                <div id="filter__location">
                    <form>
                        <label for="location">Location</label></br>
                        <select id="location" name="location">
                            <option value="example1">Example 1</option>
                            <option value="example2">Example 2</option>
                        </select>
                    </form>
                </div>
            </div>
        </div>

        <div id="main__right">
            <div id="applicants__list">
                <div id="applicants__amount">100 applicants</div>
                <app-applicant-brief-card
                    name="abc"
                    email="123@gmail.com"
                    location="hanoi"
                    current="student" currentStart="2018" currentEnd="now"
                    education="HUST" educationStart="2018" educationEnd="now"
                    jobTitle="abc">
                </app-applicant-brief-card>

                <app-applicant-brief-card
                    name="abc"
                    email="123@gmail.com"
                    location="hanoi"
                    current="student" currentStart="2018" currentEnd="now"
                    education="HUST" educationStart="2018" educationEnd="now"
                    jobTitle="abc">
                </app-applicant-brief-card>

                <app-applicant-brief-card
                    name="abc"
                    email="123@gmail.com"
                    location="hanoi"
                    current="student" currentStart="2018" currentEnd="now"
                    education="HUST" educationStart="2018" educationEnd="now"
                    jobTitle="abc">
                </app-applicant-brief-card>
                
            </div>
        </div>
    </main>
        `;
    }
}

customElements.define('app-recruiter-applicant', ApplicantRecruiter);