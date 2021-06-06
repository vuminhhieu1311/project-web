import { css } from '../../core/components/css-tag';

export const popupJobtypeStyle = css`
#overlay__jobtype{
    display: none;
    position: fixed;
    left: 200px;
    border-radius: 8px;
    width: 300px;
    height: 180px;
    background-color: white;
    border: 1px solid #AEADAD;
  }

  .overlay{
    display: block !important;
  }


  .overlay__jobtype__checkbox{
    margin: 10px;

  }

  .overlay__jobtype__checkbox input{
    margin: 10px;
  }

  .overlay__jobtype__checkbox div{
    text-align: center;
    margin-top: 10px;
  }

  .overlay__jobtype__checkbox button{
    padding: 10px;
    font-size: 15px;
    font-weight: bold;
    color: white;
    background: linear-gradient(
      135deg
      , #1e4157f2 0%, #1597bb 100%);
    outline: none;
    border: none;
    border-radius: 5px;
    margin-left: 20px;
    cursor: pointer;
  }

  #cancle{
    padding: 10px;
    font-size: 15px;
    font-weight: bold;
    color: white;
    background: linear-gradient(
      135deg
      , #1e4157f2 0%, #1597bb 100%);
    outline: none;
    border: none;
    border-radius: 5px;
    margin-left: 20px;
    cursor: pointer;
    display: inline-block;
  }

`;