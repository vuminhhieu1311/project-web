const searchCompany = (query) => {
    const url = `http://localhost:9002/api/v1/_search/companies/${query}`;
    return fetch(url)
        .then(res => res.json());
};

export default searchCompany;
