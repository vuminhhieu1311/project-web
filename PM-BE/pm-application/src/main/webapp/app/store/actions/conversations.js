import * as types from '../constants/types';
import { clearError, createError } from './error';
import { getConversationsData } from '../../api/conversations';

const getConversationsPayload = (payload) => {
    return {
        type: types.conversations.GET,
        conversations: payload
    };
};

export const getConversations = () => {
    return dispatch => {
        return new Promise(resolve => {
            getConversationsData()
                .then(data => {
                    dispatch(clearError());
                    dispatch(getConversationsPayload(data));
                    resolve();
                })
                .catch(err => {
                    dispatch(createError(err));
                    resolve();
                });
        });
    };
};
