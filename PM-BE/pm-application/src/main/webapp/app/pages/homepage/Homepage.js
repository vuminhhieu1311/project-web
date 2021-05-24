import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { homepageStyle } from './homepage-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/Footer/Footer';
import '../../components/Button/Button';
import '../../components/PostCard/PostCard';
import '../../components/Modal/UploadPost/UploadPost';

class Homepage extends MaleficComponent {
    static get styles() {
        return [homepageStyle];
    }
    
    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
    }

    static get properties() {
        return {
            showModal: {type: Boolean}
        };
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
            <app-upload-post .show="${this.showModal}" @close-modal="${this.closeModal}"></app-upload-post>
            <main class="main">
                <div class="main__content news">
                    <div class="status">
                        <div class="status__post">
                            <div class="post__avatar">
                                <img src="content/images/engineering2.jpeg">
                            </div>
                            <div class="post__text" @click="${this.handleToggleModal}">
                                <div>Start A Post</div>
                            </div>
                        </div>
                        <div class="status__tools">
                            <div class="status__tool__list">
                                <div class="status__tool__icon" @click="${this.handleToggleModal}">
                                    <i class="far fa-image"></i>
                                    <span>Image</span>
                                </div>
                            </div>
                            <div class="status__tool__list">
                                <div class="status__tool__icon">
                                    <i class="fas fa-video"></i>
                                    <span>Video</span>
                                </div>
                            </div>
                            <div class="status__tool__list">
                                <div class="status__tool__icon">
                                    <i class="fas fa-calendar-plus"></i>
                                    <span>Event</span>
                                </div>
                            </div>
                            <div class="status__tool__list">
                                <div class="status__tool__icon">
                                    <i class="far fa-newspaper"></i>
                                    <span>Article</span>
                                </div>
                            </div>
                        </div>
                    </div>
        
                    <div class="news__content">
                        <post-card
                            accountImg="content/images/engineering2.jpeg"
                            accountName="Vu Minh Hieu";
                            numFollowers = 10000
                            time = '3hr'
                            postText = 'This is my first post'
                            postImg = 'content/images/engineering2.jpeg'
                        ></post-card>
                        <post-card
                            accountImg="content/images/avatar.png"
                            accountName="Vu Minh Hieu";
                            numFollowers = 900
                            time = '5w'
                            postText = 'This is my second post'
                            postImg = 'content/images/engineering2.jpeg'
                        ></post-card>
                    </div>
                </div>
        
                <div class="main__content ads">
                    <div class="quote">
                        <img src="content/images/background_footer.jpeg" style="height: 100%;width: 100%; border-radius: 10px;">
                    </div>
                    <div class="suggest">
                        <h3 class="suggest__title">Top Company</h3>
                        <a class="suggest__link" href="#">
                            <div class="suggest__info">
                                <img class="suggest__info__avatar" src="content/images/engineering2.jpeg">
                                <h4 class="suggest__info__name">Name 1</h4>
                            </div>
                        </a>
        
                        <a class="suggest__link" href="#">
                            <div class="suggest__info">
                                <img class="suggest__info__avatar" src="content/images/engineering2.jpeg">
                                <h4 class="suggest__info__name">Name 2</h4>
                            </div>
                        </a>
        
                        <a class="suggest__link" href="#">
                            <div class="suggest__info">
                                <img class="suggest__info__avatar" src="content/images/engineering2.jpeg">
                                <h4 class="suggest__info__name">Name 3</h4>
                            </div>
                        </a>
                    </div>
        
                    <div class="web__info">
                        <a href="#">About Us</a>
                        <a href="#">Contact Us</a>
                        <a href="#">Privacy Policy</a>
                        <a href="#">Complaint Handling</a>
                        <a href="#">Term & Conditions</a>
                        <a href="#">Help Center</a>
                        <a href="#">Advertising</a>
                    </div>
                </div>
            </main>    
        `;
    }
}

customElements.define('app-homepage', Homepage);
