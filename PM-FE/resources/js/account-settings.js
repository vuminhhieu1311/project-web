/*------------------TAB MENU---------------------*/
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

/*-----------------SEARCH POPUP-------------*/

const openModalButtons = document.querySelectorAll('[data-modal-target]')
const closeModalButtons = document.querySelectorAll('[data-close-button]')
const overlay = document.getElementById('overlay')

openModalButtons.forEach(button => {
  button.addEventListener('click', () => {
    const modal = document.querySelector(button.dataset.modalTarget)
    openModal(modal)
  })
})

overlay.addEventListener('click', () => {
  const modals = document.querySelectorAll('.modal.active');
  modals.forEach(modal => {
    closeModal(modal);
  })
  const avtModals = document.querySelectorAll('.avt-modal.active');
  avtModals.forEach(modal => {
    closeModal(modal);
  });
});

closeModalButtons.forEach(button => {
  button.addEventListener('click', () => {
    const modal = button.closest('.modal')
    closeModal(modal)
    const avtModal = button.closest('.avt-modal')
    closeModal(avtModal)
  })
})

function openModal(modal) {
  if (modal == null) return
  modal.classList.add('active')
  overlay.classList.add('active')
}

function closeModal(modal) {
  if (modal == null) return
  modal.classList.remove('active')
  overlay.classList.remove('active')
}

/*-------------UPLOAD IMG POPUP-------------------------*/

const wrapper = document.querySelector(".wrapper");
const defaultBtn = document.querySelector("#default-btn");
const customBtn = document.querySelector("#custom-btn");
const cancelBtn = document.querySelector(".cancel-btn");
const img = document.querySelector(".container img");
const saveBtn = document.querySelector(".close-button");
const avtImage = document.querySelector("#main-avatar img");
const profileImgs = document.querySelectorAll(".profile img");
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
    profileImgs.forEach(image => {
        image.src = img.src;
    })
    const modal = document.querySelector('.avt-modal');
    // Close the modal
    if (modal == null) return;
    modal.classList.remove('active');
    overlay.classList.remove('active');
}

/*----------------DROPDOWN MENU----------------*/

const toggleMenu = document.querySelector('.dropdown-menu');
var dropdownBtn = document.querySelector('.dropbtn');

window.addEventListener("click", function () {
    if (toggleMenu.classList.contains('active')) {
        toggleMenu.classList.remove('active');
    }
}, false);
dropdownBtn.addEventListener("click", function (ev) {
    toggleMenu.classList.toggle('active');
    ev.stopPropagation(); 
}, false);