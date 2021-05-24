import { css } from '../../../core/components/css-tag';

export const uploadPostStyle = css`
    .custom-btn, .cancel-btn{
        width: 30%;
        height: 10%;
        font-weight: 500;
        text-align: center;
        padding: 5px;
        border-radius: 7px;
        margin-top: 3%;
        transition: all 0.3s;
        font-size: 16px;
        letter-spacing: 1px;
        cursor: pointer;
        border: none;
    }

    .custom-btn {
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        color: #fff;
    }

    .cancel-btn {
        background-color: #E8E9EB;
        color: #1e4157f2;
    }

    .cancel-btn:hover {
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        color: #fff
    }

    .custom-btn:hover,
    .custom-btn:active {
        background: linear-gradient(135deg, #1597bb 0%, #265077 100%);
    }

    .button {
        display: flex;
        justify-content: space-between;
    }

    .button i {
        margin-right: 5px;
    }

    .post__edit__header{
        display: flex;
        padding-bottom: 10px;
        border-bottom: 1px solid black;
    }
    
    .post__edit__header h3{
        flex: 1 1 auto;
    }

    .post__edit__close{
        border-radius: 50%;
        height: 25px;
        width: 25px;
        border: 1px solid black;
        position: relative;
        cursor: pointer;
    }

    .post__edit__close i{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    
    .post__edit__close:hover {
        color: #265077;
    }

    .post__edit__text textarea{
        display: block;
        resize: none;
        width: 100%;
        height: 80px;
        outline: none;
        margin: auto;
        padding: 10px;
        border: none;
    }

    .avt-modal {
        padding: 5px 10px;
        width: 100%;
        height: 400px;
    }

    .avt-modal .wrapper {
        height: 60%;
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
        height: 100%;
        width: 100%;
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
