import MaleficComponent from '../core/components/MaleficComponent';
import { navigator } from '../core/router/malefic-router';
import { html } from '../core/components/malefic-html';

class Link extends navigator(MaleficComponent) {
    static get properties() {
        return {
            href: {type: String}
        };
    }
    
    constructor() {
        super();
        this.href = '';
    }
    
    linkClick(e) {
        e.preventDefault();
        this.navigate(this.href);
    }
    
    render() {
        return html`
            <a href="${this.href}" @click="${this.linkClick}">
                <slot></slot>
            </a>
        `;
    }
}

customElements.define('app-link', Link);
