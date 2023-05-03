package com.ubb.postuniv.domain;

public class Medicine extends Entity{
    private String name;
    private String manufacturer;
    private float price;
    private boolean prescription;

    public Medicine(int id, String name, String manufacturer, float price, boolean prescription) {
        super(id);
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.prescription = prescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPrescription() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                ", prescription=" + prescription +
                '}';
    }
}
