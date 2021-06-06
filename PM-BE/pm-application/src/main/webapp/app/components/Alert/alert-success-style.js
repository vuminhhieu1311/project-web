import { css } from '../../core/components/css-tag';
export const alertStyle = css`

    .alert-simple.alert-primary {
        border: 1px solid #1597bb;
        background-color: rgba(1, 204, 220, 0.16);
        box-shadow: 0px 0px 2px #03fff5;
        color: #265077; 
        transition:0.5s;
        cursor:pointer;
        padding-left: 20px;
    }

    .alert-primary:hover{
    background-color: rgba(1, 204, 220, 0.33);
    transition:0.5s;
    }
    .alertprimary
    {
        font-size: 18px;
        color: #03d0ff;
        text-shadow: none;
    }

    .square_box {
        position: absolute;
        -webkit-transform: rotate(45deg);
        -ms-transform: rotate(45deg);
        transform: rotate(45deg);
        border-top-left-radius: 45px;
        opacity: 0.302;
    }

    .alert:before {
        content: '';
        position: absolute;
        width: 0;
        height: calc(100% - 44px);
        border-left: 1px solid;
        border-right: 2px solid;
        border-bottom-right-radius: 3px;
        border-top-right-radius: 3px;
        left: 0;
        top: 50%;
        transform: translate(0,-50%);
        height: 20px;
    }
`;
