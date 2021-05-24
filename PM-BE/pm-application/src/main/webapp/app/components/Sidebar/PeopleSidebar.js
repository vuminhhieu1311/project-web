import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { commonStyles } from '../../shared/styles/common-styles';
import { peopleSidebarStyle } from './people-sidebar-style';

class PeopleSidebar extends MaleficComponent {
    static get styles() {
        return [peopleSidebarStyle];
    }
    
    render() {
        return html`
            ${commonStyles}
            
            <aside>
                <div class="quote">
                    <img src="content/images/20943749.jpg" style="height: 195px;width: 195px;">
                </div>
                <div class="suggest">
                    <h2 class="suggest__title">People you may know</h2>
                    <a class="suggest__link" href="#">
                        <div class="suggest__info">
                            <img class="suggest__info__avatar" src="content/images/user.svg">
                            <h3 class="suggest__info__name">Name 1</h3>
                        </div>
                    </a>
            
                    <a class="suggest__link" href="#">
                        <div class="suggest__info">
                            <img class="suggest__info__avatar" src="content/images/user.svg">
                            <h3 class="suggest__info__name">Name 2</h3>
                        </div>
                    </a>
            
                    <a class="suggest__link" href="#">
                        <div class="suggest__info">
                            <img class="suggest__info__avatar" src="content/images/user.svg">
                            <h3 class="suggest__info__name">Name 3</h3>
                        </div>
                    </a>
                </div>
            </aside>
        `;
    }
}

customElements.define('app-people-sidebar', PeopleSidebar);
