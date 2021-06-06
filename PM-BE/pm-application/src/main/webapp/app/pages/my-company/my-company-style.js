import { css } from '../../core/components/css-tag';

export const myCompanyStyle = css`
    
/*MAIN---------------------------*/
:host {
    font-size: 1rem;
    color: #5a5a5a;
}

main{
    display: flex;
    justify-content: center;
    background-color: rgb(245, 245, 245);
    margin-top: 3%;
    margin-left: 5px;
}

#main-content{
    margin-top: 0;
    height: auto;
    display: flex;
    flex-direction: column;
    background-color: rgb(245, 245, 245);
    padding: 10px;
    align-items: center;
}

.main-content-div{
    width: 100%;
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
    margin-top: -10%;;
    background-color:transparent;
    position: relative;
    padding-bottom: 15px;
    display: flex;
    flex-direction: row;
}

#main-avatar img{
    display: block;
    background-color: white;
    margin-left: 20px;
    width: 100px;
    height: 100px;
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


/*-------------------------------------------------*/

.main-content-div h2{
    padding: 10px;
    background-color: transparent;
    width:100%;
}

/*-------------------------------------------------*/
aside{
    display: none;
    flex: 0 0 auto;
    height: 500px;
    width: 280px;
    background-color: rgb(245, 245, 245);
    display: flex;
    flex-direction: column;
    align-items: center;
}

aside > div{
    width: 90%;
    background-color: white;
    margin-bottom: 10px;
    margin-top: 10px;
    text-align: center;
    border-radius: 10px;
    border: 0.5px solid gray;
}


.quote{
    background-color: white;
}



@media (max-width: 1024px){
    aside{
        display: none;
}

    main{
        display: block;
        background-color: rgb(245, 245, 245);
    
    }

    #main-content{
        margin-left: auto;
        margin-right: auto;
    }
}

@media (max-width:899px){
    .menu-icons-link h5{
        display: none;
    }

    .menu-icons{
        height: 45px;
        width: 70px;
    }

    .menu-icons div{
        text-align: center;
        font-size: 30px;
    }

}

@media (max-width: 800px){
    #main-content{
        width: 400px;
    }

    .main-content-div{
        width: 400px;
    }

    #left-header{
        flex: 1 0 100px;
    }    

    #search-div{
        width: 40px;
        cursor: pointer;
    }
    #search-div img{
        display: block;
        height: 20px;
        width:20px;
        margin: 8px;
    }
    
    #search-div input{
        display: none;
    }

}

.search-div--expand{
    background-color: brown !important;
    width: 80vw !important;
    z-index: 10;
}

.right-header--remove{
    display: none;
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
    margin-left: 20px;
    cursor: pointer;
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

#company-info a:hover{
    text-decoration: underline;
}

.main-content-div-expand{
    padding: 10px;
    text-align: center;
    border-top: 0.2px solid gray;
}

.main-content-div-expand a{
    text-decoration: none;
    color: black;
}

.main-content-div-expand a:hover{
    text-decoration: underline;
}

#about-short{
    margin: 10px;
}

#about__detail{
    margin: 10px;
}

#about__detail__overview{
    margin-bottom: 20px;
}

#about__detail__overview h3{
    margin-bottom: 10px;
}

#about__detail__overview p{
    text-indent: 20px;
}

#about__detail__specified{
    display: grid;
    grid-template-columns: 1fr 4fr;
    gap: 10px;
}

.about__detail__specified__tag {
    font-weight: bold;
    font-size: 15px;
    padding-top: 5px;
}

.about__detail__specified__content{
    padding: 5px;
    font-size: 15px;
}

.about__detail__specified__content a{
    color: #265077;
}

.about__detail__specified__content a:hover{
    color: #1597bb;
}

#about__location{
    margin: 10px;
}

#about__location h3{
    margin: 10px;
}


/*MAIN-------------------------------------------------------*/
.main{
    position: relative;
    width: 100%;
    height: max-content;
    min-height: 100%;
    background-color: #F0F2EF;
    opacity: 1;
    display: flex;
    justify-content: center;
}

.news{
    width: 750px;
    min-width: 400px;
    background-color: bisque rgb(201, 201, 201);
    height: max-content;
}

.ads{
    margin-top: 32px;
    width: 250px;
    background-color: brown rgb(201, 201, 201);
}


/*ADS----------------*/
.ads{
    display: none;
    flex: 0 0 auto;
    background-color: rgb(245, 245, 245) rgb(201, 201, 201);
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-right: 5px;
}

.suggest, .quote{
    width: 90%;
    background-color: white;
    margin-bottom: 10px;
    margin-top: 10px;
    text-align: center;
    border-radius: 10px;
    border: 0.5px solid rgb(201, 201, 201);
}

.suggest{
    display: flex;
    flex-direction: column;
    width: 90%;
    background-color: white;
    align-items: center;
}

.suggest__link{
    display: block;
    background-color: white;
    height: 50px;
    width: 90%;
    margin: 20px;
    color: black;
    text-decoration: none;
}

.suggest h3 {
    margin-top: 10%;
}

.suggest__info{
    display: flex;
    align-items: center;
}

.suggest__info__avatar{
    display: block;
    height: 45px;
    width: 45px;
}

.edit-company {
    margin-left: 75%;
    margin-top: 5%;
    background-color: #6dd5ed;
    border: 1px solid white;
    border-radius: 50%;
    height: 50px;
    width: 50px;
    text-align: center;
    vertical-align: center;
    cursor: pointer;
}

.edit-company i {
    margin-top: 15px;
    color: #0F2027;
    font-size: 20px;
}


.suggest__info__name{
    background-color: white;
    height: 30px;
    margin-left: 10px;
    width: 90%;
    text-align: left;
    color: #5a5a5a;
}

.suggest__info__name:hover {
    color: #265077;
    text-decoration: underline;
}

.quote{
    background-color: white;
}

.web__info {
    border: none !important;
    background-color: transparent !important;
    text-align: center;
}

.web__info > a {
    margin: 5px;
    font-size: 12px;
    text-decoration: none;
    color: #5a5a5a;
}

.web__info a:hover {
    text-decoration: underline;
    color: #265077;
}

`; 
