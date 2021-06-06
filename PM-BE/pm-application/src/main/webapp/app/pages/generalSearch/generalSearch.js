import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import {GeneralSearchStyle } from '../generalSearch/generalSearch-style';


import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/nameCard_Contact/nameCard_Contact';
import '../../components/general-search/company-card/company-card';
import '../../components/general-search/people-card/peopleCard';
import searchCompany from "../../api/searchCompany";
import {withRouter} from "../../core/router/malefic-router";
import searchPeople from "../../api/searchPeople";
import '../../routes/Link';


class GeneralSearch extends withRouter(MaleficComponent){
    static get properties(){
        return{
            companyList: {type: Array},
            userList: {type: Array},
        }
    }

    static get styles(){
        return [GeneralSearchStyle];
    }

    constructor(){
        super();
        this.companyList = [];
        this.userList = [];
    }

    connectedCallback() {
        super.connectedCallback();
        searchCompany(this.params.query)
            .then(data => {
                this.companyList = data._embedded ? data._embedded.companySearchList : [];
            })
        searchPeople(this.params.query)
            .then(data => {
                this.userList = data._embedded ? data._embedded.userSearchList : [];
        })
    }

    render() {
        return html`
            ${commonStyles}
            <app-header></app-header>
            <main>
                <div class="main">
                    ${this.userList.map((user) =>
                            html`
                            <app-link href="profile/${user.id}">
                            <app-people-card
                                        Name="${user.firstName} ${user.lastName}"
                                        avtImg="${user.avatarUrl}"
                                       >
                                </app-people-card>
                            </app-link>
                                `)}
                    ${this.companyList.map((company) =>
                            html`
                            <app-link href="company/${company.id}">
                            <app-company-card           
                                        Name="${company.name}"
                                        logo=${company.logoUrl}>
                                </app-company-card>
                            </app-link>
                                `)}
                </div>

            </main>
        `;
    }
}

customElements.define('app-general-search', GeneralSearch);
