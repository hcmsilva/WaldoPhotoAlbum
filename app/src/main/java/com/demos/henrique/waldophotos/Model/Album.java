package com.demos.henrique.waldophotos.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Album {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;
    @SerializedName("photos")
    @Expose
    public List<PhotoRecord> photos;

    @SerializedName("total")
    @Expose
    public int total;



    public Album(String id, String name, List<PhotoRecord> photoRecordList) {

        this.id = id;
        this.name = name;
        this.photos = photoRecordList;
    }

    public Album(String id, String name, List<PhotoRecord> photoRecordList, String albumUpdateDate, int totalPhotoRecords) {
        this(id, name, photoRecordList);
        this.updated_at = albumUpdateDate;
        this.total = totalPhotoRecords;

    }


    public Album(){
        this.id = "";
        this.name = "";
        this.photos = new ArrayList<>();
        this.total = 0;
        this.updated_at = "";
    };

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


    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}