import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { profileStyle } from './profile-style';

import '../../components/layouts/Header/Header';
import '../../components/Sidebar/PeopleSidebar';
import '../../components/layouts/Footer/Footer';
import '../../components/Modal/ContactInfo/ContactInfo';

class Profile extends MaleficComponent {
    static get properties() {
        return {
            showModal: {type: Boolean}
        };
    }
    
    static get styles() {
        return [profileStyle];
    }
    
    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
    }
    
    handleOpenContactModal() {
        this.showModal = true;
    }
    
    handleCloseContactModal() {
        this.showModal = false;
    }
    
    scrollIntoEducation(e) {
        e.preventDefault();
        this.shadowRoot.querySelector('#education').scrollIntoView({
            block: 'center'
        });
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <app-header></app-header>

            <main>
                <div id="main-content">
                    <div class="main-content-div" id="basic-info-div">
                        <div id="background-avatar">
                            <img src="content/images/4853433.jpg" alt="">
                            <a class="link-icon" href="#">
                                <div class="material-icons md-24">
                                    photo_camera
                                </div>
                            </a>
                        </div>
            
                        <div id="main-avatar">
                            <img src="content/images/user.svg" style="height: 100px;width: 100px;" alt="">
                            <a class="link-icon" href="#">
                                <div class="material-icons md-24">
                                    edit
                                </div>
                            </a>
                        </div>
            
                        <div id="info">
                            <div id="personal-info">
                                <h1 id="personal-name">Name</h1>
                                <h3 id="personal-jobs">Jobs</h3>
                                <h4 id="personal-address">Address</h4>
                                <h4 id="contact-info" @click="${this.handleOpenContactModal}">Contact info</h4>
                            </div>
                
                            <div id="workplace">
                                <a style="cursor: pointer" @click="${this.scrollIntoEducation}">
                                    <p>Name of school of company,etc.</p>
                                </a>
                            </div>
                        </div>
                    </div>
        
                    <app-contact-info .show="${this.showModal}" @close-modal="${this.handleCloseContactModal}"></app-contact-info>
        
                    <div class="main-content-div" id="experience">
                        <h2>Experience</h2>
                        <div class="certification">
                            <h3 class="certification__title">Certification</h3>
                            <div class="certification__list">
                
                            </div>
                        </div>
            
                        <div class="skill">
                            <h3 class="skill__title">Skill</h3>
                            <div class="skill__list">
                            </div>
                        </div>
            
                        <div class="workExperience">
                            <h3 class="workExperience__title">Work Experience</h3>
                            <div class="workExperience__list">
                
                            </div>
                        </div>
            
                        <div class="project">
                            <h3 class="project__title">Project</h3>
                            <div class="project__list">
                            </div>
                        </div>
            
                        <div class="publication">
                            <h3 class="publication__title">Publication</h3>
                            <div class="publication__div">
                            </div>
                        </div>
                    </div>
        
                    <div class="main-content-div" id="education">
                        <h2>Education</h2>
                        <div class="education">
                            <img class="education__logo" src="content/images/HUST_logo.png">
                            <div class="education__info">
                                <h3 class="education__info__name">Hanoi University of Science and Technology</h3>
                                <h4 class="education__info__degree">Engineer's degree, Computer Engineering</h4>
                                <h4 class="education__info__time">2018 - 2023</h4>
                            </div>
                        </div>
                    </div>
        
                    <div class="main-content-div" id="interest">
                        <h2>Interest</h2>
                        <div class="interest">
                            <div class="interest__page">
                                <a href="#" class="interest__page__link">
                                    <img class="interest__page__logo" src="content/images/HUST_logo.png">
                        
                                    <div class="interest__page__info">
                                        <h3>Hanoi University of Science and Technology</h3>
                                    </div>
                                </a>
                
                            </div>
                        </div>
                    </div>
                </div>
    
                <app-people-sidebar></app-people-sidebar>
            </main>
            <app-footer></app-footer>
        `;
    }
}

customElements.define('app-profile', Profile);
