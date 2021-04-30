import './components/router/Router';

document.querySelector('#root').innerHTML = `
    <wc-router>
        <wc-route path="/" component="app-home"></wc-route>
        <wc-route path="/profile" title="Profile" component="app-profile"></wc-route>
        <wc-route path="*" title="Not Found" component="app-not-found"></wc-route>
        <wc-outlet></wc-outlet>
    </wc-router>
`;
