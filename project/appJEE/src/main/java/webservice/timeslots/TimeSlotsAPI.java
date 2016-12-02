package webservice.timeslots;

import ejb.face.TimeSlotEJB;
import entities.timeslots.TimeInterval;
import entities.timeslots.TimeSlot;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle TimeSlots web service routes.
 * Created by ace_nanter on 02/12/16.
 */
@Path("timeslots")
public class TimeSlotsAPI {
    /**
     * Logger for patient web service.
     */
    private static final Logger LOG = Logger.getLogger(TimeSlotsAPI.class.getCanonicalName());

    /**
     * Manager of TimeSlots : EJB.
     */
    @EJB
    private TimeSlotEJB m_timeSlotManager;

    /**
     * Serialize a list of TimeSlot and return the appropriate Response.
     * @param timeSlotList List of TimeSlot to serialize
     * @return List of TimeSlot as JSON.
     */
    private Response serializeTimeSlotList(List<TimeSlot> timeSlotList) {

        Response response;

        ObjectMapper mapper = new ObjectMapper();
        Writer writer = new StringWriter();
        try
        {
            mapper.writeValue(writer, timeSlotList);

            writer.close();
            response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, "Unable to serialize timeslot list.");
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\n\t\"error\": \"Unable to serialize timeslot list.\"\n}")
                    .build();
        }

        return response;
    }

    /**
     * Get the list of TimeSlot associated to the given doctor.
     * @param id Doctor id.
     * @return List of TimeSlot as JSON.
     */
    @GET
    @Path("{doctorId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getAllTimeSlotsFromDoctor(@PathParam("doctorId") long id)
    {
        Response response = Response.ok("{}", MediaType.APPLICATION_JSON).build();
        List<TimeSlot> timeSlotList = null;

        // Get list of TimeSlots
        try
        {
            timeSlotList = m_timeSlotManager.readAllTimeSlotsFromDoctor(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                    .build();
        }

        if(timeSlotList != null)
        {
            response = serializeTimeSlotList(timeSlotList);
        }

        return response;
    }

    /**
     * Get the list of TimeSlot in the interval given.
     * @param interval interval given.
     * @return List of TimeSlot as JSON.
     */
    @GET
    @Path("/between")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllTimeSlotsBetween(TimeInterval interval)
    {
        Response response = Response.ok("{}", MediaType.APPLICATION_JSON).build();
        List<TimeSlot> timeSlotList = null;

        Date begin = interval.getBegin();
        Date end = interval.getEnd();

        if(begin != null && end != null) {
            // Get list of TimeSlots
            try
            {
                timeSlotList = m_timeSlotManager.readAllTimeSlotsBetween(interval.getBegin(), interval.getEnd());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                        .build();
            }

            if(timeSlotList != null)
            {
                response = serializeTimeSlotList(timeSlotList);
            }
        }
        else {
            response = Response .status(Response.Status.BAD_REQUEST)
                    .entity("{\n\t\"error\": \"Incorrect request.\"\n}")
                    .build();
        }

        return response;
    }

    /**
     * Give the list of the available TimeSlots.
     * @return List of TimeSlot as JSON.
     */
    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getAvailableTimeSlots()
    {
        Response response = Response.ok("{}", MediaType.APPLICATION_JSON).build();
        List<TimeSlot> timeSlotList = null;

        // Get list of TimeSlots
        try
        {
            timeSlotList = m_timeSlotManager.getAvailableTimeSlots();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                    .build();
        }

        if(timeSlotList != null)
        {
            response = serializeTimeSlotList(timeSlotList);
        }

        return response;
    }

}
