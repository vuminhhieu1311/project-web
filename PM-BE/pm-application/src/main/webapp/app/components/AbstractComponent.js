/**
 * Base enhanced web component
 */
export default class AbstractComponent extends HTMLElement {
    /**
     * loaded: determine whether html template page is completely fetched.
     * <br>
     * state: state of the component.
     */
    static get observedAttributes() {
        return ['loaded', 'state'];
    }

    get loaded() {
        return this.getAttribute('loaded');
    }

    set loaded(value) {
        this.setAttribute('loaded', value);
    }

    get state() {
        return JSON.parse(this.getAttribute('state'));
    }

    set state(object) {
        this.setAttribute('state', JSON.stringify(object));
    }

    constructor() {
        super();
        this.loaded = false;
        this.state = {};
    }

    /**
     * Set state of component
     * @param obj: object contains key value need to be updated in state
     */
    setState(obj) {
        if (typeof obj === 'function') {
            // TODO
        }

        if (typeof obj === 'object') {
            this.state = {
                ...this.state,
                ...obj
            };
        }
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'loaded') {
            if (this.loaded) {
                this.componentDidMount();
            }
        }

        if (name === 'state') {
            const prevState = JSON.parse(oldValue);
            const currentState = JSON.parse(newValue);

            if (currentState !== prevState) {
                this.componentDidUpdate(prevState, currentState);
            }
        }
    }

    disconnectedCallback() {
        this.loaded = false;
        this.state = {};
        this.componentDidUnmount();
    }

    /**
     * Invoked each time the custom element is appended into a document-connected element
     * and document is completely loaded
     */
    componentDidMount() {

    }

    /**
     * Invoked each time one of the custom element's attributes is added, removed, or changed.
     * @param prevState: previous state
     * @param state: current state
     */
    componentDidUpdate(prevState, state) {

    }

    /**
     * Invoked each time the custom element is disconnected from the document's DOM
     */
    componentDidUnmount() {

    }

    /**
     * Load html template page
     * @param page: html template to be fetched
     */
    loadPage(page) {
        this.render(page).then(html => {
            this.innerHTML = html;
            this.loaded = true;
        });
    }

    async render(page) {
        const response = await fetch(page);
        
        return response.text();
    }
}
