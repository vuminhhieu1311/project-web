import { css } from '../../../../core/components/css-tag';

export const introHeaderStyle = css`
    :host {
        font-size: 20px;
    }

    header {
        background-image: linear-gradient(
          45deg,
          rgba(2, 33, 64, 0.8) 55%,
          rgba(45, 95, 93, 0.85)
        ),
        url('content/images/employment-rate.jpeg');
        background-size: cover;
        background-position: center; /* can giua */
        height: 90vh; /* view height */
        margin-top: -2%;
        background-attachment: fixed;
    }

    .logo {
        width: 250px;
        float: left;
        margin-top: 28px;
        margin-left: 40px;
    }

    .main-nav {
        float: right;
        list-style: none;
        margin-right: 50px;
        margin-top: 50px;
    }
    .main-nav li {
        display: inline-block;
        margin-right: 30px;
    }

    .main-nav li a {
        text-decoration: none;
        color: white;
        font-size: 110%;
        font-weight: 700;
        transition: border-bottom 0.5s; /* chuyen canh */
    }

    .main-nav li a:hover,
    .main-nav li a:active {
        border-bottom: 2px solid #cccccc;
        padding: 3px 0;
    }

    .heading-main-box {
        position: absolute;
        top: 30%;
        left: 4.5%;
        width: 95%;
    }
    
    .heading-main-box .hot-tags {
        margin-left: -2.5%;
    }
    
    .row {
        max-width: 1140px;
        margin: 0 auto;
    }
    
    h1, h2 {
        text-transform: uppercase;
        letter-spacing: 1px;
    }

    h1 {
        font-size: 200%;
        font-weight: 400;
        word-spacing: 5px;
        line-height: 135%;
        color: white;
        margin-bottom: 50px;
    }

    h2 {
        text-align: center;
        color: #e67e22;
        font-size: 180%;
        font-weight: 700;
    }

    h2:after {
        content: " ";
        display: block;
        width: 50%;
        height: 3px;
        background-color: hsl(28, 80%, 52%, 0.5);
        margin: 0 auto;
    }
    
    .clearfix::after {
        content: " ";
        display: table;
        clear: both;
    }
`;
