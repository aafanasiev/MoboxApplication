package com.aafanasiev.moboxapplication.model.ServerAPI;

import com.aafanasiev.moboxapplication.model.BaseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerAPI {

    @GET("articles?source=bbc-sport&sortBy=top&apiKey=16ba21c0f767423ab017eeb1bf778e9a")
    Call<BaseModel> getModel();


}
