import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { homepageStyle } from './homepage-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../../components/layouts/Header/Header';
import '../../components/layouts/Footer/Footer';
import '../../components/Button/Button';
import '../../components/PostCard/PostCard';
import '../../components/Modal/UploadPost/UploadPost';
import getProfile from '../../api/getProfile';
import { transformImage } from '../../shared/utils/url-utils';
import getNewsFeed from '../../api/getNewsFeed';

class Homepage extends MaleficComponent {
    static get styles() {
        return [homepageStyle];
    }

    static get properties() {
        return {
            showModal: { type: Boolean },
            profile: { type: Object },
            imgAvt: { type: String },
            postList: {type: Array}
        };
    }

    constructor() {
        super();
        window.addEventListener('beforeunload', () => {
            window.scrollTo(0, 0);
        });
        this.showModal = false;
        this.postList = [];
    }

    handleToggleModal() {
        this.showModal = !this.showModal;
    }

    closeModal() {
        this.showModal = false;
    }

    async connectedCallback() {
        super.connectedCallback()
        getProfile()
            .then(res => {
                this.profile = res;
                this.imgAvt = res.user.imageUrl;
                // console.log(res.user.imageUrl)
            })
            .catch(e => console.log(e));

        getNewsFeed()
            .then(res => {
                this.postList = res._embedded ? res._embedded.postList : [];
            })
            .catch(e => console.log(e));
    }


    render() {
        return html`
            ${commonStyles}
            <app-header></app-header>
            <app-upload-post .show="${this.showModal}" @close-modal="${this.closeModal}" 
                placeHolder="Type something" 
                typePost="Upload your post"
                placeHolderImage="./content/images/avatar.png">
                
                </app-upload-post>
            <main class="main">
                <div class="main__content news">
                    <div class="status">
                        <div class="status__post">
                            <div class="post__avatar">
                                <img src="${this.imgAvt ? transformImage(this.imgAvt, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35') : 'content/images/avatar.png'}">
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
                        ${this.postList.slice(0).reverse().map((e) => {
                            return html`
                        <post-card
                        accountName="${e.author.firstName} ${e.author.lastName}"
                        accountImg="${e.author.imageUrl ? transformImage(e.author.imageUrl, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35') : 'content/images/avatar.png'}"
                        numFollowers=10
                        time="5w"
                        postText="${e.content}"
                        postId="${e.id}">    
                        </post-card>
                        `})}
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
