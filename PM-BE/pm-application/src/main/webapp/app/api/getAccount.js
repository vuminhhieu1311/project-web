const getAccount = () => {
    const url = `http://localhost:9002/api/v1/account`;
    return fetch(url)
        .then(res => res.json());
};

export default getAccount;
