const getEducation = () => {
    const url = `http://localhost:9002/api/v1/profile/educations`;
    return fetch(url)
        .then(res => res.json());
};

export default getEducation;
