package com.example.ipi_json;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создание Retrofit и вызов API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Интерфейс для API
        ApiService ApiService = retrofit.create(ApiService.class);

        // Запрос к API
        Call<List<Album>> call = ApiService.getAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем список альбомов и отображаем
                List<Album> albums = response.body();
                if (albums != null) {
                    // Логирование данных для проверки
                    for (Album album : albums) {
                        Log.d("API_Response", "Title: " + album.getTitle());
                    }

                    // Устанавливаем адаптер
                    albumAdapter = new AlbumAdapter(albums);
                    recyclerView.setAdapter(albumAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
