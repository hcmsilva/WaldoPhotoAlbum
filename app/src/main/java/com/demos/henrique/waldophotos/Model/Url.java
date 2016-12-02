package com.demos.henrique.waldophotos.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by henrique on 29-11-2016.
 */

public class Url {

    @SerializedName("size_code")
    @Expose
    public String sizeCode;
    @SerializedName("url")
    @Expose
    public String url;

    /**
     *
     * @return
     * The sizeCode
     */
    public String getSizeCode() {
        return sizeCode;
    }

    /**
     *
     * @param sizeCode
     * The size_code
     */
    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}