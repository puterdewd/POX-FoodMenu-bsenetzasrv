package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.menuitem;

import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem.FoodItem;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadMenuItem extends ActionMenuItem {

    private static final Logger LOG = LoggerFactory.getLogger(ReadMenuItem.class);

    private static final String START_TAG = "<RetrievedFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n";
    private static final String END_TAG = "</RetrievedFoodItems>";

    public ReadMenuItem(Document document) {
        super(document);
    }

    @Override
    public void execute() {
        if (isValidRequest(document)) {
            response.append(START_TAG);
            List<String> idList = getReadIds(document);
            idList.forEach((id) -> {
                LOG.debug("Id: " + id);
                FoodItem foodItem = foodItemManager.getFoodItemById(id);
                if (foodItem == null) {
                    LOG.debug("foodItem is null");
                    appendInvalidFoodItemToResponse(id);
                } else {
                    appendFoodItemToResponse(foodItem);
                }
            });
            response.append(END_TAG);
            LOG.debug(response.toString());
        } else {
            setInvalidMessageResponse();
        }
    }

    private void appendInvalidFoodItemToResponse(String id) {
        String invalidFoodItem = "<InvalidFoodItem>\n"
                + "    <FoodItemId>" + id + "</FoodItemId>\n"
                + "</InvalidFoodItem>\n";
        response.append(invalidFoodItem);
    }

    private void appendFoodItemToResponse(FoodItem foodItem) {
        response.append(foodItem);
    }

    private List<String> getReadIds(Document document) {
        List<String> ids = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("FoodItemId");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ids.add(eElement.getTextContent());
            }
        }
        return ids;
    }

    @Override
    protected boolean isValidRequest(Document document) {
        boolean isValid = true;

        NodeList foodItemIds = document.getElementsByTagName("FoodItemId");

        if (foodItemIds.getLength() < 1) {
            return false;
        }
        try {
            for (int i = 0; i < foodItemIds.getLength(); i++) {
                Node foodItemId = foodItemIds.item(i);
                if (foodItemId.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) foodItemId;

                    if (eElement.getTextContent().isEmpty()) {
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
