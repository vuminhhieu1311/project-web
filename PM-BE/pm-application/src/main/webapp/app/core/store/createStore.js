import { isPlainObject } from '../../shared/utils/object-utils';

const createStore = (
    reducer,
    preloadedState = undefined,
    enhancer = undefined
) => {
    let currentReducer = reducer;
    let currentListeners = [];
    let nextListeners = currentListeners;
    let currentState = preloadedState;
    let isDispatching = false;
    
    const ensureCanMutateNextListeners = () => {
        if (currentListeners === nextListeners) {
            nextListeners = [...currentListeners];
        }
    };
    
    if (typeof enhancer !== 'undefined') {
        return enhancer(createStore)(reducer, preloadedState);
    }
    
    return {
        getState: () => {
            if (isDispatching) {
                throw new Error('Could not get state of store while dispatching');
            }
            
            return currentState;
        },
        dispatch: (action) => {
            if (!isPlainObject(action)) {
                throw new Error('Action must be plain object');
            }
            
            if (isDispatching) {
                throw new Error('Could not call dispatch while dispatching');
            }
            
            try {
                isDispatching = true;
                currentState = currentReducer(currentState, action);
            } finally {
                isDispatching = false;
            }
            
            const listeners = (currentListeners = nextListeners);
            listeners.forEach((listener) => {
                listener();
            });
            
            return action;
        },
        subscribe: (newListener) => {
            if (typeof newListener !== 'function') {
                throw new Error('Listener must be a function');
            }
            
            if (isDispatching) {
                throw new Error('Could not subscribe new listener while dispatching');
            }
            
            let isSubscribed = true;
            ensureCanMutateNextListeners();
            nextListeners.push(newListener);
            
            const unsubscribe = () => {
                if (!isSubscribed) {
                    return;
                }
                
                if (isDispatching) {
                    throw new Error('Could not unsubscribe while dispatching');
                }
                
                isSubscribed = false;
                ensureCanMutateNextListeners();
                nextListeners = nextListeners.filter((l) => l !== newListener);
                currentListeners = null;
            };
            
            return unsubscribe;
        }
    };
};

export default createStore;
