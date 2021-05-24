import { css } from '../../../core/components/css-tag';

export const searchbarStyle = css`
    .modal-header {
        display: flex;
    }

    .close-button {
        cursor: pointer;
        font-size: 2rem;
        color: hsl(203, 49%, 23%, 0.95);
        border: none;
        background: none;
        outline: none;
    }
    
    .close-button i {
        font-size: 30px !important;
    }
    
    .search-input {
        width: 87%;
        padding-left: 10px;
        padding-right: 40px;
        border-radius: 20px;
        border: 1px solid hsl(203, 49%, 23%, 0.95);
        outline: none;
        margin: 0 15px;
    }
    
    .search-input:focus {
        border-radius: 20px;
        border: 2px solid #1597bb;
    }
    
    i {
        cursor: pointer;
        font-size: 1.3rem;
        color: hsl(203, 49%, 23%, 0.95);
        border: none;
        margin-left: -18%;
        margin-top: 3%;
        background: none;
    }
    
    i:hover {
        color: #1597bb;
    }
`;
