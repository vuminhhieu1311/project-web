const getPublicWorkEx = (userId) => {
    const url = `http://localhost:9002/api/v1/users/${userId}/experiences`;
    return fetch(url)
        .then(res => res.json());
};

export default getPublicWorkEx;
