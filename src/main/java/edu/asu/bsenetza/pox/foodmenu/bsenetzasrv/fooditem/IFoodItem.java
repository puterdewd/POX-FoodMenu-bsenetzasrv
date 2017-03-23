/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem;

/**
 *
 * @author puter
 */
public interface IFoodItem {

    public String getId();

    public String getName();

    public String getDescription();

    public String getCountry();

    public String getCategory();

    public String getPrice();
    
    
    public void setId(String id);

    public void setName(String name);

    public void setDescription(String description);

    public void setCountry(String country);

    public void setCategory(String category);

    public void setPrice(String price);

}
