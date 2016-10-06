package fr.elfoa.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Class that handle Hello World web service route.
 */
@Path("/hello")
public class HelloWorld 
{
    /**
     * Hello world page template (as string).
     */
    private static final String TEMPLATE  = "{ \"Hello\" : \"%1s\"}";

    /**
     * Route to test the application : Hello World.
     * @return Hello world page as JSON content.
     */
    @GET
    @Produces("application/json")
    public String sayHello() 
    {
      return String.format(TEMPLATE,"World");
    }
}
