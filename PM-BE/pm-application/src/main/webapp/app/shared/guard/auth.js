import store from '../../store/store';
import { getAccount } from '../../store/actions/auth';

export const authenticated = () => {
    return new Promise(async (resolve) => {
        await store.dispatch(getAccount());
        const state = await store.getState();
        const isAuth = state.auth.authenticated;
        
        resolve(isAuth);
    });
};

export const authorized = (authorities) => {
    if (!Array.isArray(authorities)) {
        authorities = [authorities];
    }
    
    const userAuthorities = ['ROLE_USER'];
    
    const isAuthorized = userAuthorities.some(authority => authorities.includes(authority));
    
    return new Promise((resolve) => {
        resolve(isAuthorized);
    });
};
