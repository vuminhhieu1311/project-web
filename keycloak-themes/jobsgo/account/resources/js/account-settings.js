/*-------------UPLOAD IMG POPUP-------------------------*/

var wrapper = document.querySelector('.wrapper');
var defaultBtn = document.querySelector('#default-btn');
var customBtn = document.querySelector('#custom-btn');
var cancelBtn = document.querySelector('.cancel-btn');
var img = document.querySelector('.container img');
var avtImage = document.querySelector('#main-avatar img');
var regExp = /[0-9a-zA-Z\^\&\'\@\{\}\[\]\,\$\=\!\-\#\(\)\.\%\+\~\_ ]+$/;

if (defaultBtn) {
    customBtn.addEventListener('click', function () {
        defaultBtn.click();
    });
    cancelBtn.addEventListener('click', function () {
        document.querySelector('.avt-modal.active').classList.remove('active');
        document.querySelector('#overlay.active').classList.remove('active');
    });
    
    defaultBtn.addEventListener('change', function () {
        var file = this.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function () {
                var result = reader.result;
                img.src = result;
            }
        
            reader.readAsDataURL(file);
        }
        if (this.value) {
            let valueStore = this.value.match(regExp);
        }
    });
}
