document.body.scrollTop = 0;
document.documentElement.scrollTop = 0;

/*----------------MODAL----------------*/

var openModalButtons = document.querySelectorAll('[data-modal-target]');
var closeModalButtons = document.querySelectorAll('[data-close-button]');
var overlay = document.getElementById('overlay');

if (openModalButtons && overlay && closeModalButtons) {
    openModalButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            var modal = document.querySelector(button.dataset.modalTarget);
            openModal(modal);
        })
    });
    
    overlay.addEventListener('click', function () {
        var modals = document.querySelectorAll('.modal.active');
        modals.forEach(modal => {
            closeModal(modal);
        });
        var avtModals = document.querySelectorAll('.avt-modal.active');
        avtModals.forEach(modal => {
            closeModal(modal);
        });
    });
    
    closeModalButtons.forEach(function (button) {
        button.addEventListener('click', () => {
            var modal = button.closest('.modal');
            closeModal(modal);
            var avtModal = button.closest('.avt-modal');
            closeModal(avtModal);
        });
    });
}

function openModal(modal) {
    if (modal == null) return;
    modal.classList.add('active');
    overlay.classList.add('active');
}

function closeModal(modal) {
    if (modal == null) return;
    modal.classList.remove('active');
    overlay.classList.remove('active');
}


/*----------------DROPDOWN MENU----------------*/

var toggleMenu = document.querySelector('.dropdown-menu');
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
