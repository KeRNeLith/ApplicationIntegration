package ejb.implem;

import ejb.dao.DAOManager;
import ejb.face.AppointmentEJB;
import ejb.face.TimeSlotEJB;
import entities.persons.Doctor;
import entities.persons.Patient;
import entities.timeslots.Appointment;
import entities.timeslots.TimeSlot;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Implementation for appointments EJB.
 * Created by ace_nanter on 20/11/16.
 */
@Stateless
public class AppointmentEJBImplem extends DAOManager implements AppointmentEJB
{
    /**
     * Time Slot EJB (injected).
     */
    @EJB
    private TimeSlotEJB m_timeSlotEJB;

    @Override
    public Appointment createAppointment(Date begin, Date end, TimeSlot timeSlot, Patient patient)
    {
        Appointment appointment = timeSlot.addAppointment(begin, end, patient);

        if (appointment != null)
        {
            try
            {
                m_manager.persist(appointment);
                m_timeSlotEJB.updateTimeSlot(timeSlot); // Update time slot with the appointment
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return (appointment);
    }

    @Override
    public List<Appointment> readAllAppointmentsFromPatient(long id)
    {
        return getList("Appointment.findAllForPatient", id);
    }

    @Override
    public boolean cancelAppointment(long id)
    {
        boolean ret = false;

        Appointment app = readEntity(id, Appointment.class);

        if (app != null)
        {
            TimeSlot containingTimeSlot = app.getTimeSlot();

            // Update time slot => remove appointment
            containingTimeSlot.removeAppointment(id);
            m_timeSlotEJB.updateTimeSlot(containingTimeSlot);

            ret = deleteEntity(id, Appointment.class);
        }

        return ret;
    }

    @Override
    public boolean modifyAppointment(Appointment app)
    {
        // TODO
        return false;
    }
}
