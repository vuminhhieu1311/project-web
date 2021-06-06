import { css } from '../../core/components/css-tag';
export const alertFailStyle = css`

    .alert-simple.alert-primary {
        border: 1px solid rgba(241, 6, 6, 0.81);
        background-color: rgba(220, 17, 1, 0.16);
        box-shadow: 0px 0px 2px #ff0303;
        color: #ff0303;
        transition:0.5s;
        cursor:pointer;
        padding-left: 20px;
    }

    .alert-primary:hover{
    background-color: rgba(220, 17, 1, 0.33);
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
