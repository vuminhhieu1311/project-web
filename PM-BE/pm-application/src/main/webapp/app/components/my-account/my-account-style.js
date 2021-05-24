import { css } from '../../core/components/css-tag';

export const myAccountStyle = css`
    :host {
        font-size: 1rem;
    }
    h5 {
        font-size: 0.9rem;
        font-weight: lighter;
        line-height: 30px;
        color: #777;
        padding-bottom: 15px;
        padding-top: 2px;
    }
    h1, h3 {
        color: #1e4258;
    }
    h3 {
        font-weight: lighter;
    }
    .input {
        position: relative;
        text-align: left;
        border: 1px solid #ccd0d5;
        border-radius: 4px;
        width: 90%;
        height: 30px;
        line-height: 20px;
        margin-bottom: 2%;
        color: #777;
        padding: 10px;
        outline: none;
    }
    input:focus {
        border: 1px solid #265077;
    }
    .selector {
        background-position: right 5px center;
        background-repeat: no-repeat;
        background-size: 14px;
        padding-right: 5%;
        height: 30px;
        margin-bottom: 2%;
        width: 25%;
        color: #777;
        border: 1px solid #ccd0d5;
        border-radius: 4px;
        display: inline-block;
    }
    .gender {
        margin-bottom: 1%;
    }
    .select {
        margin-top: 0px;
        margin-right: 4px;
        border-radius: 4px;
        border-width: 1px;
        display: inline-block;
        line-height: 18px;
        color: #777;
        font-size: 15px;
        padding: 0 10px 0 3px;
    }
    .cancel-btn {
        width: 47%;
        height: 10%;
        background-color: #E8E9EB;
        color: #1e4157f2;
        margin-right: 5%;
    }
    .btn-save,
    .btn-cancel {
        border: none;
        font-size: 16px;
        letter-spacing: 1px;
        padding: 7px;
        width: 100px;
        cursor: pointer;
        outline: none;
        transition: all 0.3s;
        margin-top: 3%;
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
`;
