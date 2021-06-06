import { css } from '../../core/components/css-tag';

export const NameCardStyle = css`

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
`;