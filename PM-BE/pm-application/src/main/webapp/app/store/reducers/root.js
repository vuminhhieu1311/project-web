import combineReducers from '../../core/store/combineReducers';
import { authReducer } from './auth';

const rootReducer = combineReducers({
    auth: authReducer
});

export default rootReducer;
