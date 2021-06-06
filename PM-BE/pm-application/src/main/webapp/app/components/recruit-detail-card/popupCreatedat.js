import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { popupCreatedatStyle} from './popupCreatedat-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';

class PopupCreatedat extends MaleficComponent{
    static get properties(){
        return{
            showPopup: {type:String}
        }
    }

    static get styles(){
        return [popupCreatedatStyle];
    }

    closePopup(){
        this.showPopup ="none";
    }

    constructor(){
        super();
    }

    render(){
        return html `
            ${commonStyles}
            <div id="overlay__createdat" style="display:${this.showPopup};">
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
                        <div id="cancle" @click="${this.closePopup}">Cancel</div>
                        <button type="submit">Show result</button>
                    </div>
                </form>
            </div>
            
        </div>
        `;
    }
}

customElements.define('popup-createdat', PopupCreatedat);
