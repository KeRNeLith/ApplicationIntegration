package webservice.persons;

import ejb.dao.persons.ManagedPatient;
import entities.persons.Patient;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle Patient web service routes.
 * Provide routes GET, POST, PUT and DELETE.
 * Created by kernelith on 30/11/16.
 */
@Path("patient")
public class PatientAPI
{
    /**
     * Logger for patient web service.
     */
    private static final Logger LOG = Logger.getLogger(PatientAPI.class.getCanonicalName());

    /**
     * Manager of Patient : EJB.
     */
    @EJB
    private ManagedPatient m_patientManager;

    /**
     * Get the Patient selected.
     * @return Response containing a Patient encoded in JSON.
     */
    @GET
    @Path("{patientId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getPatient(@PathParam("patientId") long id)
    {
        Response response;

        // Look if there is a patient associated to the given ID
        Patient patient = m_patientManager.readPatient(id);

        // Create JSON response
        if(patient != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();

            try
            {
                mapper.writeValue(writer, patient);
                writer.close();
                response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
            }
            catch(IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize a patient.");
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity("{\n\t\"error\": \"Unable to serialize a patient.\"\n}")
                                    .build();
            }
        }
        else
        {
            response = Response .status(Response.Status.NOT_FOUND)
                                .entity("{\n\t\"error\": \"Unable to find the patient.\"\n}")
                                .build();
        }

        return response;
    }

    /**
     * Route to create a new patient to be added in the list.
     * @param patient Patient to create. Sent using JSON.
     * @return Response indicating if the patient has been created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPatient(Patient patient)
    {
        Response response;

        if (m_patientManager.createPatient(patient.getFirstname(), patient.getLastname()) != null)
        {
            response = Response.ok("{\n\t\"success\": \"Patient "   + patient.getFirstname() + " "
                                                                    + patient.getLastname() + " created.\"\n}",
                                    MediaType.APPLICATION_JSON)
                                .build();
        }
        else
        {
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while creating a patient.\"\n}")
                                .build();
        }

        return response;
    }

    /**
     * Route to delete a patient corresponding to the given id.
     * @param id Id of the patient to delete.
     * @return Response indicating the delete have been done.
     */
    @Path("{patientId}")
    @DELETE
    public Response deletePatient(@PathParam("patientId") long id)
    {
        Response response;

        if(m_patientManager.deletePatient(id))
        {
            response = Response.ok("Patient deleted", MediaType.APPLICATION_JSON).build();
        }
        else
        {
            // It occurs a problem while trying to delete the doctor.
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while deleting the patient.\"\n}")
                                .build();
        }


        return response;
    }

    /**
     * Route to update a patient's name
     * @param id ID of the patient to update.
     * @param newPatient Patient containing update information.
     * @return Response indicating if the update have been done.
     */
    @Path("{patientId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("patientId") long id, Patient newPatient)
    {
        Response response;

        // Get the patient to update
        Patient patient = m_patientManager.readPatient(id);

        if (patient != null)
        {
            // Retrieves names
            String firstName = newPatient.getFirstname();
            String lastName = newPatient.getLastname();

            // Update fields
            if (firstName != null)
            {
                patient.setFirstname(firstName);
            }

            if (lastName != null)
            {
                patient.setLastname(lastName);
            }

            // Do the update
            try
            {
                m_patientManager.updatePatient(patient);
                response = Response.ok("{\n\t\"success\": \"Patient updated.\"\n}", MediaType.APPLICATION_JSON).build();
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
