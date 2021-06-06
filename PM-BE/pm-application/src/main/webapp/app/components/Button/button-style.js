import { css } from '../../core/components/css-tag';

export const buttonStyle = css`
    .btn {
        text-decoration: none;
        color: white;
        background-color: #e67e22;
        padding: 15px 30px;
        border-radius: 20px;
        margin: 0 30px;
        display: inline-block;
        width: 180px;
        text-align: center;
        transition: all 0.3s;
    }

    .btn:hover,
    .btn:active {
        background-color: #d35400;
    }
    
    .btn-post {
        text-decoration: none;
        color: #265077;
        padding: 15px 15px;
        border-radius: 20px;
        border: 2px solid #265077;
        display: inline-block;
        width: 155px;
        text-align: center;
        transition: all 0.3s;
        margin-left: 23%;
        font-weight: bold;
    }
    
    .btn-post:hover,
    .btn-post:active {
        background-color: #e67e22;
        color: white;
        border-color: #e67e22;
    }
    
    .btn-save,
    .btn-cancel {
        border: none;
        font-size: 16px;
        letter-spacing: 1px;
        padding: 7px 15px;
        cursor: pointer;
        outline: none;
        transition: all 0.3s;
        margin-top: 3%;
        text-decoration: none;
    }
    
    .btn-save {
        border-radius: 7px 0 0 7px;
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        font-weight: 500;
        color: #fff;
        margin-right: 0.5%;
    }
    
    .btn-cancel {
        border-radius: 0 7px 7px 0;
        margin-top: 3%;
        background: #ccd0d5;
        font-weight: 500;
        color: #1e4258;
    }
    
    .btn-save:hover,
    .btn-save:active {
        background: linear-gradient(135deg, #1597bb 0%, #1e4157f2 100%);
        transform: scale(1.1);
    }
    
    .btn-cancel:hover,
    .btn-cancel:active {
        background: linear-gradient(135deg, #1597bb 0%, #1e4157f2 100%);
        transform: scale(1.1);
        color: #fff;
    }

    .custom-btn {
        width: 30%;
        height: 10%;
        font-weight: 500;
        text-align: center;
        padding: 5px;
        border-radius: 7px;
        margin-top: 3%;
        transition: all 0.3s;
        font-size: 16px;
        letter-spacing: 1px;
        cursor: pointer;
        border: none;
        background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
        color: #fff;
        padding: 10px
    }

    .custom-btn:hover,
    .custom-btn:active {
        background: linear-gradient(135deg, #1597bb 0%, #265077 100%);
    }
`;
