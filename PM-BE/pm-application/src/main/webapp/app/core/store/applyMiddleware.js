import { compose } from '../../shared/utils/function-utils';

const applyMiddleware = (...middlewares) => {
    return createStore => (reducer, preloadedState = undefined) => {
        const store = createStore(reducer, preloadedState);
        
        let dispatch = () => {
            throw new Error('Dispatching while constructing middleware is not allowed. ' +
                'Other middleware would not be applied to this dispatch.');
        };
        
        const middlewareAPI = {
            dispatch: (action, ...args) => dispatch(action, ...args),
            getState: store.getState
        };
        const chain = middlewares.map(middleware => middleware(middlewareAPI));
        dispatch = compose(...chain)(store.dispatch);
        
        return {
            ...store,
            dispatch
        };
    }
};

export default applyMiddleware;
