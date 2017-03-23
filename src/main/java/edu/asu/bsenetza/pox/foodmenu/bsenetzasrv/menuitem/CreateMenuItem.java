package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.menuitem;

import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem.FoodItemFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CreateMenuItem extends ActionMenuItem  {
private static final Logger LOG = LoggerFactory.getLogger(CreateMenuItem.class);
    private static final Map<String, Integer> COUNTRY_MAP;

    static {
        Map<String, Integer> map = new HashMap<>();
        map.put("GB", 100);
        map.put("US", 200);
        map.put("IN", 300);
        COUNTRY_MAP = Collections.unmodifiableMap(map);
    }

    private FoodItemFactory foodItemFactory = new FoodItemFactory();

    public CreateMenuItem(Document document) {
        super(document);

    }

    @Override
    public void execute() {

        if (isValidRequest(document)) {
            NodeList foodItems = document.getElementsByTagName("FoodItem");

            for (int i = 0; i < foodItems.getLength(); i++) {
                Node foodItemData = foodItems.item(i);

                if (foodItemData.getNodeType() == Node.ELEMENT_NODE) {
                    Element foodItemElement = (Element) foodItemData;

                    if (foodItemManager.contains(foodItemElement.getElementsByTagName("name").item(0).getTextContent(),
                            foodItemElement.getElementsByTagName("category").item(0).getTextContent())) {

                        appendFoodItemExistsToResponse(foodItemManager.getFoodItemByNameAndCategory(
                                foodItemElement.getElementsByTagName("name").item(0).getTextContent(),
                                foodItemElement.getElementsByTagName("category").item(0).getTextContent()).getId());
                    } else {
                        String id = nextId(foodItemElement.getAttribute("country"));
                        foodItemManager.addFoodItem(foodItemFactory.createFoodItem(id,
                                foodItemElement.getElementsByTagName("name").item(0).getTextContent(),
                                foodItemElement.getElementsByTagName("description").item(0).getTextContent(),
                                foodItemElement.getAttribute("country"),
                                foodItemElement.getElementsByTagName("category").item(0).getTextContent(),
                                foodItemElement.getElementsByTagName("price").item(0).getTextContent()));
                        appendFoodItemAddedToResponse(id);
                    }
                }
            }
        } else {
            setInvalidMessageResponse();
        }
    }

    private void appendFoodItemAddedToResponse(String id) {
        String foodItemAdded = "<FoodItemAdded> xmlns=\"http://cse564.asu.edu/PoxAssignment\n"
                + "    <FoodItemId>" + id + "</FoodItemId>\n"
                + "</FoodItemAdded>\n";
        response.append(foodItemAdded);
    }

    private void appendFoodItemExistsToResponse(String id) {
        String foodItemExists = "<FoodItemExists> xmlns=\"http://cse564.asu.edu/PoxAssignment\n"
                + "    <FoodItemId>" + id + "</FoodItemId>\n"
                + "</FoodItemExists>\n";
        response.append(foodItemExists);
    }

    private String nextId(String country) {
        int idCounter;
        if (COUNTRY_MAP.get(country) != null) {
            idCounter = COUNTRY_MAP.get(country);
        } else {
            idCounter = (COUNTRY_MAP.size() + 1) * 100;
        }
        while (foodItemManager.getFoodItemById(String.valueOf(idCounter)) != null) {
            idCounter++;
        }
        return String.valueOf(idCounter);
    }

    @Override
    protected boolean isValidRequest(Document document) {
        boolean isValid = true;
        int NUM_ELEMENTS = 4;
        
        NodeList foodItems = document.getElementsByTagName("FoodItem");
        if (foodItems.getLength() < 1) {
            return false;
        }
        try {
            for (int i = 0; i < foodItems.getLength(); i++) {
                Node foodItemData = foodItems.item(i);

                if (foodItemData.getNodeType() == Node.ELEMENT_NODE) {
                    Element foodItemElement = (Element) foodItemData;
                    if (foodItemElement.getElementsByTagName("*").getLength() != NUM_ELEMENTS) {
                        return false;
                    }
                    if (foodItemElement.getAttribute("country").isEmpty()) {
                        return false;
                    }
                    if (foodItemElement.getElementsByTagName("name").item(0).getTextContent().isEmpty()) {
                        return false;
                    }
                    if (foodItemElement.getElementsByTagName("description").item(0).getTextContent().isEmpty()) {
                        return false;
                    }
                    if (foodItemElement.getElementsByTagName("category").item(0).getTextContent().isEmpty()) {
                        return false;
                    }
                    if (foodItemElement.getElementsByTagName("price").item(0).getTextContent().isEmpty()) {
                        return false;
                    }

                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            isValid = false;
        }
        return isValid;
    }

}
