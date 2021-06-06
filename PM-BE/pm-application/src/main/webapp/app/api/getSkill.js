const getSkill = () => {
    const url = `http://localhost:9002/api/v1/profile/skills`;
    return fetch(url)
        .then(res => res.json());
};

export default getSkill;
