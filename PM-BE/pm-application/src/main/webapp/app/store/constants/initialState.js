const initialState = {
    error: null,
    loading: false,
    account: {
        userId: null,
        authenticated: false,
        username: null,
        email: null,
        logout: null
    },
    conversations: {},
    participants: {}
};

export default initialState;
