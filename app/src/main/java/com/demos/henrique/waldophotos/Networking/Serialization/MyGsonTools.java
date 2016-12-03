package com.demos.henrique.waldophotos.Networking.Serialization;


import com.demos.henrique.waldophotos.Model.Album;
import com.demos.henrique.waldophotos.Model.PhotoRecord;
import com.demos.henrique.waldophotos.Model.Url;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.List;


public class MyGsonTools<T> {

    public Album getModelFromJSON(String json)
    {

        try {

            JSONObject albumData = new JSONObject(json);


            String id = albumData.getString("id");
            String name = albumData.getString("name");
            JSONObject photosJson = albumData.getJSONObject("photos");

            JSONArray recordsJsonArray = photosJson.getJSONArray("records");
            Type entityTypePhotoRecord = new TypeToken<List<PhotoRecord>>(){}.getType();
            List<PhotoRecord> photoRecordListEntity = new Gson().fromJson(recordsJsonArray.toString(), entityTypePhotoRecord);// recordsJsonArray, PhotoRecord.class);

            Type entityTypeUrlList = new TypeToken<List<Url>>(){}.getType();
            for (int i = 0; i< recordsJsonArray.length(); i++)
            {
                JSONObject record = recordsJsonArray.getJSONObject(i);
                List<Url> urlListEntity = new Gson().fromJson(record.getJSONArray("urls").toString(), entityTypeUrlList);// recordsJsonArray, PhotoRecord.class);

                photoRecordListEntity.get(i).setUrls(urlListEntity);

            }


            Album alb = new Album(id, name, photoRecordListEntity);
            alb.getId();

            //Album entity = new Gson().fromJson(json.toString(), entityType);
            //AlbumDTO entity = new Gson().fromJson(json, AlbumDTO.class);

        return alb;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Album getModelFromJSON2(String json)
    {
        Type entityType = new TypeToken<Album>(){}.getType();


        try {
            JSONArray jsonArray = (new JSONObject(json)).getJSONArray("records");

            Album entity = new Gson().fromJson(json.toString(), entityType);

            return entity;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
