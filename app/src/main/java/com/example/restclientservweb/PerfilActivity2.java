package com.example.restclientservweb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity2 extends AppCompatActivity {
    private ApiService apiService;
    private User userPerfil;
    private String username;
    private String idUser;
    TextView textViewUsername;
    TextView textViewEmail;
    TextView textViewDinero;
    TextView textViewPuntos;
    TextView textViewAvatar;
    TextView textViewMejora;
    ListView listViewInventario;
    ImageButton btnFotoPerfil;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewDinero = findViewById(R.id.textViewCoins);
        textViewPuntos = findViewById(R.id.textViewMaxPoints);
        textViewAvatar = findViewById(R.id.textViewAvatar);
        textViewMejora = findViewById(R.id.textViewMejora);

        username = getIntent().getStringExtra("username");
        idUser = getIntent().getStringExtra("idUser");

        if (idUser == null || username == null) {
            sharedPreferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username", null);
            idUser = sharedPreferences.getString("idUser", null);
        }






        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        try {
            getDatosPerfil(idUser);

        }
        catch (Exception e) {
            Toast.makeText(PerfilActivity2.this, "Error al obtener los datos del perfil", Toast.LENGTH_SHORT).show();
        }

        // Inicializar el adaptador para la lista de productos comprados
        RankingAdapter rankingAdapter = new RankingAdapter(this, new ArrayList<>());
        listViewInventario = findViewById(R.id.ListViewInventario);
        getProducts(idUser);
        listViewInventario.setAdapter(rankingAdapter);
        listViewInventario.setVisibility(View.VISIBLE);



        Button btnActualizarUsername = findViewById(R.id.btnActualizarUsername);
        btnActualizarUsername.setEnabled(false);

        Button btnActualizarEmail = findViewById(R.id.btnActualizarEmail);
        btnActualizarEmail.setEnabled(false);

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilActivity2.this, MenuActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
        });


        textViewUsername.setOnClickListener(v -> {
            EditText editTextUsername = new EditText(this);
            editTextUsername.setText(textViewUsername.getText());
            editTextUsername.setLayoutParams(textViewUsername.getLayoutParams());

            // Replace the TextView with the EditText
            ViewGroup parent = (ViewGroup) textViewUsername.getParent();
            int index = parent.indexOfChild(textViewUsername);
            parent.removeView(textViewUsername);
            parent.addView(editTextUsername, index);

            btnActualizarUsername.setEnabled(true);
            btnActualizarUsername.setOnClickListener(v1 -> {
                textViewUsername.setText(editTextUsername.getText());
                parent.removeView(editTextUsername);
                parent.addView(textViewUsername, index);
                btnActualizarUsername.setEnabled(false);
                actualizarDatosPerfil(idUser, textViewUsername.getText().toString(), textViewEmail.getText().toString(),textViewAvatar.getText().toString(),textViewMejora.getText().toString());
            });

        });




        textViewEmail.setOnClickListener(v -> {
            EditText editTextEmail = new EditText(this);
            editTextEmail.setText(textViewEmail.getText());
            editTextEmail.setLayoutParams(textViewEmail.getLayoutParams());

            // Replace the TextView with the EditText
            ViewGroup parent = (ViewGroup) textViewEmail.getParent();
            int index = parent.indexOfChild(textViewEmail);
            parent.removeView(textViewEmail);
            parent.addView(editTextEmail, index);

            btnActualizarEmail.setEnabled(true);
            btnActualizarEmail.setOnClickListener(v1 -> {
                textViewEmail.setText(editTextEmail.getText());
                parent.removeView(editTextEmail);
                parent.addView(textViewEmail, index);
                btnActualizarEmail.setEnabled(false);
                actualizarDatosPerfil(idUser, textViewUsername.getText().toString(), textViewEmail.getText().toString(),textViewAvatar.toString(),textViewMejora.toString());
            });

        });


    }
    public void getDatosPerfil(String id) {
        Call<User> call = apiService.getUserProfileById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userPerfil = response.body();
                    textViewUsername.setText(userPerfil.getUsername());
                    textViewEmail.setText(userPerfil.getEmail());
                    textViewDinero.setText(String.valueOf(userPerfil.getDinero()));
                    textViewPuntos.setText(String.valueOf(userPerfil.getPuntos()));
                    textViewAvatar.setText(userPerfil.getActSkinUser());
                    textViewMejora.setText(userPerfil.getActSkinWeapon());

                    btnFotoPerfil = findViewById(R.id.btnFotoPerfil);
                    if (textViewAvatar.getText().equals("avatar2"))
                        btnFotoPerfil.setBackgroundResource(R.drawable.avatar2);
                    else if (textViewAvatar.getText().equals("avatar1"))
                        btnFotoPerfil.setBackgroundResource(R.drawable.avatar1);
                    else if (textViewAvatar.getText().equals("avatar3"))
                        btnFotoPerfil.setBackgroundResource(R.drawable.avatar3);
                    else if (textViewAvatar.getText().equals("avatar4"))
                        btnFotoPerfil.setBackgroundResource(R.drawable.avatar4);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(PerfilActivity2.this, "Error al obtener los datos del perfil", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //User( String id, String email, String username, String password, Integer puntos, String actSkinUser, String actSkinWeapon)
    public void actualizarDatosPerfil(String id, String username, String email, String actSkinUser, String actSkinWeapon) {
        Call<User> call = apiService.updateUser(new User(id,email, username, actSkinUser, actSkinWeapon));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    getDatosPerfil(id);
                    Toast.makeText(PerfilActivity2.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PerfilActivity2.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(PerfilActivity2.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getProducts(String idUser) {
        Call<List<Products>> call = apiService.getProductsOfUser(idUser);

        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> products = response.body();
                    // ArrayAdapter<Products> adapter = new ArrayAdapter<>(StoreActivity.this, android.R.layout.simple_list_item_1, products);
                    PerfilAdapter2 adapter = new PerfilAdapter2(PerfilActivity2.this, products,false, userPerfil);
                    listViewInventario.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(PerfilActivity2.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(PerfilActivity2.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }
}