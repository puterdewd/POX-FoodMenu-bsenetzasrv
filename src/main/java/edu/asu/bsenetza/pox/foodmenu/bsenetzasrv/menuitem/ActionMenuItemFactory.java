package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.menuitem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class ActionMenuItemFactory implements IActionMenuItemFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ActionMenuItemFactory.class);
    
    private ActionMenuItem actionMenuItem;
    private static final String READ_TAG = "SelectedFoodItems";
    private static final String CREATE_TAG = "NewFoodItems";

    @Override
    public ActionMenuItem createActionMenuItem(Document document) {
        if (document.getDocumentElement().getNodeName().equals(READ_TAG)) {
            LOG.debug(READ_TAG);
            actionMenuItem = new ReadMenuItem(document);
        } else if (document.getDocumentElement().getNodeName().equals(CREATE_TAG)) {
            LOG.debug(CREATE_TAG);
            actionMenuItem = new CreateMenuItem(document);
        }
        
        return actionMenuItem;
    }

}
