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
     * Get an Appointment entity from persistence context.
     * @param id Database appointment id.
     * @return Appointment entity.
     */
    Appointment readAppointment(long id);

    /**
     * Get the list of all appointments associated to the given patient id.
     * @param id Patient id.
     * @return List of Appointments.
     */
    List<Appointment> readAllAppointmentsFromPatient(long id);

    /**
     * Remove the specified appointment.
     * @param id Id of the appointment to cancel.
     * @return True if the operation succeed, otherwise false.
     */
    boolean cancelAppointment(long id);

    /**
     * Modify the specified appointment by the new patient.
     * @param appointmentId Appointment id.
     * @param newPatientId Patient's id that will replace the current affected patient.
     * @return True if the operation succeed, otherwise false.
     */
    boolean modifyAppointment(long appointmentId, long newPatientId);

    /**
     * Modify the specified appointment by the new patient.
     * @param appointmentId Appointment id.
     * @param newBegin New beginning date wanted for the given appointment.
     * @param newEnd New date wanted for the given appointment.
     * @return True if the operation succeed, otherwise false.
     */
    boolean modifyAppointment(long appointmentId, Date newBegin, Date newEnd);
}
