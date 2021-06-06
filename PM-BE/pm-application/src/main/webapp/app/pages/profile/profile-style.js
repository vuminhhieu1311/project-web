
import { css } from '../../core/components/css-tag';

export const profileStyle = css`
    :host {
        font-size: 1rem;
        color: #5a5a5a;
    }

    main {
        display: flex;
        justify-content: center;
        background-color: rgb(245, 245, 245);
        margin-top: 60px;
    }
    
    #main-content {
        margin-top: 0;
        width: 840px;
        height: auto;
        display: flex;
        flex-direction: column;
        background-color: rgb(245, 245, 245);
        padding: 10px;
        margin-left: 50px;
        align-items: center;
    }
    
    .main-content-div {
        width: 97%;
        border: 0.1px solid #c7cfd4;
        border-radius: 10px;
        border-collapse: separate;
        padding: 1px;
        margin-bottom: 20px;
        background-color: white;
    }

    #basic-info-follow{
        background: linear-gradient(
            135deg
            , #1e4157f2 0%, #1597bb 100%);
        width: max-content;
        padding: 8px 15px;
        font-size: 18px;
        font-weight: bold;
        color:rgb(245, 245, 245);
        border-radius: 30px;
        margin: 10px;
        margin-left: 20px;
        cursor: pointer;
    }
    
    #basic-info-follow i{
        margin-right: 5px;
        font-size: 15px;
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
        margin-top: -11%;
        background-color:transparent;
        position: relative;
    }
    
    #main-avatar img {
        display: block;
        height: 150px;
        width: 150px;
        background-color: white;
        border: 1px solid #1597bb;
        border-radius: 50%;
        margin-left: 20px;
        cursor: pointer;
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

    #workplace {
        margin-top: 10px;
    }
    
    #info #personal-info {
        width: 70%;
        padding: 0 20px 10px 20px;
    }

    #info #work-place {
        width: 30%;
    }
    
    #workplace a {
        font-weight: bold;
        color: #265077;
        text-decoration: none;
        line-height: 30px
    }

    .profile-text {
        line-height: 20px;
        padding: 22px;
    }
    
    #workplace a:hover {
        color: #1597bb;
        text-decoration: underline;
    }

    .light {
        font-weight: lighter;
    }
    
    #contact-info {
        cursor: pointer;
        color: #265077;

    }
    
    #contact-info:hover {
        text-decoration: underline;
        color: #1597bb;
    }
    
    /*-------------------------------------------------*/
    
    .main-content-div h2 {
        padding: 20px;
        background-color: transparent;
        width:100%;
        border-bottom: 0.1px solid #c7cfd4;
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
        width: 100px;
    }
    
    .education__info {
        margin-left: 20px;
    }

    .education__info h4 {
        font-weight: lighter;
        line-height: 20px;
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

    .skill__list, .certification__list, .workExperience__list, 
        .project__list, .publication__list{
            display: grid;
            grid-template-columns: 1fr 1fr;
            background-color: white;
        }

    h3{
        cursor: pointer;
    }

    .education {
        .education__list, .interest__list {
            margin: 20px;
            background-color: white;
            display: flex;
            display: grid;
            grid-template-columns: 1fr 1fr;
            align-items: center;
        }
`;
