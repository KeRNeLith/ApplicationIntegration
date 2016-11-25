package ejb.face;

import entities.persons.Patient;
import entities.timeslots.Appointment;
import entities.timeslots.TimeSlot;

import java.util.Date;
import java.util.List;

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
     * Get the list of all appointments associated to the given patient id.
     * @param id Patient id.
     * @return List of Appointments.
     */
    List<Appointment> readAllAppointmentsFromPatient(long id);

    /**
     * Remove the specified appointment.
     * @param id Id of the appointment to cancel.
     */
    void cancelAppointment(long id);

    /**
     * Modify the specified appointment.
     * @param app TODO
     * @return TODO
     */
    boolean modifyAppointment(Appointment app);
}
