package ejb.implem;

import ejb.face.AppointmentEJB;
import entities.timeslots.Appointment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation for appointments EJB.
 * Created by ace_nanter on 20/11/16.
 */
public class AppointmentEJBImplem  implements AppointmentEJB
{
    /**
     * Entity manager (injected).
     */
    @PersistenceContext
    protected EntityManager m_manager;


    @Override
    public Appointment createAppointment()
    {
        Appointment appointment = null;

        try
        {
            appointment = new Appointment();
            m_manager.persist(appointment);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return(appointment);
    }

    @Override
    public boolean cancelAppointment(Appointment app) {
        Appointment appointment = null;
        try
        {
            appointment = m_manager.find(Appointment.class, app.getId());
            m_manager.remove(appointment);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean modifyAppointment(Appointment app) {
        return false;
    }
}
