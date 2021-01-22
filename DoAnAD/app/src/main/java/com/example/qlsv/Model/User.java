package com.example.qlsv.Model;

public class User {
    int id;
    String user;
    String name;
    String place;
    String phone;

    public User(int id, String user, String name, String place, String phone) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.place = place;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
