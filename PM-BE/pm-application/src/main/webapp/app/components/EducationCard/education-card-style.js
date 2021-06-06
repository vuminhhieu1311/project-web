import { css } from '../../core/components/css-tag';

export const educationCardStyle = css`
    :host {
        font-size: 0.8rem;
    }
    .news-card{
        display: flex;
        flex-direction: column;
        margin-top: 20px;
        border: 1px solid rgb(201, 201, 201);
        border-radius: 8px;
        padding: 10px 10px 0px 20px;
        background-color: white;
    }

    .poster .brief {
        font-size: 50px;
        color: #1687a7;
    }

    .poster .edit {
        color: #1687a7;
        margin-top: 2.5%;
        margin-left: 5%;
        font-size: 17px;
        cursor: pointer;
    }

    .poster{
        display: flex;
    }

    .poster-info{
        margin-left: 20px;
    }

    .poster-info p {
        margin-top: -20px;
    }

    .news-card.delete {
        display: none;
    }

    .iconImg {
        height: 100px;
        width: 100px;
        padding-right: 20px;
    }
`;
