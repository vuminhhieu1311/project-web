import { css } from '../../core/components/css-tag';

export const briefJobCardStyle = css`

#brief__info{
    display: flex;
    background-color: white;
    border-top: 0.2px solid rgba(196, 221, 255, 0.5);
}

#brief__info:hover{
    background-color: rgb(196, 221, 255);
}

#brief__info__content:hover{
    background-color: rgb(196, 221, 255);
    cursor: pointer;
}

#brief__info__content{
    padding: 0;
    padding-left: 10px;
    padding-top: 5px;
    padding-bottom: 5px;
    margin: 0;
    background-color: white;
    width: 100%;
}

#brief__info__content p{
    margin: 0;
}

#brief__info__content h3{
    margin-bottom: 5px;
    margin-top: 0;
    margin-top: 5px;
}

#brief__info__content a{
    color: black;
    text-decoration: none;
}


`;