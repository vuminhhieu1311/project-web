module.service('UserAvatar', function(Auth) {
    this.url = function(user, realm) {
        return authUrl + '/realms/' + realm.realm + '/avatar-provider/admin/' + user.id + "?access_token=" + Auth.authz.token + "&" + + new Date().getTime();
    }
    
    this.transformImage = function (url, options) {
        return url.replace('/image/upload/', `/image/upload/${options}/`);
    }
});

module.controller('UserAvatarCtrl', function($scope, $http, Notifications, UserAvatar) {
    $http.get(UserAvatar.url($scope.user, $scope.realm))
        .then(function (response) {
            if (response.data.resources[0]) {
                $scope.avatarUrl = UserAvatar.transformImage(response.data.resources[0].secure_url, 'w_200');
            } else {
                $scope.avatarUrl = '';
            }
        }, function (error) {
            console.log(error);
            Notifications.error("Could not get the avatar");
        });
    
    $scope.uploadAvatar = function(files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("image", files[0]);
    
        $http.post(UserAvatar.url($scope.user, $scope.realm), fd, {
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
        }).then(function(response) {
            Notifications.success("Your changes have been saved to the user.");
            $scope.avatarUrl = UserAvatar.transformImage(response.data.secure_url, 'w_200');
        }, function(error) {
            console.error(error);
            Notifications.error("Could not save the avatar");
        });
    }
});
