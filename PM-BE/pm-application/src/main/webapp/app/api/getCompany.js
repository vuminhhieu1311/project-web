const getCompany = (id) => {
    const url = `http://localhost:9002/api/v1/companies/${id}`;
    return fetch(url)
        .then(res => res.json());
};
export default getCompany;
