import { css } from '../../../core/components/css-tag';

export const profileMenuStyle = css`
    :host {
        display: contents;
    }
    
    .profile img {
        width: 22%;
        height: 50px;
        border-radius: 50%;
    }
    
    .profile {
        display: flex;
        margin: 5%;
        background: none;
    }
    
    .profile h3 {
        color: #265077;
        width: 100%;
        font-size: 1rem;
        font-weight: 500;
        line-height: 1.2rem;
        padding: 0 10px;
    }
    
    .profile h3 span {
        font-size: 0.8rem;
        color: #5c5959;
        font-weight: 300;
    }
    
    .setting-list {
        margin: 2% 8%;
        background: none;
    }
    
    ul li {
        list-style: none;
        padding: 10px 0;
        border-top: 1px solid rgba(0, 0, 0, 0.1);
        display: flex;
        align-items: center;
        color: #265077;
    }
    
    ul li i {
        margin-right: 5%;
    }
    
    ul li a {
        color: #265077;
        text-decoration: none;
        display: inline-block;
        font-weight: 500;
        transition: 0.5s;
    }
    
    ul li a:hover {
        text-decoration: underline;
        color: #2d6187;
    }
    
    .dropdown-btn {
        background: none;
        border: none;
    }
`;
