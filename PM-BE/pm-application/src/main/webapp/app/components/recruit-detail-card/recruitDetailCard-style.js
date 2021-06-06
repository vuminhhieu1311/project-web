import { css } from '../../core/components/css-tag';

export const recruitDetailCardStyle = css`
#recruit__info{
    background-color: white;
    border-radius: 8px;
    padding: 10px;
}

#recruit__info__overview{
    display: flex;
}

#recruit__info__overview h3{
    margin: 10px 0 0 0;
}

#recruit__info__overview span{
    font-size: 15px;
}

#recruit__info__company__avatar{
    display: block;
    margin: 10px;
}

#recruit__info__web__feature{
    display: flex;
}

#recruit__info__web__feature div{
    background: linear-gradient(
        135deg
        , #1e4157f2 0%, #1597bb 100%);
    margin: 10px;
    border-radius: 20px;
    color: white;
    font-weight: bold;
    padding: 10px;
    cursor: pointer;
    min-width: 50px;
    flex: 0 0 auto;
}

#recruit__info__web__feature div i{
    display: inline-block;
}

#recruit__info__brief__info{
    display: grid;
    grid-template-columns: 1fr 1fr;
    background-color: white;
    gap: 10px;
}

#recruit__info__brief__info div{
    background-color: white;
    padding: 10px;
    border: 0.1px solid  rgb(199, 199, 199);
    border-radius: 8px;
}

#recruit__info__brief__info h4{
    margin: 0;
    background-color: white;
}

#recruit__info__brief__info ul{
    margin: 0;
}

#recruit__info__detail__job__criteria{
    display: grid;
    grid-template-columns: 1fr 4fr;
    margin: 10px;
}

.recruit__info__detail__job__criteria__tag{
    font-weight: bold;
    font-size: 15px;
    margin: 8px;
}

.recruit__info__detail__job__criteria__detail{
    font-size: 15px;
    margin: 8px;
}


#overlay__apply{
    position: fixed;
    background-color: rgba(0,0,0,0.5);
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: none;
  }

  #popup__apply{
    background-color: white;
    position: fixed;
    top: 130px;
    left:50%;
    transform: translateX(-50%);
    height: 450px;
    border: 1px solid black;
    width: 500px;
    border-radius: 8px;
    z-index: 10;  
  }

  .show__apply{
    display: block;
  }

  #popup__apply__header{
    display: flex;
    justify-content: space-between;
    padding: 10px;
    border-bottom: 1px solid black;
  }

  #popup__apply__header__close{
    padding: 2px 5px 2px 5px;
    border-radius: 50%;
  }

  #popup__apply__header__close:hover{
    background-color: rgb(177, 176, 176);
  }

  #popup__apply__header__text{
    font-size: 20px;
    font-weight: bold;
  }

  #popup__apply__form{
    position: relative;
    height: max-content;
    padding: 10px;
  }

  .contact__info__input{
    width: 460px;
    outline: none;
    height: 30px;
    font-size: 15dpx;
    margin: 10px;
    border-radius: 5px;
    border: 1px solid rgb(206, 205, 205);
  }

  #cv{
    margin: 10px;
  }

  #popup__apply__form button{
    position: absolute;
    right: 20px;
    padding: 10px;
    font-size: 15px;
    font-weight: bold;
    color: white;
    background-color: royalblue;
    outline: none;
    border: none;
    border-radius: 5px;
    margin-left: 20px;
    cursor: pointer;
  }

  #popup__apply__form__contactinfo__basic{
    display: flex;
    margin-bottom: 20px;
  }

  #popup__apply__form__contactinfo__basic img{
    height: 50px;
    width: 50px;
    margin-right: 10px;
  }

  #form__contactinfo__basic__name{
    font-weight: bold; 
  }

  #form__contactinfo__basic__position{
    font-size: 12px;
  }

  #form__contactinfo__basic__location{
    font-size: 12px;
  }


`;