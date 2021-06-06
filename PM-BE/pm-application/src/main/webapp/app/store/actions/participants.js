import * as types from '../constants/types';
import { clearError, createError } from './error';
import { getParticipantsData } from '../../api/participants';

const getParticipantsPayload = (payload) => {
    return {
        type: types.participants.GET,
        participants: payload
    };
};

export const getParticipants = (conversationId) => {
    return dispatch => {
        return new Promise(resolve => {
            getParticipantsData(conversationId)
                .then(data => {
                    dispatch(clearError());
                    dispatch(getParticipantsPayload(data));
                    resolve();
                })
                .catch(err => {
                    dispatch(createError(err));
                    resolve();
                });
        });
    };
};
