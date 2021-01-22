package com.example.qlsv.Model;


public class Students  {
    int id;
    String tenSV;
    String date;
    int idclass;
    int iduser;
    String tenlop;
    String sdt;
    String email;
    String place;
    byte[] images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdclass() {
        return idclass;
    }

    public void setIdclass(int idclass) {
        this.idclass = idclass;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public Students() {
    }

    public Students(int id, String tenSV, String date, int idclass, int iduser, String tenlop, String sdt, String email, String place, byte[] images) {
        this.id = id;
        this.tenSV = tenSV;
        this.date = date;
        this.idclass = idclass;
        this.iduser = iduser;
        this.tenlop = tenlop;
        this.sdt = sdt;
        this.email = email;
        this.place = place;
        this.images = images;
    }
}
