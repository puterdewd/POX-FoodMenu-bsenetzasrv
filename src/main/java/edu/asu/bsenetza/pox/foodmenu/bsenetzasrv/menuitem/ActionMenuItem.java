package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.menuitem;

import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem.FoodItemManager;
import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.process.IProcessMenuItem;
import org.w3c.dom.Document;

public abstract class ActionMenuItem implements IProcessMenuItem {

    protected FoodItemManager foodItemManager = FoodItemManager.getInstance();
    protected Document document;
    protected StringBuffer response = new StringBuffer();

    public ActionMenuItem(Document document) {
        this.document = document;
    }

    public String getResponse() {
        return response.toString();
    }

    void setInvalidMessageResponse() {
        String foodItemExists = "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment/>\n";
        response.append(foodItemExists);
    }
    
    protected abstract boolean isValidRequest(Document document);
}
