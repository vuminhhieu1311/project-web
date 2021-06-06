import { css } from '../../core/components/css-tag';

export const skillStyle = css`
    :host {
        font-size: 1rem;
    }
    
    h5 {
        font-size: 0.9rem;
        font-weight: lighter;
        color: #777;
    }

    .skill-list {
        display: flex;
        flex-direction: row;
        padding-left: 20px;
        padding-bottom: 15px;
    }

    .skill-list p {
        line-height: 15px;
        padding-left: 10px;
    }

    .skill-list i {
        cursor: pointer
    }

    .skill-list i:hover {
        color: #1687a7;
    }

    h1 {
        color: #1e4258;
        padding-bottom: 3%;
    }

    app-button {

    }

    app-button i {
        margin-right: 10px;
    }

    .header-row {
        display: flex;
        flex-direction: row;
    }

    input {
        position: relative;
        text-align: left;
        border: 1px solid #ccd0d5;
        border-radius: 4px 0 0 4px;
        width: 98%;
        height: 38px;
        line-height: 20px;
        margin-bottom: 5%;
        color: #777;
        padding: 10px;
        outline: none;
    }
    input:focus {
        border: 1px solid #265077;
    }
    .custom-btn {
        height: 10%;
        font-weight: 500;
        text-align: center;
        padding: 5px 10px;
        border-radius: 0 4px 4px 0;
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
