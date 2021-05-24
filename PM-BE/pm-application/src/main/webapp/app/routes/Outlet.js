import MaleficComponent from '../core/components/MaleficComponent';
import { outlet } from '../core/router/malefic-router';
import { html } from '../core/components/malefic-html';

class Outlet extends outlet(MaleficComponent) {
    render() {
        return html`
            <slot></slot>
        `;
    }
}

customElements.define('app-outlet', Outlet);
