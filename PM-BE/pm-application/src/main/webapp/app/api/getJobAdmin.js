const getJobAdmin = () => {
    const url = `http://localhost:9002/api/v1/jobs`;
    return fetch(url)
        .then(res => res.json());
};

export default getJobAdmin;
