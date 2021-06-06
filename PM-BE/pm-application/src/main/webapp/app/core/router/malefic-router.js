import { parseParams, parseQuery, testRoute } from './helper/router-utils';

export function router(base) {
    return class extends base {
        static get properties() {
            return {
                route: {type: String, reflect: true, attribute: 'route'},
                canceled: {type: Boolean}
            };
        }
        
        constructor(...args) {
            super(...args);
            
            this.route = '';
            this.canceled = false;
        }
        
        connectedCallback(...args) {
            super.connectedCallback(...args);
            
            this.routing(this.constructor.routes, (...args) => this.router(...args));
            window.addEventListener('route', () => {
                this.routing(this.constructor.routes, (...args) => this.router(...args));
            });
            
            window.onpopstate = () => {
                window.dispatchEvent(new CustomEvent('route'));
            }
        }
        
        routed(name, params, query, data, component, resolve, callback, localCallback) {
            localCallback && localCallback(name, params, query, data, component, resolve);
            callback(name, params, query, data, component, resolve);
        }
        
        routing(routes, callback) {
            this.canceled = true;
            
            const uri = decodeURI(window.location.pathname);
            const querystring = decodeURI(window.location.search);
            
            let notFoundRoute = routes.filter(route => route.pattern === '*')[0];
            let activeRoute = routes.filter(route => route.pattern !== '*' && testRoute(uri, route.pattern))[0];
            let query = parseQuery(querystring);
            
            if (activeRoute) {
                activeRoute.params = parseParams(activeRoute.pattern, uri);
                activeRoute.data = activeRoute.data || {};
                if (typeof activeRoute.resolve !== 'function') {
                    throw new Error('resolve must be a function');
                }
                
                if (activeRoute.authentication && activeRoute.authentication.authenticate && typeof activeRoute.authentication.authenticate === 'function') {
                    this.canceled = false;
                    Promise.resolve(activeRoute.authentication.authenticate.bind(this).call())
                        .then((authenticated) => {
                            if (!this.canceled) {
                                if (authenticated) {
                                    if (activeRoute.authorization && activeRoute.authorization.authorize && typeof activeRoute.authorization.authorize === 'function') {
                                        this.canceled = false;
                                        Promise.resolve(activeRoute.authorization.authorize.bind(this).call())
                                            .then((authorizatied) => {
                                                if (!this.canceled) {
                                                    if (authorizatied) {
                                                        this.routed(activeRoute.name, activeRoute.params, query, activeRoute.data, activeRoute.component, activeRoute.resolve, callback, activeRoute.callback);
                                                    } else {
                                                        this.routed(activeRoute.authorization.unauthorized.name, activeRoute.params, query, activeRoute.data, activeRoute.authorization.unauthorized.component, activeRoute.authorization.unauthorized.resolve, callback, activeRoute.callback);
                                                    }
                                                }
                                            })
                                    } else {
                                        this.routed(activeRoute.name, activeRoute.params, query, activeRoute.data, activeRoute.component, activeRoute.resolve, callback, activeRoute.callback);
                                    }
                                } else {
                                    this.routed(activeRoute.authentication.unauthenticated.name, activeRoute.params, query, activeRoute.data, activeRoute.authentication.unauthenticated.component, activeRoute.authentication.unauthenticated.resolve, callback, activeRoute.callback);
                                }
                            }
                        });
                } else if (activeRoute.authorization && activeRoute.authorization.authorize && typeof activeRoute.authorization.authorize === 'function') {
                    throw new Error('authentication is not implement in routing yet');
                } else {
                    this.routed(activeRoute.name, activeRoute.params, query, activeRoute.data, activeRoute.component, activeRoute.resolve, callback, activeRoute.callback);
                }
            } else if (notFoundRoute) {
                notFoundRoute.data = notFoundRoute.data || {};
                this.routed(notFoundRoute.name, {}, query, notFoundRoute.data, notFoundRoute.component, notFoundRoute.resolve, callback, notFoundRoute.callback);
            }
        }
    };
}

export function navigator(base) {
    return class extends base {
        navigate(href) {
            window.history.pushState({}, null, href);
            window.dispatchEvent(new CustomEvent('route'));
        }
    };
}

export function outlet(base) {
    return class extends base {
        static get properties() {
            return {
                activeRoute: {type: String, reflect: true, attribute: 'activeroute'},
                resolve: {type: Function},
                props: {type: Object}
            };
        }
        
        attributeChangedCallback(name, oldVal, newVal) {
            super.attributeChangedCallback(name, oldVal, newVal);
            
            name === 'activeroute' && newVal !== '' && setTimeout(() => this.outlet());
        }
        
        outlet() {
            const component = this.activeRoute;
            const props = JSON.parse(this.props);
    
            while (this.firstChild) {
                this.removeChild(this.firstChild);
            }
            const updateView = () => {
                const view = document.createElement(component);
                view.setAttribute('props', JSON.stringify(props));
                this.appendChild(view);
            };
    
            if (this.resolve && typeof this.resolve === 'function') {
                this.resolve().then(updateView);
            } else {
                updateView();
            }
        }
    };
}

export function withRouter(base) {
    return class extends base {
        connectedCallback() {
            super.connectedCallback();
            const props = JSON.parse(this.getAttribute('props'));
            this.params = props.params;
            this.query = props.query;
            this.data = props.data;
        }
    }
}
