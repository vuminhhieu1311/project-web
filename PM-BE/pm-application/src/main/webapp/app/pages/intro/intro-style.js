import { css } from '../../core/components/css-tag';

export const introStyle = css`
    :host {
        font-size: 20px;
    }

    .p-long {
        text-align: center;
        color: #265077;
        margin-top: 40px;
        margin-bottom: 40px;
        width: 70%;
        margin-left: 15%;
        line-height: 140%;
    }
    
    section {
        padding: 50px;
    }

    /*--------------------------------------------------*/
    /*---------------------FIND-JOBS-SECTION------------*/
    /*--------------------------------------------------*/
    .jobs-picture {
        position: relative;
    }
    
    .jobs-picture img {
        width: 90%;
        height: 150px;
        border-radius: 20px;
        border: 2px solid rgb(139, 186, 231);
        box-shadow: 0px 5px 15px 2px #265077;
    }
    
    .job-title {
        position: absolute;
        top: 50%;
        left: 50%;
        color: #c2e1fd;
        transform: translate(-50%, 100%);
        opacity: 0;
        transition: all .7s;
    }
    
    .jobs-picture:hover .job-title,
    .jobs-picture:active .job-title {
        transform: translate(-50%, -50%);
        opacity: 1;
    }
    
    .jobs-picture:hover img,
    .jobs-picture:active img {
        filter: brightness(55%) blur(2px);
    }

    /*--------------------------------------------------*/
    /*------------------POST JOBS SECTION---------------*/
    /*-----------------CONNECT PEOPLE SECTION-----------*/
    .post-jobs-section {
        background-color: #f4f4f4;
    }

    .job-picture img {
          width: 100%;
          height: 100%;
          object-fit: contain;
    }
`;
