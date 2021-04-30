import * as types from '../constants/types';

export const loading = () => {
    return {
        type: types.app.LOADING
    };
};

export const loaded = () => {
    return {
        type: types.app.LOADED
    };
};
