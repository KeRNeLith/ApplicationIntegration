package ejb.face;

import entities.persons.Patient;
import entities.timeslots.Appointment;
import entities.timeslots.TimeSlot;

import java.util.Date;

/**
 * Interface for the EJB handling appointments.
 * Created by kernelith on 18/11/16.
 */
public interface AppointmentEJB
{
    /**
     * Create an appointment for the given patient into the given time slot.
     * @param begin Begin date of the appointment.
     * @param end End date of the appointment.
     * @param timeSlot Time slot that contain a free slot for the given begin and end date.
     * @param patient Patient concerned by the appointment.
     * @return An appointment instance if creation is possible otherwise null.
     */
    Appointment createAppointment(Date begin, Date end, TimeSlot timeSlot, Patient patient);

    /**
     * Remove the specified appointment.
     * @param app Appointment to cancel.
     */
    void cancelAppointment(Appointment app);

    /**
     * Modify the specified appointment.
     * @param app TODO
     * @return TODO
     */
    boolean modifyAppointment(Appointment app);
}
