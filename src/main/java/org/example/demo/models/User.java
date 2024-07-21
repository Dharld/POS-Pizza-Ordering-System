package org.example.demo.models;

public class User {
    private int id;
    private String fname;
    private String lname;

    private String email;

    private String phoneNum;

    private Address address = null;

    public User() {

    }

    public User(String fname, String lname, String email, String phoneNum) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNum = phoneNum;
        this.id = -1;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getName() {
        return getFname() + " " + getLname();
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
