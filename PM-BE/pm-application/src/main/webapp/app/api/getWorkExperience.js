const getWorkExperience = () => {
    const url = `http://localhost:9002/api/v1/profile/experiences`;
    return fetch(url)
        .then(res => res.json());
};

export default getWorkExperience;
