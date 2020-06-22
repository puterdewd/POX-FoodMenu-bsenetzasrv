package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.process;

import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.menuitem.ActionMenuItem;
import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.menuitem.ActionMenuItemFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ProcessRequests {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessRequests.class);

    ActionMenuItemFactory actionMenuItemFactory = new ActionMenuItemFactory();
    
    public String actionRequests(String contents) throws SAXException, IOException, ParserConfigurationException {
        Document document = contentsToDocument(contents);
        ActionMenuItem actionMenuItem = actionMenuItemFactory.createActionMenuItem(document);
        actionMenuItem.execute();
        LOG.debug(actionMenuItem.getResponse());
        return actionMenuItem.getResponse();
    }

    private Document contentsToDocument(String contents) throws SAXException, IOException, ParserConfigurationException {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new InputSource(new ByteArrayInputStream(contents.getBytes("utf-8"))));
            document.getDocumentElement().normalize();
            return document;

    }

}
