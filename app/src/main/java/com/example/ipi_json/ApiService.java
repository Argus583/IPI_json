package com.example.ipi_json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("albums")
    Call<List<Album>> getAlbums();
}
