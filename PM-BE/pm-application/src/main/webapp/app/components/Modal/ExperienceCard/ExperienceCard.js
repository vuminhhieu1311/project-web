import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { ExperienceCardStyle } from './ ExperienceCard-style';

import '../Modal';
import { commonStyles } from '../../../shared/styles/common-styles';

class ExperienceCard extends MaleficComponent{
    static get properties(){
        return{
            image: {type: String},
            info: {type: String},
            time: {type: String}
        }
    }

    static get styles(){
        return [ExperienceCardStyle];
    }

    render(){
        return html`
            ${commonStyles}

            <div class="experience__card">
                <div class="experience__card__image">
                    <img src="${this.image}" height="60px" width="40px">
                </div>

                <div class="experience__card__info">
                    <h3>${this.info}</h3>
                    <p>${this.time}</p>
                </div>
            </div>
        `;
    }
}

customElements.define("app-experience-card", ExperienceCard);
