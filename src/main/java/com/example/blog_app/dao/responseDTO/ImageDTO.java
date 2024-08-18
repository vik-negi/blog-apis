package com.example.blog_app.dao.responseDTO;

import com.example.blog_app.bens.Image;

public class ImageDTO {
    private String url;
    private String name;

    public ImageDTO(Image image) {
        this.url = image.getUrl();
        this.name = image.getName();
    }
}
