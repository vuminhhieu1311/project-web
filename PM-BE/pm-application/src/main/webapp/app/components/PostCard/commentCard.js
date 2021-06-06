import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { CommentCardStyle} from '../../components/PostCard/commentCard-style';
import { commonStyles } from '../../shared/styles/common-styles';

class CommentCard extends MaleficComponent{
    static get properties(){
        return{
            
        };
    }

    static get styles(){
        return [CommentCardStyle];
    }

    constructor(){
        super();
    }

    render(){
        return html`
            ${commonStyles}
                <div class="comment__card">
                    <div class="comment__card__avatar">
                        <img id="comment__card__avatar" src="https://cdn.theculturetrip.com/wp-content/uploads/2018/05/shutterstock_89159080.jpg">
                    </div>

                    <div class="comment__card__content">
                        <div class="comment__card__content__name">
                            <a href="#">Some account</a>
                        </div>

                        <div class="comment__card__content__detail">
                            <p>This is a beautiful sight!</p>
                        </div>
                        
                        <div class="comment__card__content__react">
                            <div id="comment__card__react__like" > Like </div>
                            <div id="comment__card__react__reply"> Reply </div>
                        </div>
                    </div>
                </div>

        `;
    }
}

customElements.define("comment-card", CommentCard);
