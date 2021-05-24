import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';

class NotAuthorized extends MaleficComponent {
    render() {
        return html`
            <h1>Forbidden</h1>
            <p>You are not allowed to view this page</>
        `;
    }
}

customElements.define('app-not-authorized', NotAuthorized);
