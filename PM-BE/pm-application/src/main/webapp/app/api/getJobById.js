const getJobById = (id) => {
    const url = `http://localhost:9002/api/v1/jobs/${id}`;
    return fetch(url)
        .then(res => res.json());
};

export default getJobById;
