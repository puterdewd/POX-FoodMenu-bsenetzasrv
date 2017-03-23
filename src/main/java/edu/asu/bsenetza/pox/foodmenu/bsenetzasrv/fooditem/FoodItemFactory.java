package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem;

public class FoodItemFactory implements IFoodItemFactory {

    @Override
    public FoodItem createFoodItem(String id, String name, String description, String country, String category, String price) {
        FoodItem foodItem = new FoodItem();
        foodItem.setId(id);
        foodItem.setName(name);
        foodItem.setDescription(description);
        foodItem.setCountry(country);
        foodItem.setCategory(category);
        foodItem.setPrice(price);
        return foodItem;
    }
    
}
