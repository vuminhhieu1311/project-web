
const tabBtn = document.querySelectorAll(".tab");
const tab = document.querySelectorAll(".tab-show");
const tabMenus = document.querySelectorAll('.tab');

function tabs(panelIndex) {
    tab.forEach(function (node) {
        node.style.display = "none";
    });
    tab[panelIndex].style.display = "block";

    tabMenus.forEach(tabMenu => {
        tabMenu.classList.remove('tab-active');
    })
}
tabs(0);

tabMenus.forEach(tab => {
    tab.addEventListener('click', () => {
        tab.classList.add('tab-active');
    })
})

/*--------------------------------------*/

const wrapper = document.querySelector(".wrapper");
const defaultBtn = document.querySelector("#default-btn");
const customBtn = document.querySelector("#custom-btn");
const cancelBtn = document.querySelector(".cancel-btn");
const img = document.querySelector(".container img");
const saveBtn = document.querySelector(".close-button");
const avtImage = document.querySelector("#main-avatar img");
let regExp = /[0-9a-zA-Z\^\&\'\@\{\}\[\]\,\$\=\!\-\#\(\)\.\%\+\~\_ ]+$/;

function defaultBtnActive() {
    defaultBtn.click();
}

defaultBtn.addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function () {
            const result = reader.result;
            img.src = result;
        }
        
        cancelBtn.addEventListener("click", function () {
            img.src = "";
        })
        
        reader.readAsDataURL(file);
    }
    if (this.value) {
        let valueStore = this.value.match(regExp);
    }
});

function saveImg() {
    avtImage.src = img.src;
    const modal = document.querySelector('.avt-modal');
    // Close the modal
    if (modal == null) return;
    modal.classList.remove('active');
    overlay.classList.remove('active');
}