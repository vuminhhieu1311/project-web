import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { modalStyle } from './modal-style';
import { commonStyles } from '../../shared/styles/common-styles';

class Modal extends MaleficComponent {
    static get properties() {
        return {
            show: {type: Boolean}
        };
    }
    
    static get styles() {
        return [modalStyle];
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <div id="modal" class="modal ${this.show ? 'active' : ''}">
                <slot></slot>
            </div>
            <div id="overlay" class="${this.show ? 'active' : ''}"></div>
        `;
    }
}

customElements.define('app-modal', Modal);
