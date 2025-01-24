package com.example.restclientservweb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    private String username;
    private String idUser;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        sharedPreferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);

        username = getIntent().getStringExtra("username");
        idUser = getIntent().getStringExtra("idUser");

        if (idUser == null || username == null) {
            username = sharedPreferences.getString("username", null);
            idUser = sharedPreferences.getString("idUser", null);
        }

        //aqui iran las variables que le pasare al acabar el juego
       // String dinero = getIntent().getStringExtra("dinero");
       // String puntos = getIntent().getStringExtra("puntos");

        Button buttonBackToMain = findViewById(R.id.btnLogOut);
        Button juegoButton = findViewById(R.id.juego);
        ImageButton buttonRanking = findViewById(R.id.btnRanking);

        buttonBackToMain.setOnClickListener(v -> {
            deleteLoginDetails();
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            startActivity(intent);
        });
        juegoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, UnityPlayerGameActivity2.class);
            intent.putExtra("username", username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
        });

        buttonRanking.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RankingActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
        });



    }

    public void click_btnTienda(View v){
        Intent intent = new Intent(MenuActivity.this, StoreActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);

        startActivity(intent);
    }

    public void click_btnPerfil(View v){
        Intent intent = new Intent(MenuActivity.this, PerfilActivity2.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);

        startActivity(intent);
    }


    private void deleteLoginDetails() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}