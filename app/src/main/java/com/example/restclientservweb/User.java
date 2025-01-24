package com.example.restclientservweb;

import java.util.LinkedList;
import java.util.List;

public class User {

    String id;
    String email;
    String username;
    String password;
    Integer dinero = 20;
    Integer puntos = 0;
    String actSkinUser = "default";
    String actSkinWeapon = "default";



    public User() {
    }
    public User(String email, String username, String password, Integer puntos, String actSkinUser, String actSkinWeapon) {
        this();
        this.email = email;
        this.username = username;
        this.password = password;
        if (id != null) this.setId(id);
        this.puntos = puntos;
        this.actSkinUser = actSkinUser;
        this.actSkinWeapon = actSkinWeapon;
    }


    public User( String id, String email, String username, String password, Integer puntos,Integer dinero, String actSkinUser, String actSkinWeapon) {
        this();
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        if (id != null) this.setId(id);
        this.puntos = puntos;
        this.dinero = dinero;
        this.actSkinUser = actSkinUser;
        this.actSkinWeapon = actSkinWeapon;
    }

    public User( String id, String email, String username, String actSkinUser, String actSkinWeapon) {
        this();
        this.id = id;
        this.email = email;
        this.username = username;
        if (id != null) this.setId(id);
        this.actSkinUser = actSkinUser;
        this.actSkinWeapon = actSkinWeapon;
    }



    public User(String id, Integer puntos, Integer dinero) {
        this();
        this.id = id;
        this.puntos = puntos;
        this.dinero = dinero;
    }




    public User(String email, String username, String password) {
        this();
        this.email = email;
        this.username = username;
        this.password = password;
        if (id != null) this.setId(id);
    }


    public void setId(String id) {
        this.id=id;
    }

    public String getId(){
        return id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getDinero(){
        return this.dinero;
    }
    public String getEmail(){
        return email;
    }
    public void setDinero(Integer dinero){
        this.dinero = dinero;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public void setActSkinUser(String actSkinUser) {
        this.actSkinUser = actSkinUser;
    }

    public void setActSkinWeapon(String actSkinWeapon) {
        this.actSkinWeapon = actSkinWeapon;
    }

    public String getActSkinUser() {
        return actSkinUser;
    }

    public String getActSkinWeapon() {
        return actSkinWeapon;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
    public void setUser(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.dinero = user.getDinero();
        //this.stats = user.getStats();
    }

//    public Stats getStats() {
//        return stats;
//    }
//
//    public void setStats(Stats stats) {
//        this.stats = stats;
//    }
    //
//    public User(String username, int dinero) {
//        this.username = username;
//        this.dinero = dinero;
//        this.productos = new LinkedList<>();
//    }
//
}