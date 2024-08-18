package com.example.blog_app.services;

import com.example.blog_app.dao.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    ImageDAO imageDAO;

    public void deleteImage(Long id){
        imageDAO.deleteById(id);
    }

}
