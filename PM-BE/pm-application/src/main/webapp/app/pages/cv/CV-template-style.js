import { css } from '../../core/components/css-tag';

export const CVTemplateStyle = css`
    :host {
        font-size: 20px;
    }

    .header{
        display: flex;
        height: 200px;
        background-color: #e96443;
    }
    
    .header__basic__info__avatar{
        background-color: white;
        width: 150px;
        height: 170px;
        margin-left: 50px;
        margin-top: 20px;
        text-align: left;
        font-size: 100px;
        position: relative;
    }
    
    .header__basic__info__avatar i{
        display: block;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    
    .header__basic__info{
        background-color: #e96443;
        flex: 1 1 auto;
        padding: 40px;
        display: grid;
        grid-template-columns: 1.5fr 2fr 2fr;
        gap: 10px;
        color: white;
    }
    
    
    .header__basic__info div{
    }
    
    .main{
        height: 1000px;
        display: flex;
        flex-direction: column;
        margin-top: 0;
        padding: 10px;
        gap: 10px;
        justify-content: start;
        flex-wrap: wrap;
        background-color: rgb(196, 196, 196);
    }
    
    .main__info{
        width: 50%;
        background-color: whitesmoke;
        box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
        border-radius: 8px;
        padding: 10px;
    }
    
    .main__info__header{
        padding: 10px;
        border-bottom: 1px solid crimson;
        font-size: 20px;
        font-weight: bold;
        color: rgb(18, 39, 23);
    }
    
    .main__info__content{
        display: flex;
        margin-left: 20px;
        line-height: 30px;
    }
    
    .main__info__content__title{
        width: 100px;
        margin: 10px;
        outline: none;
    }
    
    .main__info__content__detail{
        margin: 10px;
        outline: none;
    }
    

`;