package webservice;

import ejb.dao.persons.ManagedDoctor;
import entities.persons.Doctor;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle Doctors web service routes.
 * Created by kernelith on 23/11/16.
 */
@Path("doctors")
public class DoctorsAPI
{
    /**
     * Logger for doctor web service.
     */
    private static final Logger LOG = Logger.getLogger(DoctorsAPI.class.getCanonicalName());

    @EJB
    /**
     * Manager of Doctor : EJB.
     */
    private ManagedDoctor m_doctorManager;

    /**
     * Get the list of Doctor already created.
     * @return A Response containing a list of Doctor as JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getAllDoctors()
    {
        Response response = Response.ok("{ }", MediaType.APPLICATION_JSON).build();
        List<Doctor> doctorList = null;

        // Get List of Doctors
        try
        {
            doctorList = m_doctorManager.readAllDoctors();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}").build();
        }

        // Create the JSON response including all Doctors.
        if (doctorList != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();

            try
            {
                mapper.writeValue(writer, doctorList);

                writer.close();
                response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
            }
            catch (IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize doctor list.");
                response = Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
                    .entity("{\n\t\"error\": \"Unable to serialize doctor list.\"\n}").build();
            }
        }

        return response;
    }
}
