
const tabBtn = document.querySelectorAll(".tab");
const tab = document.querySelectorAll(".tab-show");

function tabs(panelIndex) {
    tab.forEach(function (node) {
        node.style.display = "none";
    });
    tab[panelIndex].style.display = "block";
}
tabs(0);


