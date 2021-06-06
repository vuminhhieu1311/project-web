import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { changePassStyle } from './change-pass-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';

class ChangePass extends MaleficComponent {
    static get styles() {
        return [changePassStyle];
    }

    render() {
        return html`
            ${commonStyles}
            <h1>Change Password</h1>
            <div class="row">
                <div class="col span-1-of-4 title">
                    <h5>Password</h5>
                    <h5>Confirm password</h5>
                </div>
                <div class="col span-3-of-4 info">
                    <input type="password" class="input" id="password" value="admin">
                    <input type="password" class="input" id="confirm-password" value="admin">
                    <div class="update-btn">
                        <app-button btnclass="btn-save">Save</app-button>
                        <app-button btnclass="btn-cancel">Cancel</app-button>
                    </div>
                </div>
            </div>
        `;
    }
}

customElements.define('change-pass', ChangePass);
