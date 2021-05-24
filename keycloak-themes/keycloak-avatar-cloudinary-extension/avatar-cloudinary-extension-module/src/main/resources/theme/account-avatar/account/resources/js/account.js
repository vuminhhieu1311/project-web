function avatarUrl() {
    var realmName = document.getElementById('realmId').value;
    
    return `${location.origin}/auth/realms/${realmName}/avatar-provider`;
}

function transformImage(url, options) {
    return url.replace('/image/upload/', `/image/upload/${options}/`);
}

function getUserAvatar() {
    fetch(avatarUrl())
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            document.getElementById('user-avatar').src = transformImage(data.resources[0].url, 'w_200');
        })
        .catch(function (err) {
            console.log(err);
        });
}

function uploadUserAvatar() {
    var avatarForm = document.getElementById('avatar-form');
    avatarForm.addEventListener('submit', function (e) {
        e.preventDefault();
        var stateChecker = document.querySelector('#avatar-form input[name="stateChecker"]').value;
        var files = document.querySelector('#avatar-form input[name="image"]').files;
        var fd = new FormData();
        fd.append('image', files[0]);
        fd.append('stateChecker', stateChecker);
        
        fetch(`${avatarUrl()}`, {
            method: 'POST',
            body: fd
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                document.getElementById('user-avatar').src = transformImage(data.url, 'w_200');
            })
            .catch(function (err) {
                console.log(err);
            })
    });
}

window.onload = function () {
    getUserAvatar();
    uploadUserAvatar();
}
