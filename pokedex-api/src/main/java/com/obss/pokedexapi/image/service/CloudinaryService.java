package com.obss.pokedexapi.image.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CloudinaryService {


    private final Cloudinary cloudinary;

    public CloudinaryService() {
        //trimmed for security reasons
        String CLOUD_NAME = "dst";
        String API_KEY = "5886764";
        String API_SECRET = "Duxeg3zU6";
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        ));
    }

    public String uploadImage(byte[] imageBytes, String publicId) {
        try {
            Map uploadResult = cloudinary.uploader().upload(imageBytes,
                    ObjectUtils.asMap("public_id", publicId));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getImageUrl(String publicId) {
        try {
            return cloudinary.url().generate(publicId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting image URL.";
        }
    }

    public String updateImage(String publicId, String newPublicId) {
        try {
            Map uploadResult = cloudinary.uploader().rename(publicId, newPublicId, ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating image.";
        }
    }

    public String deleteImage(String publicId) {
        try {
            Map uploadResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return uploadResult.get("result").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting image.";
        }
    }

}