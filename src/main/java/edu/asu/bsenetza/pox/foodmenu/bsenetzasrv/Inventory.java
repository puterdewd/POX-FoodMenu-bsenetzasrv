package edu.asu.bsenetza.pox.foodmenu.bsenetzasrv;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

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
    
    /**
     * Creates a new instance of HelloworldResource
     */
    public Inventory() {
        LOG.info("Creating a GreetingResource Resource");
        initialize();
    }

    private void initialize()  {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File foodItems = new File(classLoader.getResource("FoodItemData.xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(foodItems);
            LOG.debug("Root element : " + doc.getDocumentElement().getNodeName());
        } catch (ParserConfigurationException|IOException|SAXException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    
    }
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @param content
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postTextGreeting(String content) {
        LOG.info("Creating the html greeting message");
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);
        
        greeting = "<html><body><h1>" + content + "</body></h1></html>";
        LOG.debug("Greeting text is = {}", greeting);
        
        Response response;
        response = Response.status(Response.Status.CREATED).build();
        return response;
    }

    /**
     * Retrieves representation of an instance of helloWorld.HelloWorld
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public Response getHtmlGreeting() {
        LOG.info("Retrieving the html greeting message");
        LOG.debug("GET request");
        LOG.debug("Greeting message = {}", greeting);
        
        Response response;
        response = Response.status(Response.Status.OK).entity(greeting).build();
        
        return response;
    }
}
