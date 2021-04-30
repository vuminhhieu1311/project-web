import AbstractComponent from '../../AbstractComponent';

export default class Footer extends AbstractComponent {
    constructor() {
        super();
        this.loadPage('footer.html');
    }
}

window.customElements.define('app-footer', Footer);
