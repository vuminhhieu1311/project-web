import { css } from '../../core/components/css-tag';

export const notFoundStyle = css`
    * {
        line-height: 1.2;
        margin: 0;
    }

    :host {
        color: #888;
        display: table;
        font-family: sans-serif;
        height: 100vh;
        text-align: center;
        width: 100%;
    }

    .content {
        display: table-cell;
        vertical-align: middle;
        margin: 2em auto;
    }

    h1 {
        color: #555;
        font-size: 2em;
        font-weight: 400;
    }

    p {
        margin: 0 auto;
        width: 280px;
    }

    @media only screen and (max-width: 280px) {
        .content,
        p {
            width: 95%;
        }
        
        h1 {
            font-size: 1.5em;
            margin: 0 0 0.3em;
        }
    }
`;
