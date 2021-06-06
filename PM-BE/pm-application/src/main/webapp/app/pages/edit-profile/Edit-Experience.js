import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { editProfileStyle } from './edit-profile-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/footer/SmallFooter';
import '../../components/my-profile-info/PersonalInfo';
import '../../components/my-profile-info/ChangePass';
import '../../components/Modal/UploadAvatar/UploadBackground';
import '../../components/Button/Button';
import '../../components/my-profile-info/WorkExperience';
import '../../components/my-profile-info/Education';
import '../../components/my-profile-info/Certification';
import '../../components/my-profile-info/Skill';

class EditExperience extends MaleficComponent {
    static get properties() {
        return {
            
            showModal: {type: Boolean},
        };
    }

    static get styles() {
        return [editProfileStyle];
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
     
        this.showModal = false;
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }
    
    closeModal() {
        this.showModal = false;
    }

    render() {
        return html`
            ${commonStyles}
            <app-header></app-header>
            <section class="container">
                <div class="row">
                    <div class="col span-1-of-5 sidemenu">
                        <nav>
                      
                        <div>
                        <i class="fas fa-user"></i>
                        <app-link href="/edit-profile/personal">
                            <div class="app-link" class="setting-title">Personal Info</div>
                        </app-link>
                        </div>
                       
    
                    <div id="tab-active">
                    <i class="fas fa-briefcase"></i>
                        <app-link href="/edit-profile/experience">
                            
                            <div class="app-link" class="setting-title">Work Experience</div>
                        </app-link>
                    </div>
                        
    
                        <div>
                        <i class="fas fa-graduation-cap"></i>
                        <app-link href="/edit-profile/education"">
                            <span class="app-link" class="setting-title">Education</span>
                        </app-link>
                        </div>
                        
    
                        <div>
                        <i class="fas fa-certificate"></i>
                        <app-link href="/edit-profile/certification">
                            <span class="app-link" class="setting-title">Certification</span>
                        </app-link>
                        </div>
                        
    
                        <div>
                        <i class="fas fa-american-sign-language-interpreting"></i>
                        <app-link href="/edit-profile/skill" >
                            <span class="app-link" class="setting-title">Skill</span>
                        </app-link>
                        </div>


                        </nav>
                    </div>

                    <div class="col span-4-of-5 account-info">
                       
                        <div class="profile tab-show" id="tab-show-active">
                            <work-experience></work-experience>
                        </div>         
                    </div>
                </div>
            </section>
            <app-small-footer></app-small-footer>
        `;
    }
}

customElements.define('app-edit-experience', EditExperience);
