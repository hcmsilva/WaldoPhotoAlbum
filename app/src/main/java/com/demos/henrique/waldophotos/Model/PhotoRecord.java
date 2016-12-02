package com.demos.henrique.waldophotos.Model;

/**
 * Created by henrique on 29-11-2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotoRecord {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("urls")
    @Expose
    public List<Url> urls = new ArrayList<Url>();

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
     * The urls
     */
    public List<Url> getUrls() {
        return urls;
    }

    /**
     *
     * @param urls
     * The urls
     */
    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }


    public Url getLargeImage()
    {
        return urls.get(3);
    }

}