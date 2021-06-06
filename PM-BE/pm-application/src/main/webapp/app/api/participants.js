export const getParticipantsData = async (conversationId) => {
    const response = await fetch(`${location.origin}/api/v1/conversations/${conversationId}/participants`);
    const body = await response.json();
    
    if (response.status === 200) {
        return body;
    } else {
        throw new Error(body.message);
    }
};
