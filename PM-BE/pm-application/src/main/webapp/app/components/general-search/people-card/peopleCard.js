import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { commonStyles } from '../../../shared/styles/common-styles';
import { PeopleCardStyle } from './peopleCard-style';
import { transformImage } from '../../../shared/utils/url-utils';

class PeopleCard extends MaleficComponent {
    static get properties() {
        return {
            avtImg: {type:String},
            Name:  {type:String},
            headline:  {type:String},
        };
    }
    
    static get styles() {
        return [PeopleCardStyle];
    }
    
    constructor() {
        super();
        this.avtImg = "content/images/avatar.png"
    }
    
    render() {
        return html`
            ${commonStyles}
  
            <div class="people__card">
                <div class="people__card__avatar">
                    <img src="${this.avtImg ? transformImage(this.avtImg, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35') : 'content/images/avatar.png'}" height="40px" width="40px">
                </div>

                <div class="people__card__info">
                    <h3>${this.Name}</h3>
                </div>

            </div>
        `;
    }
}

customElements.define('app-people-card', PeopleCard);
