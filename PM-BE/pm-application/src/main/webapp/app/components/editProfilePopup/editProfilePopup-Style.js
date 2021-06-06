import { css } from '../../core/components/css-tag';

export const EditProfilePopupStyle = css`
#edit__overlay{
    position:fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 3;
    display: none;
}
#edit__overlay__form{
    width:700px;
    height: 270px;
    position: fixed;
    top: 80px;
    left: 50%;
    right: 50%;
    transform: translateX(-50%);
    background-color: white;
    border-radius: 8px;
}
#edit__overlay__form__header{
    border-bottom: 1px solid black;
    height: 50px;
    font-size: 25px;
    padding: 10px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
}
#edit__overlay__form__header div{
    margin-right: 10px;
    border-radius: 50%;
    width: 33px;
    height: 33px;
    text-align: center;
    position: relative;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}
#edit__overlay__form__header div:hover{
    background-color: whitesmoke;
    cursor: pointer;
}
#edit__overlay__form__header div i{
    display: block;
    margin: auto;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
#edit__overlay__form__main form{
    margin: 10px;
}
#edit__overlay__form__main form div{
    display: flex;
    margin: 10px;
    font-size: 20px;
    font-weight: lighter;
}
#edit__overlay__form__main label{
    width: 100px;
    display: block;
}
#edit__overlay__form__main input{
    width: 600px;
    outline: none;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
    border: white;
    border-radius: 2px;
    height: 30px;
    font-size: 20px;
}
#edit__overlay__form__main button{
    float: right;
    height: 30px;
    width: 50px;
    border-radius: 4px;
    background-color: white;
    border: none;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}
#edit__overlay__form__main button:hover{
    background-color: whitesmoke;
    cursor: pointer;
}
`;
