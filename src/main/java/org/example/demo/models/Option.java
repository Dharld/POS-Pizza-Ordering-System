package org.example.demo.models;

public class Option implements  Cloneable {

    private String name;
    private String value;
    private int itemID;

    public Option(String name, String value, int itemID) {
        this.name = name;
        this.value = value;
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getItemID() {
        return itemID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
