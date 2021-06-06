const getUserPosts = () => {
    const url = `http://localhost:9002/api/v1/me/posts`;
    return fetch(url)
        .then(res => res.json());
};

export default getUserPosts;
