package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FoodItemManager {

    private static final FoodItemManager instance = new FoodItemManager();

    private List<FoodItem> foodItems;

    public static FoodItemManager getInstance() {
        return instance;
    }

    private FoodItemManager() {
        foodItems = new ArrayList<>();
    }

    public FoodItem getFoodItemById(String id) {
        for (FoodItem foodItem : foodItems) {
            if (id.equals(foodItem.getId())) {
                return foodItem;
            }
        }
        return null;
    }
    
    public FoodItem getFoodItemByNameAndCategory(String name, String category) {
        for (FoodItem foodItem : foodItems) {
             if (name.equals(foodItem.getName()) && category.equals(foodItem.getCategory())) {
                return foodItem;
            }
        }
        return null;
    }
    
    public boolean contains(String name, String category) {
        for (FoodItem foodItem : foodItems) {
            if (name.equals(foodItem.getName()) && category.equals(foodItem.getCategory())) {
                return true;
            }
        }
        return false;
    }

    public void addFoodItem(FoodItem foodItem) throws IllegalArgumentException {
        if (foodItem == null) {
            throw new IllegalArgumentException("Adding null entry to FoodItemManager");
        }
        foodItems.add(foodItem);
    }

    public List<FoodItem> getFoodItems() {
        FoodItemFactory foodItemFactory = new FoodItemFactory();
        List<FoodItem> foodList = new ArrayList<>();
        foodItems.forEach((foodItem) -> {
            foodList.add(foodItemFactory.createFoodItem(foodItem.getId(),
                    foodItem.getName(),
                    foodItem.getDescription(),
                    foodItem.getCountry(),
                    foodItem.getCategory(),
                    foodItem.getPrice()));
        });
        return foodList;
    }

    public void initalizeFoodItems(Document doc) {
        foodItems = new ArrayList<>();

        NodeList nList = doc.getElementsByTagName("FoodItem");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                foodItems.add(new FoodItemFactory().createFoodItem(eElement.getElementsByTagName("id").item(0).getTextContent(),
                        eElement.getElementsByTagName("name").item(0).getTextContent(),
                        eElement.getElementsByTagName("description").item(0).getTextContent(),
                        eElement.getAttribute("country"),
                        eElement.getElementsByTagName("category").item(0).getTextContent(),
                        eElement.getElementsByTagName("price").item(0).getTextContent()));
            }
        }
    }
}
