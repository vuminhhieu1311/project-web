import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { notFoundStyle } from './not-found-style';

export default class NotFound extends MaleficComponent {
    static get styles() {
        return [notFoundStyle];
    }
    
    render() {
        return html`
            <div class="content">
                <h1>Page Not Found</h1>
                <p>Sorry, but the page you were trying to view does not exist.</p>
            </div>
        `;
    }
}

customElements.define('app-not-found', NotFound);
