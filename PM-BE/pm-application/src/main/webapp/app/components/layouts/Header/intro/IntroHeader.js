import MaleficComponent from '../../../../core/components/MaleficComponent';
import { html } from '../../../../core/components/malefic-html';
import { getLoginUrl } from '../../../../shared/utils/url-utils';
import { createRegisterUrl } from '../../../../core/keycloak/keycloak';
import { introHeaderStyle } from './intro-header-style';

import '../../../Button/Button';
import { commonStyles } from '../../../../shared/styles/common-styles';

class IntroHeader extends MaleficComponent {
    static get styles() {
        return [introHeaderStyle];
    }
    
    render() {
        const registerUrl = createRegisterUrl({
            realm: 'personal-management',
            url: `http://localhost:8090/auth`,
            clientId: 'pm_app',
            redirectUrl: getLoginUrl()
        });
        
        return html`
            ${commonStyles}
            
            <header>
                <a href="/">
                    <img class="logo" src="content/images/Logo_official.png" alt="JobsGo Logo"/>
                </a>
        
                <ul class="main-nav">
                    <li><a href="${getLoginUrl()}">Sign In</a></li>
                    <li><a href="${registerUrl}">Sign Up</a></li>
                </ul>
        
                <div class="clearfix"></div>
                <div class="row">
                    <div class="heading-main-box">
                        <h1>
                            Open jobs<br/>
                            People hiring
                        </h1>
                        <div class="hot-tags">
                            <app-button btnclass="btn">Search Jobs</app-button>
                            <app-button btnclass="btn">Find People</app-button>
                            <app-button btnclass="btn">Make CV</app-button>
                        </div>
                    </div>
                </div>
            </header>
        `;
    }
}

customElements.define('app-intro-header', IntroHeader);
