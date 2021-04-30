import { getXSRFToken } from '../shared/utils/header-utils';

export const getAccountData = async () => {
    const response = await fetch(`${location.origin}/api/v1/account`);
    const body = await response.json();
    
    if (response.status === 200) {
        return body;
    } else {
        throw new Error(body.message);
    }
};

export const logoutAccount = async () => {
    const response = await fetch(`${location.origin}/api/v1/logout`, {
        method: 'POST',
        headers: {
            'X-XSRF-TOKEN': getXSRFToken(document.cookie)
        }
    });
    const body = await response.json();
    
    if (response.status === 200) {
        return body;
    } else {
        throw new Error(body.message);
    }
};
