package com.example.villafilomena.Frontdesk;

public class Frontdesk_userDetailsModel {
    String email, fullname, contact, address, token;

    public Frontdesk_userDetailsModel(String email, String fullname, String contact, String address, String token) {
        this.email = email;
        this.fullname = fullname;
        this.contact = contact;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
