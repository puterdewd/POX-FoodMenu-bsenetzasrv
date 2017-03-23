package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem;

public interface IFoodItemFactory {

    public FoodItem createFoodItem(String id, String name, String description, String country, String category, String price);

}
