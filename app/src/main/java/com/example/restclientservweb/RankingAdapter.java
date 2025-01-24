package com.example.restclientservweb;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RankingAdapter extends ArrayAdapter<User> {
    private ApiService apiService;
    private String username;
    private Integer dinero;
    private Integer puntos;


    public RankingAdapter(Context context, List<User> users) {
        super(context, 0, users);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_ranking, parent, false);
        }

        User user = getItem(position);



        TextView textViewUserName = convertView.findViewById(R.id.textViewUserName);
        textViewUserName.setText(user.getUsername());
        TextView textViewDinero= convertView.findViewById(R.id.textViewDinero);
        textViewDinero.setText(user.getDinero().toString());
        TextView textViewPuntos= convertView.findViewById(R.id.textViewPuntos);
        textViewPuntos.setText(user.getPuntos().toString());


        return convertView;
    }
}
