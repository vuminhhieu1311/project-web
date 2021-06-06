const getAllJobs = () => {
    const url = `http://localhost:9002/api/v1/public/jobs`;
    return fetch(url)
        .then(res => res.json());
};

export default getAllJobs;
