import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { EditProfilePopupStyle } from './editProfilePopup-Style';

class EditProfilePopup extends MaleficComponent{
    static get properties(){
        return{

        }
    }

    static get styles(){
        return [EditProfilePopupStyle];
    }

    constructor(){
        super();
    }

    render(){
        return html`
            ${commonStyles}

        <div id="edit__overlay">    
        <div id="edit__overlay__form">  
            <div id="edit__overlay__form__header">  
                <h3>Edit profile</h3>   
                <div id="edit__overlay__form__close">   
                    <i class="fas fa-times"></i>    
                </div>  
            </div>  
            <div id="edit__overlay__form__main">    
                <form>  
                    <div>   
                        <label for="country">Country</label>    
                        <input type="text" name="country" id="contry"/> 
                    </div>  
                    <div>   
                        <label for="location">Location</label>  
                        <input type="text" name="location" id="location"/>  
                    </div>  
                     <div>  
                       <label for="headline">Headline</label>   
                        <input type="text" name="headline" id="headline"/>      
                     </div> 
                     <div>  
                         <label for="industry">Industry</label> 
                        <input type="text" name="industry" id="industry"/>  
                     </div> 
                    <button type="submit">Submit</button>   
                </form> 
            </div>  
        </div>  
        </div>  
        `;
    }
}

customElements.define("app-edit-profile-popup", EditProfilePopup);
