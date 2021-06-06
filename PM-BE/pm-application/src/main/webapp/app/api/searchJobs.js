const searchJobbs = () => {
    const url = `http://localhost:9002/api/v1/_search/jobs`;
    return fetch(url)
        .then(res => res.json());
};

export default searchJobbs;
