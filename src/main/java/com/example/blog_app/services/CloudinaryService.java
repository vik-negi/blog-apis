package com.example.blog_app.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.blog_app.bens.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;


    private final String folderName = "blog-app"; // Specify your desired folder name here

    public Image uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", folderName, // Specify the folder name
                "resource_type", "auto"
        ));

        return new Image(uploadResult.get("url").toString(), file.getOriginalFilename(), uploadResult.get("public_id").toString());
    }

    public void deleteImage(String publicId) throws IOException {
        try{
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }catch (Exception e){
            throw new IOException("Error deleting image from Cloudinary: " + e.getMessage());
        }
    }
}
