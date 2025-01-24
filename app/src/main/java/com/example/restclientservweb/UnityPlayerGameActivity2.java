package com.example.restclientservweb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerGameActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnityPlayerGameActivity2 extends com.unity3d.player.UnityPlayerGameActivity {

    private ApiService apiService;


//    public void SalirSinGuardar() {
//
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override public void run() {
//                Intent intent = new Intent(UnityPlayerGameActivity2.this, MenuActivity.class);
//                startActivity(intent);
//            }
//        }, 3000);
//    }
    public void RequestUserData() {

        String idUser = getIntent().getStringExtra("idUser");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        Call<List<Products>> call = apiService.getProductsOfUser(idUser);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> listaProductos = response.body();
                    Log.d("UnityCommunication", "Productos recibidos: " + listaProductos);
                    String productos = "";
                    for (Products product : listaProductos) {
                        productos = productos + product.getName() + ",";
                    }
                    if (productos.length() > 0) {
                        productos = productos.substring(0, productos.length() - 1);
                    }

                    UnityPlayer.UnitySendMessage("InfoUser", "ReceiveUserData", productos);
                } else {
                    Log.e("UnityCommunication", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Log.e("UnityCommunication", "Error en la solicitud: " + t.getMessage());
            }
        });

    }

    public void RequestUserAvatar()
    {
        String idUser = getIntent().getStringExtra("idUser");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") //http://147.83.7.203/dsaApp/
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getUserProfileById(idUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Log.d("UnityCommunication", "Usuario recibido: " + user);
                    UnityPlayer.UnitySendMessage("InfoUser", "ReceiveUserAvatar", user.getActSkinUser());
                } else {
                    Log.e("UnityCommunication", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("UnityCommunication", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

    public void receiveFromUnity(String str) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador    http://10.0.2.2:8080/dsaApp/   http://147.83.7.203:80/dsaApp/
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);


        String[] split = str.split(",");
        int dinero = Integer.parseInt(split[0]);
        int puntos = Integer.parseInt(split[1]);

        String username = getIntent().getStringExtra("username");
        String idUser = getIntent().getStringExtra("idUser");
        SendDatatoServer(idUser, dinero, puntos);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override public void run() {
        Intent intent = new Intent(UnityPlayerGameActivity2.this, MenuActivity.class);
       // intent.putExtra("dinero", dinero);
        //intent.putExtra("puntos", puntos);
        startActivity(intent);
        }
        }, 3000); // se espera 3 segundos antes de volver a la actividad principal
    }

 private  void SendDatatoServer(String id, int dinero, int puntos)
     {
         User userPruebaUnity = new User(id, puntos, dinero);
         Call<User> call = apiService.registerPartida(id, String.valueOf(puntos), String.valueOf(dinero));

         call.enqueue(new Callback<User>() {
             @Override
             public void onResponse(Call<User> call, Response<User> response) {
                 if (response.isSuccessful()) {
                    // Toast.makeText(UnityPlayerGameActivity2.this, "is succesful", Toast.LENGTH_SHORT).show();

                 } else {

               //      Toast.makeText(UnityPlayerGameActivity2.this, "else +" + response.body(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<User> call, Throwable t) {
                // Toast.makeText(UnityPlayerGameActivity2.this, "on faiulure", Toast.LENGTH_SHORT).show();
             }
         });
     }



}


