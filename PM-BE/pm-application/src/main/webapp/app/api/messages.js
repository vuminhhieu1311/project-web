export const getMessageHistoryData = async (conversationId) => {
    const response = await fetch(`${location.origin}/api/v1/conversations/${conversationId}/message-history`);
    const body = await response.json();
    
    if (response.status === 200) {
        return body;
    } else {
        throw new Error(body.message);
    }
};
