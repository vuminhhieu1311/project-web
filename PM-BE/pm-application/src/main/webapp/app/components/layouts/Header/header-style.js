import { css } from '../../../core/components/css-tag';

export const headerStyle = css`
    :host {
        font-size: 16px;
    }

    header {
        height: 60px;
        width: 100%;
        position: fixed;
        top: 0;
        display: flex;
        border-bottom: 0.5px solid gray;
        z-index: 10;
        background: linear-gradient(to right, #1e4157f2, #2d6187);
    }
    
    .logo {
        height: 90%;
        float: left;
        margin-top: 1%;
    }
    
    #left-header {
        flex: 1 0 350px;
        position: relative;
        display: flex;
    }
    
    #right-header {
        margin-top: -1%;
        flex: 0 0 auto;
        display: flex;
    }
    
    #nav-menu {
        height: 100%;
        align-items: center;
        justify-content: flex-end;
        display: flex;
        padding: 0 20px;
        margin-left: -35%;
    }
    
    .menu-icons {
        position: relative;
        width: 70px;
        height: 50px;
        cursor: pointer;
    }
    
    .profile img {
        border-radius: 50%;
    }
    
    .menu-icons:hover div {
        color: #E99346;
    }
    
    .menu-icons:hover h6 {
        color: #E99346;
    }
    
    .material-icons {
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
        height: 25px;
        width: 25px;
        color: #f4f4f4;
    }
    
    .menu-icons h6 {
        position: absolute;
        bottom: 0;
        width: 100%;
        text-align: center;
        color: #f4f4f4;
        font-size: 70%;
    }
    
    .menu-icons img {
        display: block;
        height: 25px;
        width: 25px;
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
`;
