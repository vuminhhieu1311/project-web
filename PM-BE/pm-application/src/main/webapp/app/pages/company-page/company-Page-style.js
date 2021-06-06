import { css } from '../../core/components/css-tag';

export const companyPageStyle = css`
    
/*MAIN---------------------------*/
:host {
    font-size: 1rem;
    color: #5a5a5a;
}

main{
    display: flex;
    justify-content: center;
    background-color: rgb(245, 245, 245);
    margin-top: 70px;
}

#main-content{
    margin-top: 0;
    width: 800px;
    height: auto;
    display: flex;
    flex-direction: column;
    background-color: rgb(245, 245, 245);
    padding: 10px;
    margin-left: 50px;
    align-items: center;
}

.main-content-div{
    width: 780px;
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
`; 
