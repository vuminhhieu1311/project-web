import * as types from '../constants/types';
import initialState from '../constants/initialState';

export const participantsReducer = (state = initialState.participants, action) => {
    switch (action.type) {
        case types.participants.GET:
            return action.participants;
        default:
            return state;
    }
};
