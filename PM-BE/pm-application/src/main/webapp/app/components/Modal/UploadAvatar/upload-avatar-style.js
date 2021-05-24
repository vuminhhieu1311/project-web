import { css } from '../../../core/components/css-tag';

export const UploadAvatarStyle = css`
    avt-modal {
        z-index: 10;
        background-color: #fff;
        position: fixed;
        top: 12%;
        left: 20%;
        transform: translate(-50%, -50%);
        transition: 200ms ease-in-out;
        padding: 15px 15px;
        left: 27%;
    }

    .avt-modal {
        padding: 5px 10px;
        width: 100%;
        height: 400px;
    }

    .avt-modal .wrapper {
        height: 65%;
        width: 100%;
        border-radius: 10px;
        border: 2px dashed #c2cdda;
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        position: relative;
    }


    .wrapper .image {
        position: absolute;
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
    }

    .wrapper .image img {
        border: 1px solid #265077;
        border-radius: 50%;
        height: 200px;
        width: 200px;
        object-fit: cover;
    }

    .wrapper .icon {
        font-size: 100px;
        color: hsl(203, 49%, 23%, 0.95);
    }

    .wrapper .text {
        font-size: 1.2rem;
        color: #265077;
        font-weight: 500;
    }

    #close-button {
        font-size: 20px;
        color: #1e4157f2;
        cursor: pointer;
        margin-bottom: 3%;
        margin-left: 96%;
    }
    
    .custom-btn,
    .save-btn,
    .cancel-btn {
        border: none;
        font-size: 16px;
        letter-spacing: 1px;
        cursor: pointer;
        outline: none;
        border-radius: 7px;
        margin-top: 3%;
        transition: all 0.3s;
    }
    
    .custom-btn {
        width: 100%;
        height: 10%;
        display: block;
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        font-weight: 500;
        color: #fff;
    }
    
    .custom-btn:hover,
    .custom-btn:active {
        background: linear-gradient(135deg, #1597bb 0%, #265077 100%);
    }
    
    .save-btn {
        width: 47%;
        height: 10%;
        background-color: #E8E9EB;
        color: #1e4157f2;
    }
    
    .cancel-btn {
        width: 47%;
        height: 10%;
        background-color: #E8E9EB;
        color: #1e4157f2;
        margin-right: 5%;
    }
    
    .save-btn:hover,
    .cancel-btn:hover,
    .close-button:active, 
    .cancel-btn:active {
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        color: #fff
    }

    .sk-chase {
        width: 40px;
        height: 40px;
        position: relative;
        animation: sk-chase 2.5s infinite linear both;
        top: -65%;
        left: 45%;
        opacity: 0;
    }

    .sk-chase-dot {
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0;
        top: 0;
        animation: sk-chase-dot 2.0s infinite ease-in-out both;
    }

    .sk-chase-dot:before {
        content: '';
        display: block;
        width: 25%;
        height: 25%;
        background-color: #1597bb;
        border-radius: 100%;
        animation: sk-chase-dot-before 2.0s infinite ease-in-out both;
    }

    .sk-chase-dot:nth-child(1) {
        animation-delay: -1.1s;
    }

    .sk-chase-dot:nth-child(2) {
        animation-delay: -1.0s;
    }

    .sk-chase-dot:nth-child(3) {
        animation-delay: -0.9s;
    }

    .sk-chase-dot:nth-child(4) {
        animation-delay: -0.8s;
    }

    .sk-chase-dot:nth-child(5) {
        animation-delay: -0.7s;
    }

    .sk-chase-dot:nth-child(6) {
        animation-delay: -0.6s;
    }

    .sk-chase-dot:nth-child(1):before {
        animation-delay: -1.1s;
    }

    .sk-chase-dot:nth-child(2):before {
        animation-delay: -1.0s;
    }

    .sk-chase-dot:nth-child(3):before {
        animation-delay: -0.9s;
    }

    .sk-chase-dot:nth-child(4):before {
        animation-delay: -0.8s;
    }

    .sk-chase-dot:nth-child(5):before {
        animation-delay: -0.7s;
    }

    .sk-chase-dot:nth-child(6):before {
        animation-delay: -0.6s;
    }

    @keyframes sk-chase {
        100% {
            transform: rotate(360deg);
        }
    }

    @keyframes sk-chase-dot {

        80%,
        100% {
            transform: rotate(360deg);
        }
    }

    @keyframes sk-chase-dot-before {
        50% {
            transform: scale(0.4);
        }

        100%,
        0% {
            transform: scale(1.0);
        }
    }

    .loading {
        opacity: 0.3;
    }

    .showSpinner {
        opacity: 1;
    }
`;
