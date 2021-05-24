import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { CVTemplateStyle } from './CV-template-style';
import { CVCommonStyles } from './CV-lib-style';

import '../../components/layouts/Header/Intro/IntroHeader';
import '../../components/layouts/Footer/Footer';
import '../../components/Button/Button';

class CVTemplate extends MaleficComponent {
    static get styles() {
        return [CVTemplateStyle];
    }
    
    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
    }
    
    render() {
        return html`
            ${CVCommonStyles}
            <div class="header">
        <div class="header__basic__info__avatar">
            <i class="fas fa-user cv__header__avatar__icon"></i>
        </div>

        <div class="header__basic__info">
            <div>
                <p>Your name</p>
                <p>Your current job</p>
            </div>
            <div>
                <p>Mail: 123@mail.com</p>
                <p>Phone: 123456</p>
            </div>

            <div>
                <p>Country: VietNam</p>
            </div>
        </div>
    </div>
    <div class="main">
        <div class="main__info">
            <div class="main__info__header">
                Skills
            </div>
            <div class="main__info__content">
                <ul>
                   <li>C#</li>
                   <li>C/C++</li>
                   <li>Java</li> 
                </ul>
            </div>
            
        </div>
        <div class="main__info">
            <div class="main__info__header">
                Publication
            </div>
            
        </div><div class="main__info">
            <div class="main__info__header">
                Projects
            </div>
            
        </div><div class="main__info">
            <div class="main__info__header">
                Education
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    2019-2023:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    HUST
                </div>
            </div>
            
        </div><div class="main__info">
            <div class="main__info__header">
                Certification
            </div>
            <div class="main__info__content">
                <ul>
                    <li>Certification 1</li>
                    <li>Certification 2</li>
                    <li>Certification 3</li> 
                 </ul>
            </div>
            
        </div>
       
        <div class="main__info">
            <div class="main__info__header">
                Work experiences
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    2020:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    Company A
                </div>
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    2021:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    Company B
                </div>
            </div>
        </div>
        <div class="main__info">
            <div class="main__info__header">
                Contact info
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Email:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    abc@mail.com
                </div>
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Phone:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    123456789
                </div>
            </div>
        </div>
        <div class="main__info">
            <div class="main__info__header">
                Contact info
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Email:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    abc@mail.com
                </div>
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Phone:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    123456789
                </div>
            </div>
        </div>
        <div class="main__info">
            <div class="main__info__header">
                Contact info
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Email:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    abc@mail.com
                </div>
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Phone:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    123456789
                </div>
            </div>
        </div>
        <div class="main__info">
            <div class="main__info__header">
                Contact info
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Email:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    abc@mail.com
                </div>
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Phone:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    123456789
                </div>
            </div>
        </div>
        <div class="main__info">
            <div class="main__info__header">
                Contact info
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Email:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    abc@mail.com
                </div>
            </div>
            <div class="main__info__content">
                <div class="main__info__content__title" contenteditable="true">
                    Phone:
                </div>
                <div class="main__info__content__detail" contenteditable="true">
                    123456789
                </div>
            </div>
        </div>
    </div>
        `;
    }
}

customElements.define('cv-template', CVTemplate);


