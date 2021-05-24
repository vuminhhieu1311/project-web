import MaleficComponent from '../../core/components/MaleficComponent';
import { getLoginUrl } from '../../shared/utils/url-utils';

class NotAuthenticated extends MaleficComponent {
    connectedCallback() {
        super.connectedCallback();
        location.href = getLoginUrl();
    }
}

customElements.define('app-not-authenticated', NotAuthenticated);
