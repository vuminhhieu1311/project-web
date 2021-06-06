import {
    UPDATE_PROFILE
  } from '../actions/updateProfile';

const INITIAL_STATE = {
    country: 'Hieu'
};

export const profileReducer = (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case UPDATE_PROFILE:
            return {
                ...state,
                country: action.country
              };
        default:
            return state;
    }
};