package webservice;

import dao.persons.ManagedDoctor;
import entities.persons.Doctor;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle Doctor list web service routes.
 * Provide routes GET, POST, PUT and DELETE.
 * Created by ace_nanter on 20/11/16.
 */

@Path("Doctor")
public class DoctorAPI
{
    /**
     * Logger for todo list web service.
     */
    private static final Logger LOG = Logger.getLogger(DoctorAPI.class.getCanonicalName());

    @EJB
    /**
     * Manager of Doctor : EJB.
     */
    private ManagedDoctor m_doctorManager;


    /**
     * Get the list of Doctor already created.
     * @return List of Doctor as JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public String list()
    {
        ObjectMapper mapper = new ObjectMapper();
        Writer writer = new StringWriter();

        // Get List of Doctors
        List<Doctor> doctorList = m_doctorManager.getList();

        // Compute the JSON response including all Doctors.
        try
        {
            for (Doctor d : doctorList)
            {
                mapper.writeValue(writer, d);
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
     * Route to create a new doctor to be added in the list.
     * @param doctor Doctor to create. Sent using JSON.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(Doctor doctor)
    {
        String ret;

        try
        {
            m_doctorManager.createDoctor(doctor);
            ret = "Docteur " + doctor.getFirstname() + " "
                + doctor.getLastname() + " créé.";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ret = e.getMessage();
        }

        return ret;
    }

    /**
     * Route to delete the doctor corresponding to the given id.
     * @param id Id of the doctor to delete.
     */
    @Path("{id}")
    @DELETE
    public String delete(@PathParam("id") Integer id)
    {
        String ret;

        try
        {
            m_doctorManager.deleteDoctor(id);
            ret = "Docteur effacé.";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ret = e.getMessage();
        }

        return ret;
    }

}
