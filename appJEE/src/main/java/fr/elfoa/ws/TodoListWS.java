package fr.elfoa.ws;

import fr.elfoa.entities.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle todo list web service routes.
 * Provide routes GET, POST, PUT and DELETE.
 */
@Path("todo")
public class TodoListWS 
{
    /**
     * Logger for todo list web service.
     */
    private static final Logger LOG = Logger.getLogger(TodoListWS.class.getCanonicalName());

    @EJB
    /**
     * Manager of todo : EJB.
     */
    private ManagedTodo m_todoManager;

    @EJB
    /**
     * Manager of user : EJB.
     */
    private ManagedUser m_userManager;

    /**
     * List of todos.
     */
    private static final TodoList todos = new TodoList();

    /**
     * Static inits
     */
    static 
    {
        todos.add(1, new Todo("foo"))
             .add(2, new Todo("bar"))
             .add(3, new Todo("wiz"));
    }

    /**
     * Get the list of todos already created.
     * @return List of todos as JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() 
    {
        ObjectMapper mapper = new ObjectMapper();
        Writer writer = new StringWriter();

        // Compute the JSON response including all todos.
        try
        {
            for (Todo todo : todos.getAll()) 
            {
                mapper.writeValue(writer, todo);
            }
            writer.close();
        } 
        catch (IOException e) 
        {
            LOG.log(Level.SEVERE, "Unable to seralize todo list");
            return "{}";
        }

        return writer.toString();
    }

    /**
     * Route to create a new todo to be added in the todo list.
     * @param text Parameter text coming from a form matching field name text.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void create(@FormParam("text") String text) 
    {
        todos.add(new Todo(text));
    }

    /**
     * Route to delete the todo corresponding to the given id.
     * @param id Id of the todo to delete.
     */
    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") Integer id) 
    {
        todos.delete(id);
    }

    /**
     * Update the todo item state (done or not) corresponding to the given id.
     * @param id Id of the todo to update (taken from form field id).
     * @param done State done or not of the todo idem (taken from form field done).
     */
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void check(@PathParam("id") Integer id, @FormParam("done") String done)
    {
        if ("true".equals(done)) 
        {
            todos.done(id);
        } 
        else 
        {
            todos.unDone(id);
        }
    }

    /**
     * Route to create a new todo to be added in the todo list.
     *
     */
    @Path("affectTodo/{userId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String affectTodo(@PathParam("userId") Integer idUser, Todo todo)
    {
        String ret;
        try
        {
            User user = m_userManager.readUser(idUser);

            todo.setUser(user);
            m_todoManager.createTodo(todo);

            ret = "Todo affected to user " + user.getName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ret = e.getMessage();
        }

        return ret;
    }
}
