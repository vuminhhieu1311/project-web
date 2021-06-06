const getCertById = (id) => {
    const url = `http://localhost:9002/api/v1/profile/certs/${id}`;
    return fetch(url)
        .then(res => res.json());
};

export default getCertById;
