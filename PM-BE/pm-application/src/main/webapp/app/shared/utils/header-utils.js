export const getXSRFToken = (cookie) => {
    return cookie.split(';').find((value => value.includes('XSRF-TOKEN')))
        .split('=')[1];
};
