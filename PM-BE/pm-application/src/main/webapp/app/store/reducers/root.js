import combineReducers from '../../core/store/combineReducers';
import { authReducer } from './auth';
import { conversationsReducer } from './conversations';
import { participantsReducer } from './participants';

const rootReducer = combineReducers({
    auth: authReducer,
    conversations: conversationsReducer,
    participants: participantsReducer
});

export default rootReducer;
