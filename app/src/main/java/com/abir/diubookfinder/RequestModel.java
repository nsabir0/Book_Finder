package com.abir.diubookfinder;

public class RequestModel {

    String id;
    String bookId;
    String sellerId;
    String customerId;
    String address;
    String phoneNumber;
    int status;

    public RequestModel(String id, String bookId, String sellerId, String customerId, String address, String phoneNumber, int status) {
        this.id = id;
        this.bookId = bookId;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public RequestModel() {

    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getStatus() {
        return status;
    }
}
