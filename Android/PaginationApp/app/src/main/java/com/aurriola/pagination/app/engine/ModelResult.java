package com.aurriola.pagination.app.engine;

/**
 * Created by Alexander Urriola.
 */
public class ModelResult {
    private String tilte;
    private String description;
    private String url_img;

    public ModelResult(String tilte, String description, String url_img) {
        this.tilte = tilte;
        this.description = description;
        this.url_img = url_img;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    @Override
    public String toString() {
        return "tilte='" + tilte  + ", description='" + description +'\'';
    }
}
