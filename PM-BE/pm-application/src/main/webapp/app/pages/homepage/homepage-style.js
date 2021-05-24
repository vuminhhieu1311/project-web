import { css } from '../../core/components/css-tag';

export const homepageStyle = css`
    :host {
        font-size: 1rem;
    }
    /*MAIN-------------------------------------------------------*/
    .main{
        padding-top: 75px;
        position: relative;
        width: 100%;
        height: max-content;
        min-height: 100%;
        background-color: #F0F2EF;
        opacity: 1;
        display: flex;
        justify-content: center;
    }

    .news{
        width: 750px;
        min-width: 400px;
        background-color: bisque rgb(201, 201, 201);
        height: max-content;
    }

    .ads{
        width: 250px;
        background-color: brown rgb(201, 201, 201);
    }

    /*MAIN__NEWS---------------------------------------------------*/
    .status{
        display:flex;
        flex-direction: column;
        background-color: white;
        margin: 10px;
        padding: 10px 10px 0 10px;
        border: 1px solid rgb(201, 201, 201);
        border-radius: 8px;
    }

    .status__post{
        display: flex;
    }

    .post__avatar img{
        border-radius: 50%;
        height: 50px;
        width: 50px;
    }

    .post__text{
        position: relative;
        background-color:whitesmoke;
        flex: 1 1 auto;
        width: 90%;
        border-radius: 20px;
        border: 1px solid #c7cfd4;
        height: 40px;
        cursor: pointer;
        margin: 5px 10px;
    }

    .post__text div{
        background-color: whitesmoke; 
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        margin-left: 20px;
    }

    .status__tools{
        background-color: violet white;
        display: flex;
        justify-content: space-between;
    }

    .status__tool__list{
        background-color: yellowgreen white;
        width: 150px;
        height: 40px;
        text-align: center;
        font-size: 20px;
        border-radius: 5px;
        position: relative;
    }

    .status__tool__icon{
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
        display: flex;
        justify-content: space-between;
        align-items: center;
        cursor: pointer;
    }

    .status__tool__icon > span{
        margin-left: 5px;
        font-size: 15px;
    }

    .status__tool__list:hover{
        background-color: rgb(201, 201, 201);
    }
    .news{
        display: flex;
        flex-direction: column;
    }

    .news__content{
        height: 100%;
    }


    /*-------------------*/
    .post__edit__container{
        position: fixed;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.5);
        display: none;
    }

    .post__edit__popup{
        position: fixed;
        left: 50%;
        top:90px;
        transform: translateX(-50%);
        height: max-content;
        width: 500px;
        z-index: 10;
        background-color: white;
        border-radius: 8px;
        border: 1px solid black;
    }

    .post__edit__header{
        display: flex;
        padding: 10px;
        border-bottom: 1px solid black;
    }

    .post__edit__header h3{
        flex: 1 1 auto;
    }

    .post__edit__content{
        margin: 10px;
    }

    .post__edit__scope__option{
        display: block;
        border: 1px solid whitesmoke;
        width: max-content;
        padding: 5px;
        border-radius: 8px;
        margin-top: 5px;
    }

    .post__edit__scope__option input{
        margin-right: 8px;
    }

    .post__edit__scope__option input{
        cursor: pointer;
    }

    .option__toggle{
        display: none;
    }

    .post__edit__close{
        border-radius: 50%;
        height: 25px;
        width: 25px;
        border: 1px solid black;
        position: relative;
        cursor: pointer;
    }

    .post__edit__close:hover {
        background-color: gray;
    }

    .post__edit__close i{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .post__edit__author{
        display: flex;
    }

    .scope__selection {
        width: max-content;
        border-radius: 20px;
        border: 1px solid black;
        text-align: center;
        padding: 0 5px 5px 5px;
        cursor: pointer;
    }

    .post__edit__text textarea{
        display: block;
        resize: none;
        width: 480px;
        height: 200px;
        outline: none;
        border:0.5px solid whitesmoke;
        margin: auto;

    }

    .post__button{
        background-color: aquamarine white;
        border-radius: 20px;
        width: max-content;
        padding: 10px;
        float:right;
        margin: 5px;
        cursor: pointer;
        
    }

    .post__button:hover{
        background-color: whitesmoke;
    }
    /*ADS----------------*/
    .ads{
        display: none;
        flex: 0 0 auto;
        width: 250px;
        background-color: rgb(245, 245, 245) rgb(201, 201, 201);
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .suggest, .quote{
        width: 90%;
        background-color: white;
        margin-bottom: 10px;
        margin-top: 10px;
        text-align: center;
        border-radius: 10px;
        border: 0.5px solid rgb(201, 201, 201);
    }

    .suggest{
        display: flex;
        flex-direction: column;
        width: 90%;
        background-color: white;
        align-items: center;
    }

    .suggest__link{
        display: block;
        background-color: white;
        height: 50px;
        width: 90%;
        margin: 20px;
        color: black;
        text-decoration: none;
    }
    
    .suggest h3 {
        margin-top: 10%;
    }

    .suggest__info{
        display: flex;
        align-items: center;
    }

    .suggest__info__avatar{
        display: block;
        height: 45px;
        width: 45px;
    }

    .suggest__info__name{
        background-color: white;
        height: 30px;
        margin-left: 10px;
        width: 90%;
        text-align: left;
        color: #5a5a5a;
    }

    .suggest__info__name:hover {
        color: #265077;
        text-decoration: underline;
    }

    .quote{
        background-color: white;
    }

    .web__info {
        border: none !important;
        background-color: transparent !important;
        text-align: center;
    }

    .web__info > a {
        margin: 5px;
        font-size: 12px;
        text-decoration: none;
        color: #5a5a5a;
    }

    .web__info a:hover {
        text-decoration: underline;
        color: #265077;
    }

`;
