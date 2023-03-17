package com.example.villafilomena.Guest.home_booking;

public class RoomInfos_model {
    private String id, imageUrl, name, room_capacity, room_rate;
    private RoomInfos_model(){}
    public RoomInfos_model(String id, String imageUrl, String name, String room_capacity, String room_rate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.room_capacity = room_capacity;
        this.room_rate = room_rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(String room_capacity) {
        this.room_capacity = room_capacity;
    }

    public String getRoom_rate() {
        return room_rate;
    }

    public void setRoom_rate(String room_rate) {
        this.room_rate = room_rate;
    }
}
