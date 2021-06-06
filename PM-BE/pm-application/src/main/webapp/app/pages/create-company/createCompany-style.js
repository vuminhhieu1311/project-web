import { css } from '../../core/components/css-tag';

export const CreatedCompanyStyle = css`
:host {
    font-size: 1rem;
    color: #5a5a5a;
}
main{
    margin-top: 70px;
    background-color: whitesmoke;
    display: flex;
    justify-content: space-around;
}

#main__left {
    width: 400px;
    flex: 0 0 400px;
    background-color: white;
    margin: 20px 0 20px 20px;
    border-radius: 8px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2)
}

#main__right{
    margin: 10px;
    width: 100%;
}

#main__basic__info h2{
    margin: 10px;
}

#main__basic__info__form{
    margin: 10px;
    border-radius: 8px;
}

#main__basic__info__form input{
    margin: 10px;
    width: 95%;
    height: 30px;
    font-size: 15px;
    text-indent: 5px;
    border-radius: 5px;
    color: #777;
    border: 1px solid #ccd0d5;
    outline: none;
}

#main__basic__info__form input:focus{
    border: 1px solid #265077;
}

.input {
    position: relative;

  
    line-height: 20px;
    margin-bottom: 2%;
    padding: 10px;
    
}

#term{
    display: block !important;
    height: 20px !important;
    width: 15px !important;
    margin:0 10px 0 10px !important;
}

#term__agree{
    display: flex;
}

#term__agree p {
    font-size: 0.8rem;
}


/*=======================================*/
.main-content-div{
    width: 96%;
    max-width: 800px;
    margin-left: auto;
    margin-right: auto;
    border: 0.1px solid #c7cfd4;
    border-radius: 10px;
    border-collapse: separate; 
    padding: 1px;
    margin-bottom: 20px;
    background-color: white;
}


#background-avatar{
    position: relative;
    width: 100%;
    height: 210px;
    background-color: white;
    border-top-left-radius: 15px;
    border-top-right-radius: 15px;
}

#background-avatar img{
    display: block;
    width: 100%;
    height: 200px;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}


#main-avatar{
    margin-top: -50px;
    background-color:transparent;
    position: relative;
    padding: 5px;
}

#main-avatar img{
    display: block;
    background-color: white;
    border: 0.1px solid #c7cfd4;
    margin-left: 20px;
}


#info{
    width: 100%;
    height: auto;
    background-color: white;
    display: flex;
    margin-top: 10px;
    border-bottom-left-radius: 25px;
    border-bottom-right-radius: 25px;
}

#info div{
    width: 50%;
    padding: 0 20px;
}

#basic-info-follow{
    background: linear-gradient(
        135deg
        , #1e4157f2 0%, #1597bb 100%);
    width: max-content;
    padding: 8px 15px;
    font-size: 18px;
    font-weight: bold;
    color:rgb(245, 245, 245);
    border-radius: 30px;
    margin: 10px;
    cursor: pointer;
    margin-left: 20px;
}

#basic-info-follow i{
    margin-right: 5px;
    font-size: 15px;
}

#basic-info-nav{
    display: flex;
    flex-wrap: wrap;
    border-top: 1px solid rgb(179, 179, 179);
}

#basic-info-nav a{
    text-decoration: none;
    color: black;
}

#basic-info-nav div{
    margin: 10px 20px 10px 20px;
    font-weight: bold;
}

#basic-info-nav a:hover{
    color:royalblue;
    border-bottom: 2px solid royalblue;
    cursor: pointer;
}


#company-info{
    font-size: 15px;
}
#company-info span{
    margin-right: 10px;
}

#company-info a{
    text-decoration: none;
    color: black;
}

#review{

    margin: 10px;
    border: none;
    font-weight: bold;
    color: white;
    background: linear-gradient(
        135deg
        , #1e4157f2 0%, #1597bb 100%);
    padding: 10px;
    border-radius: 8px;  
}
.btn-save,
.btn-cancel {
    border: none;
    font-size: 16px;
    letter-spacing: 1px;
    padding: 7px;
    width: 100px;
    cursor: pointer;
    outline: none;
    transition: all 0.3s;
    margin-top: 3%;
}
.btn-save {
    border-radius: 7px 0 0 7px;
    background: linear-gradient(135deg, #1e4157f2 0%, #1597bb 100%);
    font-weight: 500;
    color: #fff;
    margin-right: 0.5%;
}
.btn-cancel {
    border-radius: 0 7px 7px 0;
    margin-top: 3%;
    background: #ccd0d5;
    font-weight: 500;
    color: #1e4258;
}
.btn-save:hover,
.btn-save:active {
    background: linear-gradient(135deg, #1597bb 0%, #1e4157f2 100%);
    transform: scale(1.1);
}
.btn-cancel:hover,
.btn-cancel:active {
    background: linear-gradient(135deg, #1597bb 0%, #1e4157f2 100%);
    transform: scale(1.1);
    color: #fff;
}
.update-button {
    display: flex;
    justify-content: center;
}
.selector {
    background-position: right 5px center;
    background-repeat: no-repeat;
    background-size: 14px;
    padding-right: 5%;
    height: 30px;
    width: 95%;
    color: #777;
    border: 1px solid #ccd0d5;
    border-radius: 4px;
    display: inline-block;
    margin: 10px;
}
.gender {
    margin-bottom: 1%;
}
.select {
    margin-top: 0px;
    margin-right: 4px;
    border-radius: 4px;
    border-width: 1px;
    display: inline-block;
    line-height: 18px;
    color: #777;
    font-size: 15px;
    padding: 0 10px 0 3px;
}
label {
    margin-left: 2.5%;
}
.show-alert, .show-alert-fail {
    display: none
}

.show-alert.active, .show-alert-fail.active {
    display: block;
}

.custom-btn {
    width: 95%;
    height: 35px;
    display: block;
    background: #2193b0;  /* fallback for old browsers */
    background: -webkit-linear-gradient(to right, #6dd5ed, #2193b0);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #6dd5ed, #2193b0); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    cursor: pointer;
    font-weight: 500;
    color: #fff;
    padding-top: 7px;
    margin: 10px;
    text-align: center;
    border-radius: 7px;
}

.custom-btn:hover,
.custom-btn:active {
    background: linear-gradient(135deg, #1597bb 0%, #265077 100%);
}

.upload-btn i {
    margin-right: 5px;
}

`;

