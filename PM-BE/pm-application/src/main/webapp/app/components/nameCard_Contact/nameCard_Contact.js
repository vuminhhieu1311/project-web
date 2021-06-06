import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { NameCardStyle } from '../../components/nameCard_Contact/nameCard_Contact-style';


import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';


class NameCard extends MaleficComponent{
    static get properties(){
        return{
            image:{type:String},
            name:{type:String}
        }
    }

    static get styles(){
        return [NameCardStyle];
    }

    
    constructor(){
        super();
    }

    render(){
        return html`
            ${commonStyles}

                    <a class="friends__link" href="#">  
                        <div class="friends__info"> 
                            <img class="friends__info__avatar" src="${this.image}">   
                            <h3 class="friends__info__name">${this.name}</h3> 
                        </div>  
                    </a> 
        
        `;
    }
}

customElements.define('app-name-card', NameCard);