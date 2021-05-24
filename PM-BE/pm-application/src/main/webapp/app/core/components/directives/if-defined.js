import { AttributePart, directive } from '../malefic-html';

const previousValues = new WeakMap();

/**
 * For AttributeParts, sets the attribute if the value is defined and removes
 * the attribute if the value is undefined.
 *
 * For other part types, this directive is a no-op.
 */
export const ifDefined = directive((value) => (part) => {
    const previousValue = previousValues.get(part);
    
    if (value === undefined && part instanceof AttributePart) {
        // If the value is undefined, remove the attribute, but only if the value
        // was previously defined.
        if (previousValue !== undefined || !previousValues.has(part)) {
            const name = part.committer.name;
            part.committer.element.removeAttribute(name);
        }
    } else if (value !== previousValue) {
        part.setValue(value);
    }
    
    previousValues.set(part, value);
});
