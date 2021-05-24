import { css } from '../../core/components/css-tag';

export const changePassStyle = css`
    :host {
        font-size: 1rem;
    }
    
    h5 {
        font-size: 0.9rem;
        font-weight: lighter;
        line-height: 30px;
        color: #777;
        padding-bottom: 16%;
        padding-top: 2px;
    }

    h1 {
        color: #1e4258;
        padding-bottom: 3%;
    }

    .input {
        position: relative;
        text-align: left;
        border: 1px solid #ccd0d5;
        border-radius: 4px;
        width: 90%;
        height: 30px;
        line-height: 20px;
        margin-bottom: 4%;
        color: #777;
        padding: 10px;
        outline: none;
    }

    input:focus {
        border: 1px solid #265077;
    }
`;
