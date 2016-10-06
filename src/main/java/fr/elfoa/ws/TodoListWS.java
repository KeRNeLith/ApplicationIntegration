package fr.elfoa.ws;

import fr.elfoa.entities.Todo;
import fr.elfoa.entities.TodoList;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("todo")
public class TodoListWS {

    private static final Logger LOG = Logger.getLogger(TodoListWS.class.getCanonicalName());

    private static final TodoList todos = new TodoList();

    static {
        todos.add(1, new Todo("foo"))
             .add(2, new Todo("bar"))
             .add(3, new Todo("wiz"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() {
        ObjectMapper mapper = new ObjectMapper();
        Writer writer = new StringWriter();

        try {
            for (Todo todo : todos.getAll()) {
                mapper.writeValue(writer, todo);
            }
            writer.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Unable to seralize todo list");
            return "{}";
        }

        return writer.toString();

    }



    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void create(@FormParam("text") String text) {
        todos.add(new Todo(text));
    }



    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") Integer id) {
        todos.delete(id);
    }



    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void check(@PathParam("id") Integer id, @FormParam("done") String done) {
        if ("true".equals(done)) {
            todos.done(id);
        } else {
            todos.unDone(id);
        }
    }
}
