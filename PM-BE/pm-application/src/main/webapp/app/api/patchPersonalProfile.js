import { getXSRFToken } from '../shared/utils/header-utils';
const patchPersonalProfile = (asString) => {
    const url = 'http://localhost:9002/api/v1/profile';
    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('X-XSRF-TOKEN', getXSRFToken(document.cookie));
    const request = new Request(url, {
        method: 'PATCH',
        headers: headers,
        body: asString,
    });
    return fetch(request)
        .then(res => res.json())
}
export default patchPersonalProfile;
