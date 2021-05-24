import routes from './routes';
import MaleficComponent from './core/components/MaleficComponent';
import { router } from './core/router/malefic-router';
import { html } from './core/components/malefic-html';

import './routes/Link';
import './routes/Outlet';

class App extends router(MaleficComponent) {
    static get properties() {
        return {
            route: {type: String},
            params: {type: Object},
            query: {type: Object},
            data: {type: Object},
            component: {type: String},
            resolve: {type: Function}
        };
    }
    
    static get routes() {
        return routes;
    }
    
    constructor() {
        super();
        this.route = '';
        this.params = {};
        this.query = {};
        this.data = {};
        this.component = '';
        this.resolve = () => {};
    }
    
    router(route, params, query, data, component, resolve) {
        this.route = route;
        this.params = params;
        this.query = query;
        this.data = data;
        this.component = component;
        this.resolve = resolve;
    
        if (data.title) {
            document.getElementsByTagName('title')[0].innerHTML = data.title;
        }
    }
    
    render() {
        return html`
            <app-outlet activeroute=${this.component} .resolve="${this.resolve}"></app-outlet>
        `;
    }
}

customElements.define('app-main', App);
