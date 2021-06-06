import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';

import '../intro/Intro';

class NotAuthenticated extends MaleficComponent {
    constructor() {
        super();
        history.pushState(null, null, '/');
    }
    
    render() {
        return html`
            <app-intro></app-intro>
        `;
    }
}

customElements.define('app-not-authenticated', NotAuthenticated);
