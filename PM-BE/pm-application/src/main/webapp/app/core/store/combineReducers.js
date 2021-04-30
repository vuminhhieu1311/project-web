const combineReducers = (reducers) => {
    const reducerKeys = Object.keys(reducers);
    let finalReducers = {};

    reducerKeys.forEach(key => {
        if (typeof reducers[key] === 'function') {
            finalReducers[key] = reducers[key];
        }
    });
    const finalReducerKeys = Object.keys(finalReducers);

    return (state = {}, action) => {
        let hasChanged = false;
        const nextState = {};

        finalReducerKeys.forEach(key => {
            const reducer = finalReducers[key];
            const previousStateForKey = state[key];
            const nextStateForKey = reducer(previousStateForKey, action);

            if (typeof nextStateForKey === 'undefined') {
                throw new Error('State must not be undefined');
            }

            nextState[key] = nextStateForKey;
            hasChanged = hasChanged || nextStateForKey !== previousStateForKey;
        });
        hasChanged = hasChanged || Object.keys(finalReducers).length !== Object.keys(state).length;

        return hasChanged ? nextState : state;
    };
};

export default combineReducers;
