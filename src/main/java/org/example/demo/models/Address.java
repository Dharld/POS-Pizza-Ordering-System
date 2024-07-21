package org.example.demo.models;

public class Address {
    private int id;
    private String street;
    private String city;
    private String zip;
    private String state;

    public Address(String street, String city, String zip, String state) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getState() {
        return this.state;
    }

    public void setId(int id) {
        this.id = id;
    }


}
