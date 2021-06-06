import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { alertFailStyle } from './alert-fail-style';
import { commonStyles } from '../../shared/styles/common-styles';

class AlertFail extends MaleficComponent {
    
    static get styles() {
        return [alertFailStyle];
    }

    render() {
        return html`
            ${commonStyles}
              <div class="alert fade alert-simple alert-primary alert-dismissible text-left font__family-montserrat font__size-16 font__weight-light brk-library-rendered rendered show" role="alert" data-brk-library="component__alert">
                <strong class="font__weight-semibold">Fail!</strong> Some things went wrong!
              </div>
        `;
    }
}

customElements.define('app-alert-fail', AlertFail);


