package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv;

import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.fooditem.FoodItemManager;
import edu.asu.bsenetza.pox.foodmenu.bsenetzasrv.process.ProcessRequests;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;

import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Root resource (exposed at "inventory" path)
 */
@Path("inventory")
public class Inventory {

    private static final Logger LOG = LoggerFactory.getLogger(Inventory.class);

    private static String greeting;

    public Inventory() {
        if (FoodItemManager.getInstance().getFoodItems().isEmpty()) {
            LOG.debug("Initialize inventory");
            FoodItemManager.getInstance().initalizeFoodItems(getFoodMenuDocument());
        }

    }

    private Document getFoodMenuDocument() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File foodItems = new File(classLoader.getResource("FoodItemData.xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(foodItems);
            return document;
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            LOG.error(ex.getLocalizedMessage());
            return null;
        }

    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @param content
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces("text/html")
    public Response getHtmlGreeting() {

        Response response;
        response = Response.status(Response.Status.OK).entity(greeting).build();

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_XML)
    public Response postReponse(String content) {

        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);

        String result = "";
        try {
            ProcessRequests processRequests = new ProcessRequests();
            result = processRequests.actionRequests(content);
            LOG.debug("processRequests result = {}", result);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            LOG.error(ex.getLocalizedMessage());
        }

        Response response;
        response = Response.status(Response.Status.OK).entity(result).build();
        return response;
    }

}
