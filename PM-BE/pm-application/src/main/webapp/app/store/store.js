import createStore from '../core/store/createStore';
import applyMiddleware from '../core/store/applyMiddleware';
import thunk from '../core/store/middleware/thunk';
import rootReducer from './reducers/root';

const store = createStore(rootReducer, {}, applyMiddleware(thunk));

export default store;
