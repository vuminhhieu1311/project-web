import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { alertStyle } from './alert-success-style';
import { commonStyles } from '../../shared/styles/common-styles';

class AlertSuccess extends MaleficComponent {
    
    static get styles() {
        return [alertStyle];
    }

    render() {
        return html`
            ${commonStyles}
              <div class="alert fade alert-simple alert-primary alert-dismissible text-left font__family-montserrat font__size-16 font__weight-light brk-library-rendered rendered show" role="alert" data-brk-library="component__alert">
                <strong class="font__weight-semibold">Successfully!</strong> You have updated your information successfully.
              </div>
        `;
    }
}

customElements.define('app-alert-success', AlertSuccess);


