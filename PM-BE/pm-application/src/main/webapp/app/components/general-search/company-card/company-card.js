import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { commonStyles } from '../../../shared/styles/common-styles';
import { CompanyCardStyle } from './companyCard-style';
import { transformImage } from '../../../shared/utils/url-utils';

class CompanyCard extends MaleficComponent {
    static get properties() {
        return {
            id: {type: Int16Array},
            logo: {type:String},
            Name: {type: String},
            location: {type: String},
            follower: {type: String}
        };
    }
    
    static get styles() {
        return [CompanyCardStyle];
    }
    
    constructor() {
        super();
        this.logo='content/images/avatar.png'
    }
    
    render() {
        return html`
            ${commonStyles}
  
        <div class="company__card">
            <div class="company__card__logo">
                <img src="${this.logo ? transformImage(this.logo, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35') : 'content/images/avatar.png'}" height="40px" width="40px">
            </div>
            <div class="company__card__info">
                <h3>${this.Name}</h3>
            </div>
        </div>
        `;
    }
}

customElements.define('app-company-card', CompanyCard);
