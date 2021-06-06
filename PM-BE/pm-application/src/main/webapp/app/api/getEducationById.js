const getEducationById = (id) => {
    const url = `http://localhost:9002/api/v1/profile/educations/${id}`;
    return fetch(url)
        .then(res => res.json());
};

export default getEducationById;
