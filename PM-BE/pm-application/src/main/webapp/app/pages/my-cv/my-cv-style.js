import { css } from '../../core/components/css-tag';

export const myCVStyle = css`
    :host {
        font-size: 1rem;
    }

    .container {
        background: linear-gradient(to left, #fdfcfb, #e2d1c3); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
        height: 500px;
        padding-top: 10%;
        text-align: center;
    }

    h1 {
        color: #537895;
        font-weight: lighter;
    }

    img {
        height: 250px;
        width: 200px;
        margin: 20px;
        cursor: pointer;
    }
    
`;
