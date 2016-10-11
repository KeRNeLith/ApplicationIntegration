package fr.elfoa.ws;

import fr.elfoa.database.DatabaseUtils;
import fr.elfoa.utils.JNDIUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.sql.*;

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

    /**
     * Route to get the name of a person corresponding to the given id.
     * @param id Id of the person.
     * @return Person name.
     */
    @Path("{id}")
    @GET
    public String sayHello(@PathParam("id") Integer id)
    {
        String ret = "Nobody matching id = " + id + ".";

        PreparedStatement ps;
        try
        {
            ps = DatabaseUtils.getConnection().prepareStatement("SELECT name FROM user WHERE id = ?;");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int NAME = 1;
                ret = rs.getString(NAME);
                rs.close();
            }

            ps.close();
        }
        catch (SQLException e)
        {
            System.err.println("There is a problem with the connection to database.");
        }

        return ret;
    }
}
