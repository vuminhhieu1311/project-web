import { css } from '../../../core/components/css-tag';

export const footerStyle = css`
    :host {
        font-size: 20px;
    }

    footer {
        background-image: linear-gradient(
          45deg,
          rgba(2, 33, 64, 0.8) 55%,
          rgba(45, 95, 93, 0.85)
        ),
        url('content/images/background_footer.jpeg');
        background-size: cover;
        background-position: center; /* can giua */
        background-attachment: fixed;
        color: #c5bebe;
        padding: 3%;
    }
    
    .col-footer {
        line-height: 160%;
        margin-bottom: -1%;
        margin-top: -1%;
    }
    
    .text-footer {
        list-style-type: none;
    }
    
    .text-footer a {
        color:#c5bebe;
        font-weight: lighter;
        text-decoration: none;
        font-size: 80%;
    }
    
    .text-footer a:hover,
    .text-footer a:active {
        text-decoration: underline;
    }
    
    .information {
        list-style: none;
        line-height: 200%;
        word-spacing: 1px;
        font-size: 80%;
    }
    
    .information .small-icon {
        color: #94afc9;
        padding-right: 5px;
    }
    
    .information .small-icon:hover,
    .information .small-icon:hover {
        transform: scale(1.3);
    }
    
    .social-icons {
        list-style: none;
    }
    
    .social-icons li {
        display: inline-block;
        font-size: 180%;
        color: #94afc9;
        margin-right: 15px;
        margin-top: 15px;
    }
    
    .social-icons li i {
        transition: all .3s;
        cursor: pointer;
    }
    
    .social-icons li:hover,
    .social-icons li:active {
        transform: scale(1.3);
        color: #e67e22;
    }
`;
