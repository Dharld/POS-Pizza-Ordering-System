package org.example.demo.models;

import java.util.ArrayList;

public class Product implements Cloneable {
    private int id;
    private String name;
    private String desc;
    private float price;

    private String imageSrc;

    private ArrayList<Option> options = new ArrayList<>();

    public Product(int id, String name, String desc, float price, String imageSrc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.imageSrc = imageSrc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public float getPrice() {
        return price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getOptions() {
        StringBuilder str = new StringBuilder();
        for(Option o: options) {
            str.append(o.getValue()).append(",");
        }
        return str.toString();
    }
    public void addOption(Option o) {
        this.options.add(new Option(o.getName(), o.getValue(), o.getItemID()));
    }

    @Override
    public String toString() {
        return getOptions() + getName();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product clonedProduct = (Product) super.clone();

        // Perform deep copy of the options list
        clonedProduct.options = new ArrayList<>();
        for (Option option : this.options) {
            // Cloning each Option object and adding it to the new list
            clonedProduct.options.add((Option) option.clone());
        }

        return clonedProduct;
    }
}
