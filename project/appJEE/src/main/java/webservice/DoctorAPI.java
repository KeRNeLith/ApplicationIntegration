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
     * Get the Doctor selected.
     * @return a Doctor encoded in JSON.
     */
    @GET
    @Path("{idDoctor}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public String getDoctor(@PathParam("idDoctor") long id)
    {
        String ret = "{}";
        Doctor doctor = null;

        // Look if there is a doctor associated to the given ID
        doctor = m_doctorManager.readDoctor(id);

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
                ret = "Unable to serialize a doctor.";
            }
        }
        else
        {
            ret = "Unable to find the doctor.";
        }

        return ret;
    }

    /**
     * Get the list of Doctor already created.
     * @return List of Doctor as JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public String getAllDoctors()
    {
        String ret = "{}";
        List<Doctor> doctorList = null;

        // Get List of Doctors
        try
        {
            doctorList = m_doctorManager.getList();
        }
        catch(Exception e) {
            e.printStackTrace();
            ret = e.getMessage();
        }

        // Create the JSON response including all Doctors.
        if(doctorList != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();

            try
            {
                for (Doctor d : doctorList)
                {
                    mapper.writeValue(writer, d);
                }
                writer.close();
                ret = writer.toString();
            }
            catch (IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize doctor list.");
                ret = "Unable to serialize doctor list.";
            }
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
            ret = "Doctor " + doctor.getFirstname() + " "
                    + doctor.getLastname() + " created.";
        }
        else
        {
            ret = "Error while creating a doctor !";
        }

        return ret;
    }

    /**
     * Route to delete the doctor corresponding to the given id.
     * @param id Id of the doctor to delete.
     */
    @Path("{id}")
    @DELETE
    public String deleteDoctor(@PathParam("id") long id)
    {
        m_doctorManager.deleteDoctor(id);

        return "Doctor deleted.";
    }

    /**
     * Route to update a doctor's name
     * @param id ID of the doctor to update.
     * @param newDoctor New firstname.
     * @return Response indicating if the update have been done.
     */
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateDoctor(@PathParam("id") long id, Doctor newDoctor) {
        String ret = "";

        // Get the doctor to update
        Doctor doctor = m_doctorManager.readDoctor(id);

        // Retrieves names
        String firstname = newDoctor.getFirstname();
        String lastname = newDoctor.getLastname();

        if(doctor != null)
        {
            if(firstname != null)
            {
                doctor.setFirstname(firstname);
            }
            if(lastname != null)
            {
                doctor.setLastname(lastname);
            }

            // Do the update
            try
            {
                m_doctorManager.updateDoctor(doctor);
                ret = "Doctor updated !";
            }
            catch(Exception e)
            {
                e.printStackTrace();
                ret = e.getMessage();
            }
        }
        else
        {
            ret = "No doctor found !";
        }

        return ret;
    }
}
