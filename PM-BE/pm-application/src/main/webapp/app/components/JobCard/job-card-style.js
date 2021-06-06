import { css } from '../../core/components/css-tag';

export const jobCardStyle = css`
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
        width: 730px;
    }
    
    .news-card>div{
        margin: 10px;
    }

    .recruit-info{
        background-color: white;
        border-radius: 8px;
    }

    .recruit-info img {
        height: 350px;
        width: 100%;
    }

    .poster img {
        border-radius: 50%;
        height: 50px;
        width: 50px;
    }

    .news-react{
        background-color: red white;
        margin-bottom: 0 !important;
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
        font-weight: bold;
    }

    .react-icon:hover {
        color: #265077;
        background-color: rgb(201, 201, 201);
    }

    .react-icon:active {
        color: #265077;
        
    }

    /*Comment======================================*/
    .comment{
        background-color: whitesmoke;
        padding: 10px;
        margin-top: 0 !important;
        display: none;
        border-top: 0.5px solid rgb(201, 201, 201);
    }

    .display-comment{
        display: block;
    }

    .news-header{
        display: flex;
        justify-content: space-between;
    }
    
    .edit{
        display:flex;
        flex-direction: column;
        margin-left: auto;
    }

    .edit-icon{
        border-radius: 50%;
        height: 30px;
        width: 30px;
        text-align: center;
        position: relative;
        cursor: pointer;
    }

    .edit-icon:hover{
        background-color: whitesmoke;
    }

    .edit-icon i{
        display: block;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }

    #dropdown-edit{
        position: absolute;
        background-color: white;
        margin-top: 32px;
        border: 0.1px solid #c7cfd4;
        border-radius: 8px;
        padding: 10px;
    }

    #dropdown-edit div{
        padding: 10px;
        cursor: pointer;
    }

    #dropdown-edit div:hover{
        background-color: whitesmoke;
    }

    #dropdown-edit::before {
        content: '';
        position: absolute;
        top: -7%;
        right: 78%;
        width: 13px;
        height: 13px;
        background-color: #fff;
        transform: rotate(45deg);
        border-left: 0.1px solid #c7cfd4;
        border-top: 0.1px solid #c7cfd4;
    }
    .news-card.delete {
        display: none;
    }
`;
