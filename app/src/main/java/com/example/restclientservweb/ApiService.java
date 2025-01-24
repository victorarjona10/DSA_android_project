package com.example.restclientservweb;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/dsaApp/users/register2")
    Call<User> registerUser(@Body User user);

    @POST("/dsaApp/users/login2")
    Call<User> loginUser(@Body User user);

    @GET("/dsaApp/objects/{idUser}/Android")
    Call<List<Products>> getProducts(@Path("idUser") String idUser);

   @GET("/dsaApp/users/{idUser}/dinero")
    Call<Integer> getDinero(@Path("idUser") String idUser);

    @POST("/dsaApp/users/{idUser}/products/{idProduct}")
    Call<Products> addProductToUser(@Path("idUser") String idUser, @Path("idProduct") int idProduct);

    @GET("/dsaApp/users/{id}/products")
    Call<List<Products>> getProductsOfUser(@Path("id") String idUser);


    @PUT("/dsaApp/users/{id}/{puntos}/{dinero}/updatePartida")
    Call <User> registerPartida(@Path("id") String idUser, @Path("puntos") String puntos, @Path("dinero") String dinero);

    @GET("/dsaApp/users/ranking")
    Call<List<User>> getRanking();

    @GET("/dsaApp/users/{idUser}/datosPerfil")
    Call<User> getUserProfileById(@Path("idUser") String idUser);

    @PUT("/dsaApp/users/update2")
    Call<User> updateUser(@Body User user);
}
