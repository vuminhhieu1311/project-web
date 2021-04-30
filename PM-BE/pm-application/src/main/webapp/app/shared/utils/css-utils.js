export function importCss(paths) {
    paths.forEach(path => {
        const link = document.createElement('link');
        link.href = path;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        document.getElementsByTagName('head')[0].appendChild(link);
    });
}

export function importStyle(style) {
    const css = document.createElement('style');
    css.innerHTML = style;
    document.getElementsByTagName('head')[0].appendChild(css);
}
