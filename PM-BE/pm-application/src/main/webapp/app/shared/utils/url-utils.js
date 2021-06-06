export const getLoginUrl = () => {
    const port = location.port ? `:${location.port}` : '';
    
    // If you have configured multiple OIDC providers, then, you can update this URL to /login.
    // It will show a Spring Security generated login page with links to configured OIDC providers.
    return `http://${location.hostname}${port}/oauth2/authorization/oidc`;
};

export const transformImage = (url, options) => {
    if (url.includes('/image/upload/')) {
        return url.replace('/image/upload/', `/image/upload/${options}/`);
    }
    
    return url;
};
