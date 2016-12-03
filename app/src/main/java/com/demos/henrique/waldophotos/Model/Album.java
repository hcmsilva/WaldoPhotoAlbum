package com.demos.henrique.waldophotos.Model.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Album {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("photos")
    @Expose
    public List<PhotoRecord> photos;

    public Album(String id, String name, List<PhotoRecord> photoRecordList) {

        this.id = id;
        this.name = name;
        this.photos = photoRecordList;
    }


    public Album(){};

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The photos
     */
    public List<PhotoRecord> getPhotos() {


        return photos;
    }


    /**
     *
     * @param photos
     * The photos
     */
    public void setPhotos(List<PhotoRecord> photos) {
        this.photos = photos;
    }

}