const editor = grapesjs.init({
  container: "#editor",
  fromElement: true, // Get the content for the canvas directly from the element
  storageManager: false,
  blockManager: {
    appendTo: "#blocks"
  },
  allowScripts: 1,
  styleManager: {
    appendTo: "#styles-container",
    sectors: [
      {
        name: 'Decorations',
        open: true,
        buildProps: ['color', 'font-size', 'font-family', 'font-weight', 'text-align', 'background-color', 'background-image',
          'letter-spacing', 'line-height', 'border', 'border-radius', 'box-shadow'],
        properties: [
          {
            id: 'text-align',
            name: 'Text align',
            type: 'select',
            // List of options, available only for 'select' and 'radio'  types
            options: [
              { value: 'left', name: 'Left' },
              { value: 'center', name: 'Center' },
              { value: 'right', name: 'Right' },
            ],
          }
        ]
      },
      {
        name: "Dimension",
        open: true,
        buildProps: ["width", "height", "margin", "padding"],
        properties: [
          {
            type: "integer",
            property: "width",
            units: ["px", "%"],
            defaults: "auto",
            min: 0,
          },
          {
            type: "integer",
            property: "height",
            units: ["px", "%"],
            defaults: "auto",
            min: 0,
          },
        ],
      },
    ]
  },

  layerManager: {
    appendTo: "#layers-container",
  },
  panels: {
    defaults: [
      {
        id: "basic-actions",
        el: ".panel__basic-actions",
        buttons: [
          {
            id: "visibility",
            active: true, // active by default
            className: "btn-toggle-borders",
            label: '<i class="fas fa-border-none"></i>',
            command: "sw-visibility", // Built-in command
          },
        ],
      },
      {
        id: "panel-devices",
        el: ".panel__devices",
        buttons: [
          {
            id: "device-desktop",
            label: '<i class="fa fa-television"></i>',
            command: "set-device-desktop",
            active: true,
            togglable: false,
          },
          {
            id: "device-tablet",
            label: '<i class="fa fa-tablet"></i>',
            command: "set-device-tablet",
            togglable: false,
          },
          {
            id: "device-mobile",
            label: '<i class="fa fa-mobile"></i>',
            command: "set-device-mobile",
            togglable: false,
          },
        ],
      },
    ],
  },

  plugins: ['gjs-preset-newsletter'],
  pluginsOpts: {
    'gjs-preset-newsletter': {
    }
  }
});

editor.Panels.removePanel('devices-c'); // Delete the default device

const bm = editor.BlockManager;
bm.add("multiple-lines", {
  label: "multiple-lines",
  content:
    '<div><div><h1>This is the title</h1></div><div><p>This is the text</p></div></div>',
  attributes: {
    class: "fa fa-grip-lines"
  }
});

function HTMLtoPDF() {
  source = editor.runCommand('gjs-get-inlined-html');

  var opt = {
    margin: 0.5,
    filename: 'mycv.pdf',
    image: { type: 'jpeg', quality: 0.98 },
    html2canvas: { scale: 2 },
    jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
  };
  html2pdf().from(source).set(opt).save();
}