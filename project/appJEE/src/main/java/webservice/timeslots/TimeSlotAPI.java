package webservice.timeslots;

import ejb.face.TimeSlotEJB;
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
    /* TODO : A modifier : ajouter un ID de doctor en URL
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTimeSlot(TimeSlot timeSlot)
    {
        Response response;

        if (m_timeSlotManager.createTimeSlot(timeSlot.getBegin(), timeSlot.getEnd(), timeSlot.getDoctor()) != null)
        {
            response = Response.ok("{\n\t\"success\": \"TimeSlot from " + timeSlot.getBegin() + " "
                            + " to " + timeSlot.getEnd() + " with " + timeSlot.getDoctor() + " created.\"\n}",
                    MediaType.APPLICATION_JSON).build();
        }
        else
        {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\n\t\"error\": \"Error while creating a timeSlot.\"\n}").build();
        }

        return response;
    }*/

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

    /**
     * Route to update a timeSlot's name
     * @param id ID of the timeSlot to update.
     * @param newTimeSlot timeSlot containing update information.
     * @return Response indicating if the update have been done.
     */
    @Path("{timeSlotId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTimeSlot(@PathParam("timeSlotId") long id, TimeSlot newTimeSlot)
    {
        Response response;

        // Get the timeSlot to update
        TimeSlot timeSlot = m_timeSlotManager.readTimeSlot(id);

        if (timeSlot != null)
        {
            // TODO : make verifications and then update. Fields : begin, end,

            // Do the update
            try
            {
                //m_timeSlotManager.updateTimeSlot(timeSlot);
                response = Response.ok("{\n\t\"success\": \"TimeSlot updated.\"\n}", MediaType.APPLICATION_JSON).build();
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
                                .entity("{\n\t\"error\": \"No timeSlot found.\"\n}")
                                .build();
        }

        return response;
    }
}
