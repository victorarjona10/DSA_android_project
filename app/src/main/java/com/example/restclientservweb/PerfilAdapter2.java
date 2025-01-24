package com.example.restclientservweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilAdapter2 extends ArrayAdapter<Products> {
    private ApiService apiService;
    private String username;
    private String idUser;
    private boolean isEquiped = false;
    private User user;

    public PerfilAdapter2(Context context, List<Products> products, boolean isEquiped, User userPerfil) {
        super(context, 0, products);
        this.username = userPerfil.getUsername();
        this.idUser = userPerfil.getId();
        user = userPerfil;


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(ApiService.class); // Inicializar apiService
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Products product = getItem(position);

        TextView textViewProductName = convertView.findViewById(R.id.textViewProductName);
        textViewProductName.setText(product.getName());
        ImageView imageViewProduct = convertView.findViewById(R.id.imageViewStore);
        if (imageViewProduct != null) {
            if (product.getName().equals("avatar2"))
                imageViewProduct.setImageResource(R.drawable.avatar2);
            else if (product.getName().equals("avatar1"))
                imageViewProduct.setImageResource(R.drawable.avatar1);
            else if (product.getName().equals("avatar3"))
                imageViewProduct.setImageResource(R.drawable.avatar3);
            else if (product.getName().equals("avatar4"))
                imageViewProduct.setImageResource(R.drawable.avatar4);
            else
                imageViewProduct.setImageResource(R.drawable.mejora);
        }

        Button buttonAction = convertView.findViewById(R.id.buttonAction);
        buttonAction.setText("Equipar");
        buttonAction.setOnClickListener(v -> {

                isEquiped = true;
                //buttonAction.setText("Equipado");

                Toast.makeText(getContext(), product.getName() + " equipado", Toast.LENGTH_SHORT).show();

            if (product.getName().startsWith("avatar")){
                user.setActSkinUser(product.getName());
            }
            else{
                user.setActSkinWeapon(product.getName());
            }


            ((PerfilActivity2) getContext()).actualizarDatosPerfil(user.getId(), user.getUsername(), user.getEmail(), user.getActSkinUser(), user.getActSkinWeapon());





        });

        return convertView;
    }


}