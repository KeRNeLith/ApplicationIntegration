package webservice.timeslots;

import ejb.face.AppointmentEJB;
import entities.timeslots.Appointment;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
 * Class that handle Appointments web service routes.
 * Created by ace_nanter on 02/12/16.
 */
@Path("appointments")
public class AppointmentsAPI
{
    /**
     * Logger for patient web service.
     */
    private static final Logger LOG = Logger.getLogger(AppointmentsAPI.class.getCanonicalName());

    /**
     * Manager of Appointments : EJB.
     */
    @EJB
    private AppointmentEJB m_appointmentManager;

    /**
     * Get the list of Appointments associated to the given patient.
     * @param id Patient id.
     * @return List of appointments as JSON.
     */
    @GET
    @Path("{patientId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getAllAppointmentsFromPatient(@PathParam("patientId") long id)
    {
        Response response = Response.ok("{}", MediaType.APPLICATION_JSON).build();
        List<Appointment> appointmentList = null;

        // Get List of Appointments
        try
        {
            appointmentList = m_appointmentManager.readAllAppointmentsFromPatient(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                    .build();
        }

        if(appointmentList != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();
            try
            {
                mapper.writeValue(writer, appointmentList);

                writer.close();
                response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
            }
            catch (IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize appointment list.");
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\t\"error\": \"Unable to serialize appointment list.\"\n}")
                        .build();
            }
        }

        return response;
    }
}
