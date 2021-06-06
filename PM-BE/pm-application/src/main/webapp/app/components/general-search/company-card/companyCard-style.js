import { css } from '../../../core/components/css-tag';

export const CompanyCardStyle = css`

.company__card, .people__card{
    background-color: white;
    border: 0.2px solid rgb(172, 172, 172);
    display: flex;
    padding: 10px;
    cursor: pointer;
    border-radius: 8px;
    margin: 5px;
    line-height: 20px;
}

.company__card__logo, .people__card__avatar{
    margin-right: 10px;
}

.company__card__info h3 {
    padding-top: 10px;
}

`;
