function avatarUrl() {
    var realmName = document.getElementById('realmId').value;
    
    return `${location.origin}/auth/realms/${realmName}/avatar-provider`;
}

function transformImage(url, options) {
    return url.replace('/image/upload/', `/image/upload/${options}/`);
}

function getUserAvatar(cb) {
    fetch(avatarUrl())
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (data.resources[0]) {
                var source = transformImage(data.resources[0].secure_url, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35');
                document.getElementById('header-avatar').src = transformImage(data.resources[0].secure_url, 'w_40,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35');
                document.getElementById('dropdown-avatar').src = transformImage(data.resources[0].secure_url, 'w_50,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35');
        
                if (document.getElementById('user-avatar-form')) {
                    document.getElementById('user-avatar-form').src = source;
                }
                if (document.getElementById('user-avatar')) {
                    document.getElementById('user-avatar').src = source;
                }
            }
        })
        .catch(function (err) {
            console.log(err);
        });
}

function uploadUserAvatar() {
    var avatarForm = document.getElementById('avt-modal');
    var wrapper = document.querySelector(".wrapper");
    
    if (avatarForm && wrapper) {
        avatarForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var stateChecker = document.querySelector('#avt-modal input[name="stateChecker"]').value;
            var files = document.querySelector('#avt-modal input[name="image"]').files;
            var fd = new FormData();
            fd.append('image', files[0]);
            fd.append('stateChecker', stateChecker);
    
            var spinner = document.querySelector('.sk-chase');
            spinner.classList.add('showSpinner');
            wrapper.classList.add('loading');
        
            fetch(`${avatarUrl()}`, {
                method: 'POST',
                body: fd
            })
                .then(function (response) {
                    return response.json();
                })
                .then(function (data) {
                    var source = transformImage(data.secure_url, 'w_200,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35');
                    document.getElementById('header-avatar').src = transformImage(data.secure_url, 'w_40,c_fill,ar_1:1,g_auto,r_max,b_rgb:262c35');
                
                    document.getElementById('user-avatar').src = source;
                    document.getElementById('user-avatar-form').src = source;
    
                    spinner.classList.remove('showSpinner');
                    wrapper.classList.remove('loading');
                    
                    var modal = document.querySelector('.avt-modal');
                    // Close the modal
                    if (modal == null) return;
                    modal.classList.remove('active');
                    overlay.classList.remove('active');
                })
                .catch(function (err) {
                    console.log(err);
                })
        });
    }
}

getUserAvatar();
uploadUserAvatar();
