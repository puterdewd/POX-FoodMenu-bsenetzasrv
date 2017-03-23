package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author puter
 */
public class FoodItem implements IFoodItem {

    private String id;
    private String name;
    private String description;
    private String country;
    private String category;
    private String price;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String foodItem = "<FoodItem country=\"" + country + "\">\n" +
                          "    <id>" + id + "</id>\n" +
                          "    <name>" + name + "</name>\n" +
                          "    <description>" + description + "</description>\n" + 
                          "    <category>" + category + "</category>\n" +
                          "    <price>" + price + "</price>\n" +
                          "</FoodItem>\n";
        return foodItem;        
    }
}
