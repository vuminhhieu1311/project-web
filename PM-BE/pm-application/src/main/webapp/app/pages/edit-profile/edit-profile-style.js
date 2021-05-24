import { css } from '../../core/components/css-tag';

export const editProfileStyle = css`
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

    .btn-upload {
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
        cursor: pointer;
        border: none;
    }

    .btn-upload:hover,
    .btn-upload:active {
        background: linear-gradient(135deg, #1597bb 0%, #203949f2 100%);
        transform: scale(1.2);
    }
`;
