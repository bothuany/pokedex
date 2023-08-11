package com.obss.pokedexapi.image.controller;


import com.obss.pokedexapi.image.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/images")
@RestController
public class ImageController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image,
                              @RequestParam("publicId") String publicId) {
        try {
            byte[] imageBytes = image.getBytes();
            return cloudinaryService.uploadImage(imageBytes, publicId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading image.";
        }
    }

    @GetMapping("/{publicId}")
    public String getImageUrl(@PathVariable(name ="publicId") String publicId) {
        try {
            return cloudinaryService.getImageUrl(publicId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting image URL.";
        }
    }
}
