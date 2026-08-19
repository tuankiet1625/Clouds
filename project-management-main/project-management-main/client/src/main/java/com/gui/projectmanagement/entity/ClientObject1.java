package com.gui.projectmanagement.entity;

public class ClientObject1 {
    private String id ;
    private String fullname ;
    private String email ;
    private String phonenumber ;
    private byte[] avata ;

    public ClientObject1(String id, String fullname, String email, String phonenumber, byte[] avata) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.avata = avata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public byte[] getAvata() {
        return avata;
    }

    public void setAvata(byte[] avata) {
        this.avata = avata;
    }
}
