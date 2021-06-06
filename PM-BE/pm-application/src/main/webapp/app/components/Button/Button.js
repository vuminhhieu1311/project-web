import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { buttonStyle } from './button-style';
import { commonStyles } from '../../shared/styles/common-styles';

class Button extends MaleficComponent {
    static get properties() {
        return {
            btnClass: {type: String, attribute: 'btnclass'}
        };
    }
    
    static get styles() {
        return [buttonStyle];
    }
    
    constructor() {
        super();
        this.btnClass = '';
    }
    
    render() {
        return html`
            ${commonStyles}
            <a class="${this.btnClass}">
                <slot></slot>
            </a>
        `;
    }
}

customElements.define('app-button', Button);
