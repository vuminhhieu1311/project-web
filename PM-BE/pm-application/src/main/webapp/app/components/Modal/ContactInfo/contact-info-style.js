import { css } from '../../../core/components/css-tag';

export const contactInfoStyle = css`
    #contact-info-div {
        height: 250px;
        width: 500px;
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        background-color: white;
        z-index: 10000;
        border-radius: 10px;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        display: none;
    }
    
    #contact-info-div.active {
        display: block!important;
    }
    
    #contact-info-header {
        margin: 0;
        height: 50px;
        width: 500px;
        padding: 10px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid black;
    }
    
    #contact-info-header button {
        border: none;
        outline: none;
        background: none;
        font-size: 2rem;
        font-weight: bold;
        cursor: pointer;
        height: 2rem;
        width: 2rem;
    }
    
    #contact-info-header button:hover {
        border-radius: 50%;
        background-color: rgba(0, 0, 0, 0.08);
    }
    
    #contact-info-main {
        margin: 20px;
    }
    
    #contact-info-main div {
        margin: 20px;
        background-color: white;
        position: relative;
    }
    
    #contact-info-main div .material-icons {
        background-color: white;
        top: 0;
        left: 0;
        transform: translate(-50%, -50%);
    }
    
    #contact-info-main div h3 {
        display: inline-block;
        position: absolute;
        top: 10px;
        left: 40px;
        background-color: white;
    }
    
    #contact-info-main div a {
        display: block;
        background-color: white;
        position: absolute;
        top: 50px;
        left: 10px;
        font-weight: bold;
        font-size: 16px;
        color: #265077;
    }
`;
