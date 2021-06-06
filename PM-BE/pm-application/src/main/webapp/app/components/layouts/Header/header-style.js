import { css } from '../../../core/components/css-tag';

export const headerStyle = css`
    :host {
        font-size: 16px;
    }

    header {
        height: 100px;
        width: 100%;
        position: fixed;
        top: 0;
        display: flex;
        height: 70px;
        border-bottom: 0.5px solid gray;
        z-index: 10;
        background: linear-gradient(135deg,#1e4157f2 0%,#2193b0 100%);
    }
    
    .logo {
        /* width: 200px; */
        height: 90%;
        float: left;
        margin-top: 1%;
    }
    
    #left-header {
        width: 300px;
        position: relative;
        display: flex;
        padding-left: 10px;
    }
    
    #left-header > img{
        display: none;
    }
    
    #right-header {
        /*background-color: aquamarine;*/
        /*margin-top: -1%;
        flex: 0 0 auto;
        display: flex;*/
        margin-left: auto;
    }
    
    
    @media (max-width:800px) {
        #left-header{
            width: 90px;
        }
    
        #left-header a{
            display: none;
        }
    
        #left-header > img{
            display: block;
            margin: auto;
        }
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
        font-size: 10px;
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
