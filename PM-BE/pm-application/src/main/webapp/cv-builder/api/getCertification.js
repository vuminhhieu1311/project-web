const getCertification = () => {
    const url = `http://localhost:9002/api/v1/profile/certs`;
    return fetch(url)
        .then(res => res.json());
};

export default getCertification;
