import { css } from '../../core/components/css-tag';

export const GeneralSearchStyle = css`

main{
    margin-top: 100px;
}

.search__filter{
    background-color: white;
    padding: 10px;
    border-bottom: 0.5px solid rgb(175, 175, 175);
}

.search__filter input{
    height: 30px;
    width: 30%;
    min-width: 300px;
    font-size: 15px;
    outline: none;
    text-indent: 10px;
    border-radius: 20px;
    border: 2px solid gray;
}

.search__filter button{
    background: linear-gradient(
    135deg
    , #1e4157f2 0%, #1597bb 100%);
    padding: 10px;
    font-weight: bold;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 20px;
}

.search__filter select{
    font-size: 15px;
    outline: none;
    text-indent: 10px;
    border-radius: 20px;
    border: 2px solid gray;
    height: 30px;
    width: 110px;
}

.main{
    background-color: whitesmoke;
    margin: 10px;
}

`;
