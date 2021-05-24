import { css } from '../../../core/components/css-tag';

export const smallFooterStyle = css`
    :host {
        font-size: 20px;
    }

    .small-footer {
        background: linear-gradient(to right, #1e4157f2, hsl(205, 50%, 35%));
        color: #f4f4f4;
        font-size: 1.3rem;
        padding: 2%;
    }
    
    .small-footer-text a {
        color: #f4f4f4;
        font-weight: lighter;
        text-decoration: none;
        font-size: 70%;
        padding: 0 1%;
        border-right: 1px solid rgb(139, 135, 135);
    }
    
    .small-footer-text a:hover {
        text-decoration: underline;
    }
`;
