package webservice;

import ejb.dao.persons.ManagedDoctor;
import entities.persons.Doctor;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle Doctor web service routes.
 * Provide routes GET, POST, PUT and DELETE.
 * Created by ace_nanter on 20/11/16.
 */
@Path("doctor")
public class DoctorAPI
{
    /**
     * Logger for doctor web service.
     */
    private static final Logger LOG = Logger.getLogger(DoctorAPI.class.getCanonicalName());

    @EJB
    /**
     * Manager of Doctor : EJB.
     */
    private ManagedDoctor m_doctorManager;

    /**
     * Get the Doctor selected.
     * @return a Doctor encoded in JSON.
     */
    @GET
    @Path("{doctorId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public String getDoctor(@PathParam("doctorId") long id)
    {
        String ret;

        // Look if there is a doctor associated to the given ID
        Doctor doctor = m_doctorManager.readDoctor(id);

        // Create JSON response
        if(doctor != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();

            try
            {
                mapper.writeValue(writer, doctor);
                writer.close();
                ret = writer.toString();
            }
            catch(IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize a doctor.");
                ret = "{\n\t\"error\": \"Unable to serialize a doctor.\"\n}";
            }
        }
        else
        {
            ret = "{\n\t\"error\": \"Unable to find the doctor.\"\n}";
        }

        return ret;
    }

    /**
     * Route to create a new doctor to be added in the list.
     * @param doctor Doctor to create. Sent using JSON.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDoctor(Doctor doctor)
    {
        String ret;

        if(m_doctorManager.createDoctor(doctor) != null) {
            ret = "{\n\t\"success\": \"Doctor "     + doctor.getFirstname() + " "
                                                    + doctor.getLastname() + " created.\"\n}";
        }
        else
        {
            ret = "{\n\t\"error\": \"Error while creating a doctor.\"\n}";
        }

        return ret;
    }

    /**
     * Route to delete the doctor corresponding to the given id.
     * @param id Id of the doctor to delete.
     */
    @Path("{doctorId}")
    @DELETE
    public String deleteDoctor(@PathParam("doctorId") long id)
    {
        m_doctorManager.deleteDoctor(id);

        return "{\n\t\"success\": \"Doctor deleted.\"\n}";
    }

    /**
     * Route to update a doctor's name
     * @param id ID of the doctor to update.
     * @param newDoctor New firstname.
     * @return Response indicating if the update have been done.
     */
    @Path("{doctorId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateDoctor(@PathParam("doctorId") long id, Doctor newDoctor)
    {
        String ret;

        // Get the doctor to update
        Doctor doctor = m_doctorManager.readDoctor(id);

        if (doctor != null)
        {
            // Retrieves names
            String firstName = newDoctor.getFirstname();
            String lastName = newDoctor.getLastname();

            // Update fields
            if (firstName != null)
            {
                doctor.setFirstname(firstName);
            }

            if (lastName != null)
            {
                doctor.setLastname(lastName);
            }

            // Do the update
            try
            {
                m_doctorManager.updateDoctor(doctor);
                ret = "{\n\t\"success\": \"Doctor updated.\"\n}";
            }
            catch(Exception e)
            {
                e.printStackTrace();
                ret = "{\n\t\"error\": \"" + e.getMessage() + "\"\n}";
            }
        }
        else
        {
            ret = "{\n\t\"error\": \"No doctor found.\"\n}";
        }

        return ret;
    }
}
