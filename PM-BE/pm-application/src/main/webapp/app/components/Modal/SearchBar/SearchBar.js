import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { searchbarStyle } from './searchbar-style';

import '../Modal';
import { commonStyles } from '../../../shared/styles/common-styles';

class SearchBar extends MaleficComponent {
    static get properties() {
        return {
            show: {type: Boolean}
        };
    }
    
    static get styles() {
        return [searchbarStyle];
    }
    
    handleCloseModal() {
        this.show = false;
        const event = new CustomEvent('close-modal', {
            detail: {},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(event);
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <app-modal .show="${this.show}">
                <div class="modal-header">
                    <button class="close-button" @click="${this.handleCloseModal}"><i class="fas fa-long-arrow-alt-left"></i></button>
                    <input class="search-input" type="text" placeholder="Search">
                    <i class="fas fa-search"></i>
                </div>
            </app-modal>
        `;
    }
}

customElements.define('app-searchbar', SearchBar);
