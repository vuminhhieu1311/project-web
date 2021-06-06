import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { searchJobStyle} from './search-job-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';
import '../../components/brief-job-card/briefJobCard';
import '../../components/recruit-detail-card/recruitDetailCard';
import '../../components/recruit-detail-card/poupJobtype';
import '../../components/recruit-detail-card/popupCreatedat';

import '../../components/search-job/job'
import searchJobs from "../../api/searchJobs";
import {data} from "autoprefixer";
import {withRouter} from "../../core/router/malefic-router";
import getAllJobs from "../../api/getAllJobs";
import postCompany from "../../api/postCompany";
import getJob from "../../api/getJob";
import getCompany from "../../api/getCompany";

class SearchJobs extends withRouter(MaleficComponent){
    static get properties(){
        return{
            showJobtype: {type:String},
            showCreatedat: {type:String},
            jobsList : {type: Array},
            htmlList : {type: String},
            formData: { type: FormData},
        }
    }

    static get styles(){
        return [searchJobStyle];
    }

    handleToggleShowPopupJobtype(){
        let jobtype = this.shadowRoot.querySelector("popup-jobtype").showPopup;
        this.shadowRoot.querySelector("popup-jobtype").showPopup = (jobtype=="none")?"block":"none";
        this.shadowRoot.querySelector("popup-createdat").showPopup ="none";
    }

    handleToggleShowPopupCreatedat(){
        let createdat = this.shadowRoot.querySelector("popup-createdat").showPopup;
        this.shadowRoot.querySelector("popup-createdat").showPopup = (createdat=="none")?"block":"none";
        this.shadowRoot.querySelector("popup-jobtype").showPopup ="none";
    }

    constructor() {
        super();
        this.showJobtype = "none";
        this.showCreatedat = "none";
        this.jobsList = []
    }

    connectedCallback() {
        super.connectedCallback();
        getCompany(1)
            .then(data => {
                console.log(data)
            })
        getAllJobs()
            .then(data => {
                this.jobsList = data._embedded.jobList
                console.log(data._embedded.jobList)
                console.log(this.query)
            })
        searchJobs()
            .then(data => {
                console.log(data)
                this.jobsList = data._embedded.jobList
            })
    }

    submitForm() {
        let filter = this.shadowRoot.getElementsByClassName("search__filter");
        filter.addEventListener("submit", (e) => e.preventDefault());
        this.formData = new FormData(filter);

        // Convert formData to a query string
        const data = [...this.formData.entries()];
        const asString = data
            .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
            .join('&');

        const url = new URL("http://localhost:9002/api/v1/_search/jobs")

        const jobType = this.shadowRoot.getElementsByName("jobType").innerHTML;
        if(jobType != null) {
            url.searchParams.append("job_type", "FULL_TIME")
            console.log(jobType)
        }

        const keyWord = this.shadowRoot.getElementsByName("keywords").innerHTML;
        if(keyWord != null) {
            url.searchParams.append("keyword", keyWord)
            console.log(keyWord)
        }
        fetch(url)
            .then(data => {
                this.jobsList = data._embedded.jobList


            })
            .catch((e) => {
                console.log(e)
            })
    }

    render(){
        return html`
            ${commonStyles}

            <app-header></app-header>

            <div>
        <form class="search__filter">
            <input type="text" name="keywords">
                <!--div id="search__filter__jobtype" @click="${this.handleToggleShowPopupJobtype}
                        ">Job type <i class="fas fa-sort-down"></i></div-->
                <!--div id="search__filter__createdat" @click="${this.handleToggleShowPopupCreatedat}
                        ">Created at <i class="fas fa-sort-down"></i></div-->
            <input type="text" name="job_type" placeholder="Job type">
            <button @click="${this.submitForm}">Search</button>

        </form>
 
        <div id="overlay__jobtype">
            <div class="overlay__jobtype__header">
            </div>
            <div class="overlay__jobtype__checkbox">
                <form>
                    <input type="checkbox" name="fulltime" value="fulltime">
                        <label for="fulltime">Full time</label></br>
                    <input type="checkbox" name="contract" value="contract">
                        <label for="contract">Contract</label></br>
                    <input type="checkbox" name="internship" value="internship">
                        <label for="internship">Internship</label></br>
                    <div>
                        <button>Cancel</button>
                        <button type="submit">Show result</button>
                    </div>
                </form>
            </div>
        </div>

        <div id="overlay__createdat">
            <div class="overlay__createdat__header">
            </div>

            <div class="overlay__createdat__checkbox">
                <form>
                    <input type="checkbox" name="anytime" value="fulltime">
                        <label for="anytime">Any time</label></br>
                    <input type="checkbox" name="passmonth" value="contract">
                        <label for="passmonth">Pass month</label></br>
                    <input type="checkbox" name="passweek" value="internship">
                        <label for="passweek">Pass week</label></br>
                    <input type="checkbox" name="passday" value="internship">
                        <label for="passday">Pass 24 hours</label></br>
                    <div>
                        <button>Cancel</button>
                        <button type="submit">Show result</button>
                    </div>
                </form>
            </div>
            
        </div>
        <div class="main">
            <div class="main__left">

                ${this.jobsList.map((job) =>
                        html`
                            <brief-job-card 
                                            companyAvatar="https://i.pinimg.com/originals/d5/7e/f5/d57ef5d4d6bae63dce5b5c8a14f200e7.jpg"
                                            address="${job.location}"
                                            companyName="${job.description}"
                                            jobName="${job.title}"
                                            time="${job.jobType}"
                                            applicants="${job.closedAt}"
                                            id=${job.id}>
                                
                            </brief-job-card>
                            <div @click="${
                            html `
                                </div>
                                <div class="main__right">
                                
                                    <recruit-detail-card
                                            avatar = "https://i.pinimg.com/originals/d5/7e/f5/d57ef5d4d6bae63dce5b5c8a14f200e7.jpg"
                                            description = "nope"
                                            companyName="XYZ"
                                            address="District Hai Ba Trung, Ha Noi City"
                                            time="2 days ago"
                                            requirement="Degree in Electrical Engineering"
                                            level="Entry level"
                                            type="Full-time"
                                            industry="Electrical & Electronic Manufactoring"
                                            function="Engineering"
                                            jobBriefInfo="Example Info"
                                            companyBriefInfo="Example Info">
                                            id=${job.id}>
                                    
                                    </recruit-detail-card>`}">
                                    
                                </div>`

                )}
                

            </div>

            <div class="main__right">
                <recruit-detail-card
                    avatar="https://i.pinimg.com/originals/d5/7e/f5/d57ef5d4d6bae63dce5b5c8a14f200e7.jpg"
                    description=""
                    companyName="XYZ"
                    address="District Hai Ba Trung, Ha Noi City"
                    time="2 days ago"
                    requirement="Degree in Electrical Engineering"
                    level="Entry level"
                    type="Full-time"
                    industry="Electrical & Electronic Manufactoring"
                    function="Engineering"
                    jobBriefInfo="Example Info"
                    companyBriefInfo="Example Info"> 
                </recruit-detail-card>
            </div>
        </div>

    </div>
        `;
    }

}

customElements.define('app-search-job', SearchJobs);
