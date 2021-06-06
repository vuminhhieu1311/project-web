import * as types from '../constants/types';
import initialState from '../constants/initialState';

export const conversationsReducer = (state = initialState.conversations, action) => {
    switch (action.type) {
        case types.conversations.GET:
            return action.conversations;
        default:
            return state;
    }
};
