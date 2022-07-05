package com.example.bottomtab;

public class Contact {
    private Long photoid;
    private String phonenum;
    private String name;

    public Long getPhotoid() {
        return photoid;
    }

    public void setPhotoid(Long photoid) {
        this.photoid = photoid;
    }

    public Contact(Long photoid, String phonenum, String name) {this.photoid = photoid;
        this.phonenum = phonenum;
        this.name = name;
    }

    public Contact() {

    }


    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
