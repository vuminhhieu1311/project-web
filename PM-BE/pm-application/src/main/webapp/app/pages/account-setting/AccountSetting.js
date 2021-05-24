import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { accountSettingStyle } from './account-setting-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/footer/SmallFooter';
import '../../components/my-account/MyAccount';
import '../../components/my-account/ChangePass';
import '../../components/Modal/UploadAvatar/UploadAvatar'

class AccountSetting extends MaleficComponent {
    constructor() {
        super();
        location.href = 'http://localhost:8090/auth/realms/personal-management/account'
    }
}

customElements.define('app-account-setting', AccountSetting);
