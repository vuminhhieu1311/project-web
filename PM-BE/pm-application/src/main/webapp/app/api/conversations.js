export const getConversationsData = async () => {
    const response = await fetch(`${location.origin}/api/v1/conversations`);
    const body = await response.json();
    
    if (response.status === 200) {
        return body;
    } else {
        throw new Error(body.message);
    }
};
