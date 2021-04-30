import { match } from '../../shared/utils/router-utils';

class Router extends HTMLElement {
    constructor() {
        super();
    }
    
    get root() {
        return window.location.pathname;
    }
    
    get routes() {
        return Array.from(this.querySelectorAll('wc-route'))
            .filter(node => node.parentNode === this)
            .map(r => ({
                path: r.getAttribute('path'),
                title: r.getAttribute('title'),
                component: r.getAttribute('component'),
                resourceUrl: r.getAttribute('resourceUrl')
            }));
    }
    
    get outlet() {
        return this.querySelector('wc-outlet');
    }
    
    navigate(url) {
        const matchedRoute = match(this.routes, url);
        
        if (matchedRoute !== null) {
            this.activeRoute = matchedRoute;
            window.history.pushState(null, null, url);
            // Update the DOM
            this.update();
        }
    }
    
    update() {
        const {component, title, params = {}, resourceUrl = null} = this.activeRoute;
        if (component) {
            while (this.outlet.firstChild) {
                this.outlet.removeChild(this.outlet.firstChild);
            }
        
            const updateView = () => {
                const view = document.createElement(component);
                document.title = title || document.title;
                for (let key in params) {
                    if (key !== '*') {
                        view.setAttribute(key, params[key]);
                    }
                }
                this.outlet.appendChild(view);
                this.updateLinks();
            };
        
            if (resourceUrl !== null) {
                import(resourceUrl).then(updateView);
            } else {
                updateView();
            }
        }
    }
    
    updateLinks() {
        this.querySelectorAll('a[route]').forEach(link => {
            const target = link.getAttribute('route');
            link.setAttribute('href', target);
            link.onclick = e => {
                e.preventDefault();
                this.navigate(target);
            };
        });
    }
    
    connectedCallback() {
        this.updateLinks();
        this.navigate(window.location.pathname);
        
        window.addEventListener('popstate', this._handlePopstate);
    }
    
    disconnectedCallback() {
        window.removeEventListener('popstate', this._handlePopstate);
    }
    
    _handlePopstate = () => {
        this.navigate(window.location.pathname);
    }
    
    go(url) {
        this.navigate(url);
    }
    
    back() {
        window.history.go(-1);
    }
}

window.customElements.define('wc-router', Router);
