import { css } from '../../core/components/css-tag';

export const peopleSidebarStyle = css`
    aside {
        display: none;
        flex: 0 0 auto;
        height: 500px;
        width: 280px;
        background-color: rgb(245, 245, 245);
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    
    aside > div {
        width: 90%;
        background-color: white;
        margin-bottom: 10px;
        margin-top: 10px;
        text-align: center;
        border-radius: 10px;
        border: 0.5px solid gray;
    }
    
    .suggest {
        display: flex;
        flex-direction: column;
        width: 90%;
        background-color: white;
        align-items: center;
    }
    
    .suggest__link {
        display: block;
        background-color: white;
        height: 50px;
        width: 90%;
        margin: 20px;
        color: black;
        text-decoration: none;
    }
    
    .suggest__link:hover {
        text-decoration: underline;
    }
    
    .suggest__info {
        display: flex;
        align-items: center;
    }
    
    .suggest__info__avatar {
        display: block;
        height: 45px;
        width: 45px;
    }
    
    .suggest__info__name {
        background-color: white;
        height: 30px;
        margin-left: 10px;
        width: 90%;
        text-align: left;
    }
    
    .quote {
        background-color: white;
    }
    
    @media (max-width: 1024px) {
        aside {
            display: none;
        }
    }
`;
