package com.example.restclientservweb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingActivity extends AppCompatActivity {
    private ApiService apiService;

    ListView listViewRanking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ranking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        listViewRanking = findViewById(R.id.listViewRanking);
        getRanking();
        // Inicializar el adaptador para la lista de productos comprados
        RankingAdapter rankingAdapter = new RankingAdapter(this, new ArrayList<>());
        listViewRanking.setAdapter(rankingAdapter);
        listViewRanking.setVisibility(View.VISIBLE);



        Button buttonBackToMenu = findViewById(R.id.btnBackMenu);
        buttonBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(RankingActivity.this, MenuActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username"));
            intent.putExtra("idUser", getIntent().getStringExtra("idUser"));
            startActivity(intent);
        });
    }

    private void getRanking() {
        Call<List<User>> call = apiService.getRanking();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> ranking = response.body();
                    // ArrayAdapter<Products> adapter = new ArrayAdapter<>(StoreActivity.this, android.R.layout.simple_list_item_1, products);
                    RankingAdapter adapter = new RankingAdapter(RankingActivity.this, ranking);
                    listViewRanking.setAdapter(adapter);
                    listViewRanking.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(RankingActivity.this, "Error al cargar Ranking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(RankingActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }
}