import MaleficComponent from '../../core/components/MaleficComponent';
import { dropdownStyle } from './dropdown-style';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';

class Dropdown extends MaleficComponent {
    static get properties() {
        return {
            toggle: {type: Boolean}
        };
    }
    
    static get styles() {
        return [dropdownStyle];
    }
    
    constructor() {
        super();
        this.toggle = false;
    }
    
    connectedCallback() {
        super.connectedCallback();
        window.addEventListener('click', (e) => this.handleCloseMenu(e), false);
    }
    
    disconnectedCallback() {
        super.disconnectedCallback();
        window.removeEventListener('click', () => this.handleCloseMenu(), false);
    }
    
    handleToggleMenu(e) {
        this.toggle = !this.toggle;
        e.stopPropagation();
    }
    
    handleCloseMenu() {
        if (this.toggle) {
            this.toggle = false;
        }
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <div @click="${this.handleToggleMenu}">
                <slot name="toggle"></slot>
            </div>

            <div class="dropdown-menu ${this.toggle ? 'active' : ''}">
                <slot name="menu-item"></slot>
            </div>
        `;
    }
}

customElements.define('app-dropdown', Dropdown);
