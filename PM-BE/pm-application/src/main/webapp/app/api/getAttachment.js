const getAttachment = (postId) => {
    const url = `http://localhost:9002/api/v1/posts/${postId}/attachments`;
    return fetch(url)
        .then(res => res.json());
};

export default getAttachment;
