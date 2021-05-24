import { defaultTemplateProcessor } from './helper/default-template-processor';
import { SVGTemplateResult, TemplateResult } from './helper/template-result';

export { DefaultTemplateProcessor, defaultTemplateProcessor } from './helper/default-template-processor';
export { directive, isDirective } from './helper/directive';
// TODO: remove line when we get NodePart moving methods
export { removeNodes, reparentNodes } from './helper/dom';
export { noChange, nothing } from './helper/part';
export {
    AttributeCommitter,
    AttributePart,
    BooleanAttributePart,
    EventPart,
    isIterable,
    isPrimitive,
    NodePart,
    PropertyCommitter,
    PropertyPart
} from './helper/parts';
export { parts, render } from './helper/render';
export { templateCaches, templateFactory } from './helper/template-factory';
export { TemplateInstance } from './helper/template-instance';
export { SVGTemplateResult, TemplateResult } from './helper/template-result';
export { createMarker, isTemplatePartActive, Template } from './helper/template';

/**
 * Interprets a template literal as an HTML template that can efficiently
 * render to and update a container.
 */
export const html = (strings, ...values) =>
    new TemplateResult(strings, values, 'html', defaultTemplateProcessor);

/**
 * Interprets a template literal as an SVG template that can efficiently
 * render to and update a container.
 */
export const svg = (strings, ...values) =>
    new SVGTemplateResult(strings, values, 'svg', defaultTemplateProcessor);
