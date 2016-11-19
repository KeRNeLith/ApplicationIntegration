package ejb.face;

import entities.timeslots.Appointment;

/**
 * Interface for the EJB handling appointments.
 * Created by kernelith on 18/11/16.
 */
public interface AppointmentEJB
{
    Appointment createAppointment(/*TODO*/);

    boolean cancelAppointment(Appointment app);

    boolean modifyAppointment(Appointment app);
}
