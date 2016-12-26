package webservice.timeslots;

import ejb.dao.persons.ManagedPatient;
import ejb.face.AppointmentEJB;
import ejb.face.TimeSlotEJB;
import entities.persons.Patient;
import entities.timeslots.Appointment;
import entities.timeslots.TimeSlot;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle Appointment web service routes.
 * Provides routes GET, POST, PUT and DELETE.
 * Created by ace_nanter on 02/12/16.
 */
@Path("appointment")
public class AppointmentAPI
{
    /**
     * Logger for timeSlot web service.
     */
    private static final Logger LOG = Logger.getLogger(AppointmentAPI.class.getCanonicalName());

    /**
     * Manager of Appointment : EJB.
     */
    @EJB
    private AppointmentEJB m_appointmentManager;

    /**
     * Manager of Patient : EJB.
     */
    @EJB
    private ManagedPatient m_patientManager;

    /**
     * Manager of TimeSlot : EJB.
     */
    @EJB
    private TimeSlotEJB m_timeSlotManager;

    /**
     * Route to create a new appointment for a patient
     * @param appointment Appointment to create. Sent using JSON.
     * @return Response indicating if the appointment has been created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAppointment(Appointment appointment)
    {
        Response response;

        // Check if the patient and the timeSlot and their ids have been provided
        if(appointment.getPatient() != null && appointment.getPatient().getId() != null
                && appointment.getTimeSlot() != null && appointment.getTimeSlot().getId() != null)
        {
            try
            {
                // Look if there are a patient and a timeSlot associated to the given IDs
                Patient patient = m_patientManager.readPatient(appointment.getPatient().getId());
                TimeSlot timeSlot = m_timeSlotManager.readTimeSlot(appointment.getTimeSlot().getId());

                // If the patient and the timeSlot have been found
                if(patient != null && timeSlot != null)
                {
                    // Create the appointment
                    if (m_appointmentManager.createAppointment(appointment.getBegin(), appointment.getEnd(), timeSlot, patient) != null)
                    {
                        // Create the JSON response
                        response = Response.ok("{\n\t\"success\": \"Appointment from " + appointment.getBegin() + " "
                                        + " to " + appointment.getEnd() + " for " + patient.getFirstname()
                                        + " " + patient.getLastname() + " created.\"\n}",
                                MediaType.APPLICATION_JSON).build();
                    }
                    else
                    {
                        response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"Error while creating an appointment.\"\n}")
                                .build();
                    }
                }
                else
                {
                    response = Response .status(Response.Status.NOT_FOUND)
                            .entity("{\n\t\"error\": \"Unable to find the patient and timeslot given.\"\n}")
                            .build();
                }
            }
            catch (Exception e)
            {
                LOG.log(Level.SEVERE, e.getMessage());
                e.printStackTrace();
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\t\"error\": \"Error while creating the appointment.\"\n}")
                        .build();
            }

        }
        else
        {
            response = Response .status(Response.Status.BAD_REQUEST)
                    .entity("{\n\t\"error\": \"No patient ID or no timeslot ID provided !\"\n}")
                    .build();
        }

        return response;
    }

    @DELETE
    @Path("{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelAppointment(@PathParam("appointmentId") long id)
    {
        Response response;

        if(m_appointmentManager.cancelAppointment(id))
        {
            response = Response.ok("Appointment deleted", MediaType.APPLICATION_JSON).build();
        }
        else
        {
            // It occurs a problem while trying to delete the appointment.
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\n\t\"error\": \"Error while deleting the appointment.\"\n}")
                    .build();
        }

        return response;
    }

    @Path("{appointmentId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentId") long id, Appointment newAppointement)
    {
        Response response = Response .status(Response.Status.NOT_FOUND)
                .entity("{\n\t\"error\": \"Not enough information sent.\"\n}")
                .build();

        // Get the patient to update
        Appointment appointment = m_appointmentManager.readAppointment(id);

        if (appointment != null)
        {
            // Retrieves dates
            Date begin = newAppointement.getBegin();
            Date end = newAppointement.getEnd();

            // Retrieves patient
            long patientId = (newAppointement.getPatient().getId() != null) ? newAppointement.getPatient().getId() : -1;

            try
            {
                if (begin != null && end != null)
                {
                    m_appointmentManager.modifyAppointment(id, begin, end);
                    response = (m_appointmentManager.modifyAppointment(id, begin, end)) ?
                        Response.ok("{\n\t\"success\": \"Appointment updated.\"\n}", MediaType.APPLICATION_JSON).build()
                            : Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\n\t\"error\": \"Error while updating the appointment.\"\n}")
                            .build();
                }

                // Update patient
                if (patientId != -1) {
                    m_appointmentManager.modifyAppointment(id, patientId);
                    response = (m_appointmentManager.modifyAppointment(id, patientId)) ?
                        Response.ok("{\n\t\"success\": \"Appointment updated.\"\n}", MediaType.APPLICATION_JSON).build()
                            : Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\n\t\"error\": \"Error while updating the appointment.\"\n}")
                            .build();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                        .build();
            }
        }

        return response;
    }

}
