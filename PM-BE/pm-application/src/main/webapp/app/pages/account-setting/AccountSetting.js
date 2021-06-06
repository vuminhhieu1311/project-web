import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { accountSettingStyle } from './account-setting-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/footer/SmallFooter';
import '../../components/my-profile-info/PersonalInfo';
import '../../components/my-profile-info/ChangePass';
import '../../components/Modal/UploadAvatar/UploadBackground'

class AccountSetting extends MaleficComponent {
    constructor() {
        super();
        location.href = 'http://localhost:8090/auth/realms/personal-management/account'
    }
}

customElements.define('app-account-setting', AccountSetting);
