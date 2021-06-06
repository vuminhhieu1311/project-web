const getProfile = () => {
    const url = `http://localhost:9002/api/v1/profile`;
    return fetch(url)
        .then(res => res.json());
};

export default getProfile;
