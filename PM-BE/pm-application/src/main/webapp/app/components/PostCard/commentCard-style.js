import { css } from '../../core/components/css-tag';

export const CommentCardStyle = css`
.comment__card{
    background-color: white;
    display: flex;
    border-radius: 8px;
}

.comment__card__content{
    display: flex;
    flex-direction: column;
    margin-left: 10px;
}

.comment__card__avatar{
    padding: 5px;
}

#comment__card__avatar{
    height: 30px;
    width: 30px;
}

.comment__card__content__name a{
    font-weight: bold;
    color: blue;
    text-decoration: none;
}

.comment__card__content__detail {
    margin-left: 5px;
}

.comment__card__content__detail p{
    margin: 0;
}

.comment__card__content__react{
    display: flex;
}

.comment__card__content__react div{
    font-size:12px;
    margin-right: 10px;
    font-weight: bold;
    color: royalblue;
    cursor: pointer;
}
`;