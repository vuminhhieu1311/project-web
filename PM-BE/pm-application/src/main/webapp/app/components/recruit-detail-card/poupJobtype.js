import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { popupJobtypeStyle} from './popupJobtype-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';

class PopupJobtype extends MaleficComponent{
    static get properties(){
        return{
            showPopup: {type:String}
        }
    }

    static get styles(){
        return [popupJobtypeStyle];
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
            <div id="overlay__jobtype" style="display: ${this.showPopup};">
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
                        <div id="cancle" @click="${this.closePopup}">Cancel</div>
                        <button type="submit">Show result</button>
                    </div>
                </form>
            </div>
        </div>
        `;
    }
}

customElements.define('popup-jobtype', PopupJobtype);
