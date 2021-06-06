const getNewsFeed = () => {
    const url = `http://localhost:9002/api/v1/feed`;
    return fetch(url)
        .then(res => res.json());
};

export default getNewsFeed;
