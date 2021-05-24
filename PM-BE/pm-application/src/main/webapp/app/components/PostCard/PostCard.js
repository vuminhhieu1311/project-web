import MaleficComponent from '../../core/components/MaleficComponent';
import { html } from '../../core/components/malefic-html';
import { postCardStyle } from './post-card-style';
import { commonStyles } from '../../shared/styles/common-styles';

import '../Button/Button';

class PostCard extends MaleficComponent {
    static get styles() {
        return [postCardStyle];
    }

    static get properties() {
        return {
            accountImg: {type: String},
            accountName: {type: String},
            numFollowers: {type: Int32Array},
            time: {type: Date},
            postText: {type: String},
            postImg: {type: String}
        };
    }

    constructor() {
        super();
        this.accountImg = '';
        this.accountName = 'Vu Minh Hieu';
        this.numFollowers = 0;
        this.time = '2hr';
        this.postText = 'This is my first post';
        this.postImg = 'content/images/engineering2.jpeg';
    }

    render() {
        return html`
            ${commonStyles}
            <div class="news-card">
                <div class="news-header">
                    <div class="poster">
                        <img src="${this.accountImg}" alt="">
                        <div class="poster-info">
                            <div style="font-weight: bold; font-size: 18px;">${this.accountName}</div>
                            <div style="font-size: 12px;">${this.numFollowers} followers</div>
                            <div style="font-size: 12px;">${this.time}</div>
                        </div>
                    </div>
                </div>

                <div class="recruit-info">
                    <div>${this.postText}</div>
                    <img src="${this.postImg}" alt="">
                </div>

                <div class="news-react">
                    <div class="react">
                        <button class="react-icon"><i class="fas fa-thumbs-up"></i>Like</button>
                        <button class="react-icon"><i class="fas fa-comment-dots"></i>Comment</button>
                    </div>
                </div>
            </div>
        `;
    }
}

customElements.define('post-card', PostCard);
