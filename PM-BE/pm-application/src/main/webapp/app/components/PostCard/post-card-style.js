import { css } from '../../core/components/css-tag';

export const postCardStyle = css`
    :host {
        font-size: 1rem;
    }
    .news-card{
        display: flex;
        flex-direction: column;
        margin: 10px;
        border: 1px solid rgb(201, 201, 201);
        border-radius: 8px;
        padding: 10px 10px 0px 10px;
        background-color: white;
    }
    
    .news-card>div{
        margin: 10px;
    }

    .recruit-info{
        background-color: white;
        border-radius: 8px;
    }

    .recruit-info img {
        height: 400px;
        width: 100%;
    }

    .poster img {
        border-radius: 50%;
        height: 50px;
        width: 50px;
    }

    .news-react{
        background-color: red white;
    }

    #poster-avatar{
        display: block;
        height: 60px;
        width: 60px;
    }

    .poster{
        display: flex;
    }

    .poster-info{
        margin-left: 10px;
    }

    .recruit-info > div{
        margin-bottom: 10px;
    }

    #recruit-image{
        display: block;
        width: 100%;
    }

    .react{
        height: max-element;
        width: 100%;
        display: flex;
        border-top: 0.5px solid rgb(201, 201, 201);
    }

    .react-icon {
        left: 50%;
        top: 50%;
        font-size: 1rem;
        border: none;
        background: none;
        cursor: pointer;
        color: #5a5a5a;
        padding: 10px 20px;
    }

    .react-icon i {
        margin-right: 5px;
    }

    .react-icon:hover {
        color: #265077;
        background-color: rgb(201, 201, 201);
    }

    .react-icon:active {
        color: #265077;
        font-weight: bold;
    }
`;
