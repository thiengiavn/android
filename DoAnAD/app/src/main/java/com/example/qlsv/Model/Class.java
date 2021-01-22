package com.example.qlsv.Model;

public class Class {
    int id;
    String malop;
    String tenlop;
    int iduser;

    public Class(int id, String malop, String tenlop, int iduser) {
        this.id = id;
        this.malop = malop;
        this.tenlop = tenlop;
        this.iduser = iduser;
    }
    public Class(String tenlop) {

        this.malop = malop;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
}
