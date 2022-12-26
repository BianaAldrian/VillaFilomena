package com.example.villafilomena.Frontdesk;

public class Book_Request_model {
    String booking_id, current_date, user_email, checkIn_date, checkIn_time, checkOut_date, checkOut_time, guest_qty, room_id, cottage_id, total_cost, payment, payment_status, balance, reference_num, booking_status;

    public Book_Request_model(String booking_id, String current_date, String user_email, String checkIn_date, String checkIn_time, String checkOut_date, String checkOut_time, String guest_qty, String room_id, String cottage_id, String total_cost, String payment, String payment_status, String balance, String reference_num, String booking_status) {
        this.booking_id = booking_id;
        this.current_date = current_date;
        this.user_email = user_email;
        this.checkIn_date = checkIn_date;
        this.checkIn_time = checkIn_time;
        this.checkOut_date = checkOut_date;
        this.checkOut_time = checkOut_time;
        this.guest_qty = guest_qty;
        this.room_id = room_id;
        this.cottage_id = cottage_id;
        this.total_cost = total_cost;
        this.payment = payment;
        this.payment_status = payment_status;
        this.balance = balance;
        this.reference_num = reference_num;
        this.booking_status = booking_status;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getCheckIn_date() {
        return checkIn_date;
    }

    public void setCheckIn_date(String checkIn_date) {
        this.checkIn_date = checkIn_date;
    }

    public String getCheckIn_time() {
        return checkIn_time;
    }

    public void setCheckIn_time(String checkIn_time) {
        this.checkIn_time = checkIn_time;
    }

    public String getCheckOut_date() {
        return checkOut_date;
    }

    public void setCheckOut_date(String checkOut_date) {
        this.checkOut_date = checkOut_date;
    }

    public String getCheckOut_time() {
        return checkOut_time;
    }

    public void setCheckOut_time(String checkOut_time) {
        this.checkOut_time = checkOut_time;
    }

    public String getGuest_qty() {
        return guest_qty;
    }

    public void setGuest_qty(String guest_qty) {
        this.guest_qty = guest_qty;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getCottage_id() {
        return cottage_id;
    }

    public void setCottage_id(String cottage_id) {
        this.cottage_id = cottage_id;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getReference_num() {
        return reference_num;
    }

    public void setReference_num(String reference_num) {
        this.reference_num = reference_num;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

}
