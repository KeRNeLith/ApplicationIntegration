package webservice.persons;

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
     * @return a Response containing a Doctor encoded in JSON.
     */
    @GET
    @Path("{doctorId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getDoctor(@PathParam("doctorId") long id)
    {
        Response response;

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
                response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
            }
            catch(IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize a doctor.");
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity("{\n\t\"error\": \"Unable to serialize a doctor.\"\n}")
                                    .build();
            }
        }
        else
        {
            response = Response .status(Response.Status.NOT_FOUND)
                                .entity("{\n\t\"error\": \"Unable to find the doctor.\"\n}")
                                .build();
        }

        return response;
    }

    /**
     * Route to create a new doctor to be added in the list.
     * @param doctor Doctor to create. Sent using JSON.
     * @return Resposne indicating if the doctor has been created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDoctor(Doctor doctor)
    {
        Response response;

        if (m_doctorManager.createDoctor(doctor.getFirstname(), doctor.getLastname()) != null)
        {
            response = Response.ok("{\n\t\"success\": \"Doctor "    + doctor.getFirstname() + " "
                                                                    + doctor.getLastname() + " created.\"\n}",
                                    MediaType.APPLICATION_JSON)
                                .build();
        }
        else
        {
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while creating a doctor.\"\n}")
                                .build();
        }

        return response;
    }

    /**
     * Route to delete the doctor corresponding to the given id.
     * @param id Id of the doctor to delete.
     * @Return Response indicating the delete have been done.
     */
    @Path("{doctorId}")
    @DELETE
    public Response deleteDoctor(@PathParam("doctorId") long id)
    {
        Response response;

        if(m_doctorManager.deleteDoctor(id))
        {
            response = Response.ok("Doctor deleted", MediaType.APPLICATION_JSON).build();
        }
        else
        {
            // It occurs a problem while trying to delete the doctor.
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while deleting the doctor.\"\n}")
                                .build();
        }


        return response;
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
    public Response updateDoctor(@PathParam("doctorId") long id, Doctor newDoctor)
    {
        Response response;

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
                response = Response.ok("{\n\t\"success\": \"Doctor updated.\"\n}", MediaType.APPLICATION_JSON).build();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                                    .build();
            }
        }
        else
        {
            response = Response .status(Response.Status.NOT_FOUND)
                                .entity("{\n\t\"error\": \"No doctor found.\"\n}")
                                .build();
        }

        return response;
    }
}
