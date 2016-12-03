package webservice.timeslots;

import ejb.dao.persons.ManagedDoctor;
import ejb.face.TimeSlotEJB;
import entities.persons.Doctor;
import entities.timeslots.TimeSlot;
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
 * Class that handle TimeSlot web service routes.
 * Provide routes GET, POST, PUT and DELETE.
 * Created by ace_nanter on 25/11/16.
 */
@Path("timeslot")
public class TimeSlotAPI
{
    /**
     * Logger for timeSlot web service.
     */
    private static final Logger LOG = Logger.getLogger(TimeSlotAPI.class.getCanonicalName());

    /**
     * Manager of TimeSlot : EJB.
     */
    @EJB
    private TimeSlotEJB m_timeSlotManager;

    /**
     * Manager of Doctor : EJB.
     */
    @EJB
    private ManagedDoctor m_doctorManager;

    /**
     * Get the TimeSlot selected.
     * @return Response containing a TimeSlot encoded in JSON.
     */
    @GET
    @Path("{timeSlotId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getTimeSlot(@PathParam("timeSlotId") long id)
    {
        Response response;

        // Look if there is a timeSlot associated to the given ID
        TimeSlot timeSlot = m_timeSlotManager.readTimeSlot(id);

        // Create JSON response
        if(timeSlot != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();

            try
            {
                mapper.writeValue(writer, timeSlot);
                writer.close();
                response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
            }
            catch(IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize a timeSlot.");
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity("{\n\t\"error\": \"Unable to serialize a timeSlot.\"\n}")
                                    .build();
            }
        }
        else
        {
            response = Response .status(Response.Status.NOT_FOUND)
                                .entity("{\n\t\"error\": \"Unable to find the timeSlot.\"\n}")
                                .build();
        }

        return response;
    }

    /**
     * Route to create a new timeSlot to be added in the list.
     * @param timeSlot TimeSlot to create. Sent using JSON.
     * @return Response indicating if the timeSlot has been created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTimeSlot(TimeSlot timeSlot)
    {
        Response response;

        // Check if the doctor and its id have been provided
        if(timeSlot.getDoctor() != null && timeSlot.getDoctor().getId() != null)
        {
            try
            {
                // Look if there is a doctor associated to the given ID
                Doctor doctor = m_doctorManager.readDoctor(timeSlot.getDoctor().getId());

                // If the doctor has been found
                if(doctor != null)
                {
                    // Create the TimeSlot
                    if (m_timeSlotManager.createTimeSlot(timeSlot.getBegin(), timeSlot.getEnd(), doctor) != null)
                    {
                        // Create the JSON response
                        response = Response.ok("{\n\t\"success\": \"TimeSlot from " + timeSlot.getBegin() + " "
                                        + " to " + timeSlot.getEnd() + " with " + doctor.getFirstname()
                                        + " " + doctor.getLastname() + " created.\"\n}",
                                MediaType.APPLICATION_JSON).build();
                    }
                    else
                    {
                        response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while creating a timeSlot.\"\n}")
                                .build();
                    }
                }
                else
                {
                    response = Response .status(Response.Status.NOT_FOUND)
                            .entity("{\n\t\"error\": \"Unable to find the doctor given.\"\n}")
                            .build();
                }
            }
            catch (Exception e)
            {
                LOG.log(Level.SEVERE, e.getMessage());
                e.printStackTrace();
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\t\"error\": \"Error while creating the TimeSlot.\"\n}")
                        .build();
            }

        }
        else
        {
            response = Response .status(Response.Status.BAD_REQUEST)
                    .entity("{\n\t\"error\": \"No doctor ID provided !\"\n}")
                    .build();
        }

        return response;
    }

    /**
     * Route to delete the timeSlot corresponding to the given id.
     * @param id Id of the timeSlot to delete.
     * @return Response indicating the delete have been done.
     */
    @Path("{timeSlotId}")
    @DELETE
    public Response deleteTimeSlot(@PathParam("timeSlotId") long id)
    {
        Response response;

        if(m_timeSlotManager.deleteTimeSlot(id))
        {
            response = Response.ok("TimeSlot deleted", MediaType.APPLICATION_JSON).build();
        }
        else
        {
            // It occurs a problem while trying to delete the timeslot.
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while deleting the TimeSlot.\"\n}")
                                .build();
        }

        return response;
    }
}
