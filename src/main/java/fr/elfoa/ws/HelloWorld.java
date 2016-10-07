package fr.elfoa.ws;

import fr.elfoa.utils.JNDIUtils;

import javax.print.attribute.IntegerSyntax;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static fr.elfoa.utils.JNDIUtils.getValue;

/**
 * Class that handle Hello World web service route.
 */
@Path("hello")
public class HelloWorld 
{
    /**
     * Hello world page template (as string).
     */
    private static final String TEMPLATE  = "{ \"Hello\" : \"%1s\", \"Counter\": \"%2d\" }";

    /**
     * Route to test the application : Hello World.
     * @return Hello world page as JSON content.
     */
    @GET
    @Produces("application/json")
    public String sayHello() 
    {
        Integer counter = JNDIUtils.<Integer>getValue("HelloCounter");
        ++counter;  // Increment counter
        JNDIUtils.setValue("HelloCounter", counter);

        return String.format(TEMPLATE, JNDIUtils.<String>getValue("HelloMessage"), counter);
    }
}
