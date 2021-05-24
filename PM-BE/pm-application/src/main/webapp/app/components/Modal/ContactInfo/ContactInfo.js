import MaleficComponent from '../../../core/components/MaleficComponent';
import { html } from '../../../core/components/malefic-html';
import { commonStyles } from '../../../shared/styles/common-styles';
import { contactInfoStyle } from './contact-info-style';

class ContactInfo extends MaleficComponent {
    static get properties() {
        return {
            show: {type: Boolean}
        };
    }
    
    static get styles() {
        return [contactInfoStyle];
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

            <div id="contact-info-div" class="${this.show ? 'active' : ''}">
                <div id="contact-info-header">
                    <h1>Name</h1>
                    <button id="contact-info-close" @click="${this.handleCloseModal}">
                        &times;
                    </button>
                </div>
    
                <div id="contact-info-main">
                    <div id="contact-info-profile">
                        <div class="material-icons md-24">
                            contacts
                        </div>
                        <h3>Link profile</h3>
                        <a href="#">Link to profile in linkedIn</a>
        
                    </div>
        
                    <div id="contact-info-email">
                        <div class="material-icons md-24">
                            email
                        </div>
                        <h3>Link email</h3>
                        <a href="mailto: abc@example.com">abc@example.com</a>
                    </div>
                </div>
            </div>
            <div id="overlay" class="${this.show ? 'active' : ''}"></div>
        `;
    }
}

customElements.define('app-contact-info', ContactInfo);
