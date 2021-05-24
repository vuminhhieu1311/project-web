import { css } from '../../core/components/css-tag';

export const accountSettingStyle = css`
    :host {
        font-size: 1rem;
    }

    .container {
        background-color: #F0F2EF;
        margin: 0 auto;
        position: relative;
        padding-top: 8%
    }

    h5 {
        font-size: 0.9rem;
        font-weight: lighter;
        line-height: 30px;
        color: #777;
        padding-bottom: 10%;
        padding-top: 2px;
    }

    h1,
    h3 {
        color: #1e4258;
    }

    h3 {
        font-weight: lighter;
    }

    .input {
        position: relative;
        text-align: left;
        border: 1px solid #ccd0d5;
        border-radius: 4px;
        width: 90%;
        height: 30px;
        line-height: 20px;
        margin-bottom: 2%;
        color: #777;
        padding: 10px;
        outline: none;
    }

    input:focus {
        border: 1px solid #265077;
    }

    .selector {
        background-position: right 5px center;
        background-repeat: no-repeat;
        background-size: 14px;
        padding-right: 5%;
        height: 30px;
        margin-bottom: 2%;
        width: 25%;
        color: #777;
        border: 1px solid #ccd0d5;
        border-radius: 4px;
        display: inline-block;
    }

    .gender {
        margin-bottom: 1%;
    }

    .select {
        margin-top: 0px;
        margin-right: 4px;
        border-radius: 4px;
        border-width: 1px;
        display: inline-block;
        line-height: 18px;
        color: #777;
        font-size: 15px;
        padding: 0 10px 0 3px;
    }

    .tab-show {
        transition: all .5s ease-in;
        display: none;
    }

    #tab-show-active {
        display: block
    }

    .sidemenu {
        background-color: transparent;
        margin-left: 6%;
    }

    .sidemenu nav a {
        list-style: none;
        display: block;
        padding: 9%;
        color: #1e4258;
        transition: all .3;
    }

    .sidemenu nav a:hover,
    .sidemenu nav a:active {
        transform: scale(1.1);
        cursor: pointer;
        color: #2d6187;
        background-color: #d8e3e7;
    }

    #tab-active {
        color: #2d6187;
        background-color: #d8e3e7;
        transform: scale(1.1);
    }

    .setting-title {
        margin-left: 10px;
    }

    .account-info .profile {
        background-color: white;
        padding: 2% 5%;
        line-height: 40px;
        margin-bottom: 4%;
    }

    .avt-image {
        
        align-items: center;
        justify-content: center;
    }

    #main-avatar {
        margin: 0 auto;
        background-color: transparent;
        position: relative;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    #main-avatar img {
        display: block;
        width: 80%;
        height: 150px;
        background-color: white;
        border: 1px solid #2d6187;
        border-radius: 50%;
        margin-bottom: 7%;
        object-fit: cover;
    }

    .tab-show {
        transition: all .5s ease-in;
        display: none;
    }

    .btn-add {
        text-decoration: none;
        color: white;
        padding: 10px 10px;
        border-radius: 20px;
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        display: inline-block;
        text-align: center;
        transition: all 0.3s;
        font-weight: bold;
        margin-left: 20%;
        font-size: 80%;
        transition: 0.3s;
    }

    .btn-add:hover,
    .btn-add:active {
        background: linear-gradient(135deg, #1597bb 0%, #203949f2 100%);
        transform: scale(1.2);
    }

    /*--------------------------UPLOAD IMAGE POPUP CSS--------------------------------*/

    .avt-modal {
        z-index: 10;
        background-color: #fff;
        position: fixed;
        top: 12%;
        left: 20%;
        transform: translate(-50%, -50%);

        transition: 200ms ease-in-out;
        transform: scale(0);
        padding: 15px 15px;
        height: 85%;
        width: 45%;
        left: 27%;
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

    .container #custom-btn,
    .container .close-button,
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

    .container #custom-btn {
        width: 100%;
        height: 10%;
        display: block;
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        font-weight: 500;
        color: #fff;
    }

    .container #custom-btn:hover,
    .container #custom-btn:active {
        background: linear-gradient(135deg, #1597bb 0%, #265077 100%);
    }

    .container .close-button {
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

    .container .close-button:hover,
    .cancel-btn:hover,
    .container .close-button:active .cancel-btn:active {
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        color: #fff
    }

    .btn-save,
    .btn-cancel {
        border: none;
        font-size: 16px;
        letter-spacing: 1px;
        padding: 7px;
        width: 100px;
        cursor: pointer;
        outline: none;
        transition: all 0.3s;
        margin-top: 3%;
    }

    .btn-save {
        border-radius: 7px 0 0 7px;
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        font-weight: 500;
        color: #fff;
        margin-right: 0.5%;
    }

    .btn-cancel {
        border-radius: 0 7px 7px 0;
        margin-top: 3%;
        background: #ccd0d5;
        font-weight: 500;
        color: #1e4258;
    }

    .btn-save:hover,
    .btn-save:active {
        background: linear-gradient(135deg, #1597bb 0%, #1e4157f2 100%);
        transform: scale(1.1);
    }

    .btn-cancel:hover,
    .btn-cancel:active {
        background: linear-gradient(135deg, #1597bb 0%, #1e4157f2 100%);
        transform: scale(1.1);
        color: #fff;
    }

    /*------------Loading with spinners CSS-----------*/
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
