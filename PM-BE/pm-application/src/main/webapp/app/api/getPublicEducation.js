const getPublicEducation = (userId) => {
    const url = `http://localhost:9002/api/v1/users/${userId}/edus`;
    return fetch(url)
        .then(res => res.json());
};

export default getPublicEducation;
