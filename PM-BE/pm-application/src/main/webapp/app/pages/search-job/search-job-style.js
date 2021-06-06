import { css } from '../../core/components/css-tag';

export const searchJobStyle = css`
body{
    overflow: hidden;
}

.search__filter{
  margin-top: 70px;
  width: 100%;
  height: 60px;
  padding: 10px;
  background-color: white;
  display: flex;
  border-bottom: 0.5px solid rgb(180, 180, 180);
}

.search__filter div{
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 18px;
  background: linear-gradient(
    135deg
    , #1e4157f2 0%, #1597bb 100%);
  padding: 10px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

.search__filter div{
    min-width: max-content;
}

.search__filter input{
  display: block;
  border: 2px solid rgb(136, 136, 136);
  border-radius: 20px;
  outline: none;
  font-size: 20px;
  padding: 10px;
  margin-right: 20px;
}

.main{
    display: grid;
    grid-template-columns: 1fr 2fr;
    width: 100%;
}

.main__left{
    background-color: rgb(196, 221, 255);
    min-width: 400px;
    height: 80vh;
    margin-left: 10px;
    margin-right: 10px;
    overflow-y: scroll;
}

.main__right{
    height: 80vh;
    overflow-y: auto;
}

/* width */
::-webkit-scrollbar {
    width: 10px;
  }
  
  /* Track */
  ::-webkit-scrollbar-track {
    background: #f1f1f1; 
  }
   
  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: rgb(211, 211, 211); 
  }
  
  /* Handle on hover */
  ::-webkit-scrollbar-thumb:hover {
    background: rgb(172, 172, 172); 
  }

`;