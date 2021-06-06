import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { searchJobStyle} from './search-job-style';
import '../../components/brief-job-card/briefJobCard';
import '../../components/recruit-detail-card/recruitDetailCard';
import '../../components/recruit-detail-card/poupJobtype';
import '../../components/recruit-detail-card/popupCreatedat';
import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';


import getJob from "../../api/getJob";
import getCompany from "../../api/getCompany";

class JobDetail extends MaleficComponent{
    static get properties(){
        return{
            showJobtype: {type:String},
            showCreatedat: {type:String},
            id: {type: Int16Array},
            job: {type: JSON},
            company: {type: JSON}
        }
    }

    static get styles(){
        return [searchJobStyle];
    }

    constructor(){
        super();
        this.showJobtype = "none";
        this.showCreatedat ="none";
    }

    connectedCallBack() {
        super.connectedCallback();
        getJob(this.id)
            .then(data => {
                this.job = data;
                console.log(data);
            })
        getCompany(this.job.company_id)
            .then(data => {
                this.company = data;
                console.log(data);
            })

    }

    render(){
        return html`
            ${commonStyles}

            <div class="main__right">
                <recruit-detail-card
                    avatar="${this.company.logo_url}"
                    description="${this.job.description}"
                    companyName="${this.company.name}"
                    address="${this.job.location}"
                    time="${this.job.create_at}"
                    type="${this.job.job_type}"
                    industry="${this.company.industry}"
                    contact="${this.job.contactemail}"
                    companyBriefInfo="${this.company.tagline}"> 
                </recruit-detail-card>
            </div>
        `;
    }
}

customElements.define('job-detail', JobDetail);
