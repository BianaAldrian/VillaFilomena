package com.example.villafilomena.Manager;

public class Frontdesk_Clerk_model {
    String clerk_id, clerk_name, clerk_username, clerk_password, clerk_token;

    public Frontdesk_Clerk_model(String clerk_id, String clerk_name, String clerk_username, String clerk_password, String clerk_token) {
        this.clerk_id = clerk_id;
        this.clerk_name = clerk_name;
        this.clerk_username = clerk_username;
        this.clerk_password = clerk_password;
        this.clerk_token = clerk_token;
    }

    public String getClerk_id() {
        return clerk_id;
    }

    public void setClerk_id(String clerk_id) {
        this.clerk_id = clerk_id;
    }

    public String getClerk_name() {
        return clerk_name;
    }

    public void setClerk_name(String clerk_name) {
        this.clerk_name = clerk_name;
    }

    public String getClerk_username() {
        return clerk_username;
    }

    public void setClerk_username(String clerk_username) {
        this.clerk_username = clerk_username;
    }

    public String getClerk_password() {
        return clerk_password;
    }

    public void setClerk_password(String clerk_password) {
        this.clerk_password = clerk_password;
    }

    public String getClerk_token() {
        return clerk_token;
    }

    public void setClerk_token(String clerk_token) {
        this.clerk_token = clerk_token;
    }
}
