const getFollowings = () => {
    const url = `http://localhost:9002/api/v1/social/followings`;
    return fetch(url)
        .then(res => res.json());
};

export default getFollowings;
