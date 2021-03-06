import { css } from '../../core/components/css-tag';

export const RecruiterApplicantsStyle = css`
main{
    margin-top: 80px;
    display: flex;
}

#main__left{
    width: 300px;
    background-color: aqua;
    height: max-content;
    margin: 10px;
    border-radius: 8px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2)
}

#main__right{
    background-color: rgb(255, 255, 255);
    margin: 10px;
    width: 100%;
}

#filter{
    width: 100%;
    background-color: rgb(253, 253, 253);
    border-radius: 8px;
    padding: 10px;
}

#filter div{
    margin-bottom: 10px;
}

#custom__filter{
    display: flex;
    justify-content: space-between;
    padding: 5px 10px 5px 10px;
    border-bottom: 1px solid rgb(201, 201, 201);
}

#filter select{
    margin: 10px;
    width: 250px;
    outline: none;
    height: 30px;
}

#applicants__amount{
    background: linear-gradient(
135deg
, rgba(30, 65, 87, 0.95) 0%, rgb(21, 151, 187) 100%);
color: white;
    font-weight: bold;
    border: 1px solid rgb(201, 201, 201);
    padding: 10px;
}

#applicants__brief__card{
    border-bottom: 1px solid rgb(201, 201, 201);
}

#applicants__brief__card div{
    margin: 5px;
}

#applicants__brief__card__header{
    display: flex;
}

#applicants__brief__card__header__name{
    flex: 1  1 auto;
}

#applicants__brief__card__header__name p{
    font-size: 12px;
}

.applicants__brief__card__info{
    display: grid;
    grid-template-columns: 1fr 4fr;
}

.applicants__brief__card__info__title{
    font-weight: bold;
}

.applicants__brief__card__info__title{
    text-align: right;
}

.applicants__brief__card__info__detail i{
    font-size: 8px;
    margin: 5px;
}

.applicants__brief__card__info__detail span{
    font-style: italic;
    font-size: 13px;
}

#applicants__brief__card__header__button button{
    padding: 7px;
    background: linear-gradient(
135deg
, rgba(30, 65, 87, 0.95) 0%, rgb(21, 151, 187) 100%);
color:white;
font-weight: bold;
border: none;
margin: 10px;
cursor: pointer;
}
`;
