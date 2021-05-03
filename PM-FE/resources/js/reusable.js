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


