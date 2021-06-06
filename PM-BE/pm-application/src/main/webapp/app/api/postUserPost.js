import { getXSRFToken } from '../shared/utils/header-utils';
const postUserPost = (formData) => {
    const url = `http://localhost:9002/api/v1/me/posts`;
    const headers = new Headers();
    headers.append('X-XSRF-TOKEN', getXSRFToken(document.cookie));
    const request = new Request(url, {
        method: 'POST',
        headers: headers,
        body: formData,
    });
    return fetch(request)
        .then(res => res.json())
}
export default postUserPost;

