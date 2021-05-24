/**
 * Whether the current browser supports `adoptedStyleSheets`.
 */
export const supportsAdoptingStyleSheets = (window.ShadowRoot) &&
    (window.ShadyCSS === undefined || window.ShadyCSS.nativeShadow) &&
    ('adoptedStyleSheets' in Document.prototype) &&
    ('replace' in CSSStyleSheet.prototype);

const constructionToken = Symbol();

export class CSSResult {
    /*_styleSheet;
    
    cssText;*/
    
    constructor(cssText, safeToken) {
        if (safeToken !== constructionToken) {
            throw new Error(
                'CSSResult is not constructable. Use `unsafeCSS` or `css` instead.');
        }
        
        this.cssText = cssText;
    }
    
    // Note, this is a getter so that it's lazy. In practice, this means
    // stylesheets are not created until the first element instance is made.
    get styleSheet() {
        if (this._styleSheet === undefined) {
            // Note, if `supportsAdoptingStyleSheets` is true then we assume
            // CSSStyleSheet is constructable.
            if (supportsAdoptingStyleSheets) {
                this._styleSheet = new CSSStyleSheet();
                this._styleSheet.replaceSync(this.cssText);
            } else {
                this._styleSheet = null;
            }
        }
        return this._styleSheet;
    }
    
    toString() {
        return this.cssText;
    }
}

/**
 * Wrap a value for interpolation in a [[`css`]] tagged template literal.
 *
 * This is unsafe because untrusted CSS text can be used to phone home
 * or exfiltrate data to an attacker controlled site. Take care to only use
 * this with trusted input.
 */
export const unsafeCSS = (value) => {
    return new CSSResult(String(value), constructionToken);
};

const textFromCSSResult = (value) => {
    if (value instanceof CSSResult) {
        return value.cssText;
    } else if (typeof value === 'number') {
        return value;
    } else {
        throw new Error(
            `Value passed to 'css' function must be a 'css' function result: ${
                value}. Use 'unsafeCSS' to pass non-literal values, but
            take care to ensure page security.`);
    }
};

/**
 * Template tag which which can be used with LitElement's [[LitElement.styles |
 * `styles`]] property to set element styles. For security reasons, only literal
 * string values may be used. To incorporate non-literal values [[`unsafeCSS`]]
 * may be used inside a template string part.
 */
export const css = (strings, ...values) => {
    const cssText = values.reduce(
        (acc, v, idx) => acc + textFromCSSResult(v) + strings[idx + 1],
        strings[0]);
    return new CSSResult(cssText, constructionToken);
};
