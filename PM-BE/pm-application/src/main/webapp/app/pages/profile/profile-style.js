import { css } from '../../core/components/css-tag';

export const profileStyle = css`
    :host {
        color: black;
    }

    main {
        display: flex;
        justify-content: center;
        background-color: rgb(245, 245, 245);
        margin-top: 60px;
    }
    
    #main-content {
        margin-top: 0;
        width: 800px;
        height: auto;
        display: flex;
        flex-direction: column;
        background-color: rgb(245, 245, 245);
        padding: 10px;
        margin-left: 50px;
        align-items: center;
    }
    
    .main-content-div {
        width: 780px;
        border:0.5px solid gray;
        border-radius: 10px;
        border-collapse: separate;
        padding: 1px;
        margin-bottom: 20px;
        background-color: white;
    }
    
    
    #background-avatar {
        position: relative;
        width: 100%;
        height: 210px;
        background-color: white;
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
    }
    
    #background-avatar img {
        display: block;
        width: 100%;
        height: 200px;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    
    #background-avatar a {
        color: black;
        position: absolute;
        top:30px;
        right: 30px;
        transform: translate(-50%, -50%);
        border-radius: 50%;
        background-color: white;
        padding: 20px;
        cursor: pointer;
    }
    
    #background-avatar a div {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 50%;
    }
    
    #main-avatar {
        margin-top: -50px;
        background-color:transparent;
        position: relative;
    }
    
    #main-avatar img {
        display: block;
        background-color: white;
        border: 1px solid black;
        border-radius: 50%;
        margin-left: 20px;
    }
    
    #main-avatar a {
        color: black;
        position: absolute;
        cursor: pointer;
        top: 60px;
        right: 30px;
        transform: translate(-50%, -50%);
        padding: 20px;
        border-radius: 50%;
        box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.2);
    }
    
    #main-avatar a div {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 50%;
    }
    
    #info {
        width: 100%;
        height: auto;
        background-color: white;
        display: flex;
        margin-top: 10px;
        border-bottom-left-radius: 25px;
        border-bottom-right-radius: 25px;
    }
    
    #info div {
        width: 50%;
        padding: 0 20px;
    }
    
    #workplace a {
        font-weight: bold;
        color: black;
        text-decoration: none;
    }
    
    #workplace a:hover {
        color: blue;
        text-decoration: underline;
    }
    
    #contact-info {
        cursor: pointer;
        color: blue;
    }
    
    #contact-info:hover {
        text-decoration: underline;
    }
    
    /*-------------------------------------------------*/
    
    .main-content-div h2 {
        padding: 20px;
        background-color: transparent;
        width:100%;
        border-bottom: 0.2px solid gray;
    }
    .education {
        margin: 20px;
        background-color: white;
        display: flex;
        align-items: center;
    }
    
    .education__logo {
        display: inline-block;
        height: 100px;
        width: 70px;
    }
    
    .education__info {
        margin-left: 20px;
    }
    
    #experience > div {
        background-color: rgb(245, 244, 244);
        margin: 10px;
        border-radius: 8px;
        padding: 10px;
    }
    
    .interest {
        display: flex;
    }
    
    .interest__page {
        margin: 20px;
        width: 100%;
        background-color: white;
    }
    
    .interest__page__link {
        display: block;
        width: 100%;
        background-color: white;
        display: flex;
        align-items: center;
        color: black;
        text-decoration: none;
    }
    
    .interest__page__link:hover {
        text-decoration: underline;
    }
    
    .interest__page__logo {
        display: inline-block;
        height: 100px;
        width: 70px;
    }
    
    .interest__page__info {
        background-color: white;
        margin: 20px;
    }
    
    @media (max-width: 1024px) {
        main {
            display: block;
            background-color: rgb(245, 245, 245);
        }
        
        #main-content {
            margin-left: auto;
            margin-right: auto;
        }
    }

`;
