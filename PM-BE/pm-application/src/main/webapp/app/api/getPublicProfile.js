const getPublicProfile = (userId) => {
    const url = `http://localhost:9002/api/v1/users/${userId}/profile`;
    return fetch(url)
        .then(res => res.json());
};

export default getPublicProfile;
