package com.abir;

import java.io.Serializable;

public class BookModel implements Serializable {
    String id, name, writer, library, address, phone, price, sellerId;

    public BookModel(String id, String name, String writer, String library, String address, String phone, String price, String sellerId) {
        this.id = id;
        this.name = name;
        this.writer = writer;
        this.library = library;
        this.address = address;
        this.phone = phone;
        this.price = price;
        this.sellerId = sellerId;
    }

    public BookModel(String name, String writer, String library, String address, String phone, String price, String  sellerId) {
        this.name = name;
        this.writer = writer;
        this.library = library;
        this.address = address;
        this.phone = phone;
        this.price = price;
        this.sellerId = sellerId;
    }

    public BookModel() {
    }



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWriter() {
        return writer;
    }

    public String getLibrary() {
        return library;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public String getSellerId() {
        return sellerId;
    }
}
