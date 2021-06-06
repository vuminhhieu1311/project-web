import * as types from '../constants/types';
import initialState from '../constants/initialState';

export const authReducer = (state = initialState.account, action) => {
    switch (action.type) {
        case types.account.GET:
            const {account} = action;
            
            return {
                ...state,
                userId: account.id,
                username: account.login,
                email: account.email
            };
        case types.auth.LOGIN_SUCCESS:
            return {
                ...state,
                authenticated: true
            };
        case types.auth.LOGOUT_SUCCESS:
            const {logout} = action;
            
            return {
                ...initialState.account,
                logout
            };
        default:
            return state;
    }
};
