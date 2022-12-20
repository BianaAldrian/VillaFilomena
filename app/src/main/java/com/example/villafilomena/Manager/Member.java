package com.example.villafilomena.Manager;

public class Member {

    private String Name, MyUri;

    private Member(){}

    public Member(String name, String MyUri){
        if (name.trim().equals("")){
            name = "not available";
        }
        Name = name;
        this.MyUri = MyUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMyUri() {
        return MyUri;
    }

    public void setMyUri(String myUri) {
        MyUri = myUri;
    }
}
