import { css } from '../../core/components/css-tag';

export const modalStyle = css`
    .modal {
        z-index: 10000;
        background-color: #fff;
        position: fixed;
        top: 12%;
        left: 25%;
        width: 50%;
        max-width: 50%;
        transition: 200ms ease-in-out;
        transform: scale(0);
        padding: 10px 15px;
    }
    .active {
        transform: scale(1);
    }
`;
