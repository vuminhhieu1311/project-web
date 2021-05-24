package com.company.keycloak.avatar.storage.cloudinary;

import com.cloudinary.Cloudinary;

public interface CloudinaryCallback<T> {
    
    T doInCloudinary(Cloudinary cloudinary) throws Exception;
}
