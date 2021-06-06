const getWorkExById = (id) => {
    const url = `http://localhost:9002/api/v1/profile/experiences/${id}`;
    return fetch(url)
        .then(res => res.json());
};

export default getWorkExById;
