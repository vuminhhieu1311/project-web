import { css } from '../../core/components/css-tag';

export const dropdownStyle = css`
    :host {
        display: contents;
    }
    
    .dropdown-menu {
        position: absolute;
        top: 110%;
        margin-right: -0.5%;
        background-color: #fff;
        border-radius: 12px;
        transition: 0.5s;
        width: 250px;
        border: 0.1px solid #c7cfd4;
        visibility: hidden;
        opacity: 0;
    }
    
    .dropdown-menu.active {
        visibility: visible;
        opacity: 1;
    }
    
    .dropdown-menu::before {
        content: '';
        position: absolute;
        top: -3%;
        right: 13%;
        width: 15px;
        height: 15px;
        background-color: #fff;
        transform: rotate(45deg);
        border-left: 0.1px solid #c7cfd4;
        border-top: 0.1px solid #c7cfd4;
    }
`;
