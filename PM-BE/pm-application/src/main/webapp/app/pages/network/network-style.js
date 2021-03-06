import { css } from '../../core/components/css-tag';

export const NetworkStyle = css`

main{
    display: flex;
    margin-top: 70px;
    padding: 10px;
    background-color: whitesmoke;
    height: 100vh;
    width: 100%;
}

#main__left{
    flex: 0 0 420px;
    background-color: white;
    height: max-content;
    margin-right: 10px;

}

#main__right{
    width: 100%;
    background-color: rgb(255, 255, 255);
    height: max-content;
    padding: 5px;
    border-radius: 8px;
    border: 0.1px solid rgb(197, 196, 196);
}

#main__left__title{
    background: linear-gradient(
        135deg
        , rgba(30, 65, 87, 0.95) 0%, rgb(21, 151, 187) 100%);
    color: white;
    font-weight: bold;
    padding:10px;
}

.friends{
    display: flex;
    flex-direction: column;
    width: 90%;
    background-color: white;
    align-items: center;
}

.friends__link{
    display: block;
    background-color: white;
    width: 190px;
    color: black;
    text-decoration: none;
    padding: 15px;
    margin: 5px;    
    border-radius: 8px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2)
}

.friends__link:hover{
    text-decoration: underline;
}

.friends__info{
    display: flex;
    align-items: center;
}

.friends__info__avatar{
    display: block;
    height: 45px;
    width: 45px;
}

.friends__info__name{
    background-color: white;
    height: 30px;
    margin-left: 10px;
    width: 90%;
    text-align: left;
}

#friends__list{
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap:10px;
}

#add__contact__header{
    background-color: white;
    display: flex;
    padding: 10px;
    align-items: center;
}

#add__contact__info{
    padding: 10px;
}

#add__contact__info label{
    font-size: 12px;
}

#add__contact__info input{
    margin: 10px;
    height: 30px;
    font-size: 15px;
    text-indent: 5px;
    outline: none;
    width: 400px;
}

#add__contact__info button{
    background: linear-gradient(
        135deg
        , rgba(30, 65, 87, 0.95) 0%, rgb(21, 151, 187) 100%);
    font-weight: bolder;
    color: white;
    padding: 5px;
    border: none;
    font-size: 15px;
    cursor: pointer;
}
`;