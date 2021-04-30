import * as types from '../constants/types';
import { clearError, createError } from './error';
import { getAccountData, logoutAccount } from '../../api/auth';

const getAccountPayload = (payload) => {
    return {
        type: types.account.GET,
        account: payload
    };
};

const loginSuccess = () => {
    return {
        type: types.auth.LOGIN_SUCCESS
    };
};

const logoutSuccess = (payload) => {
    return {
        type: types.auth.LOGOUT_SUCCESS,
        logout: payload
    };
};

export const getAccount = () => {
    return dispatch => {
        return new Promise(resolve => {
            getAccountData()
                .then(data => {
                    dispatch(clearError());
                    dispatch(getAccountPayload(data));
                    dispatch(loginSuccess());
                    resolve();
                })
                .catch(err => {
                    dispatch(createError(err));
                    resolve();
                });
        });
    };
};

export const logout = () => {
    return dispatch => {
        return new Promise(resolve => {
            logoutAccount()
                .then((logout) => {
                    dispatch(clearError());
                    dispatch(logoutSuccess(logout));
                    resolve();
                })
                .catch(err => {
                    dispatch(createError(err));
                    resolve();
                })
        });
    };
};
