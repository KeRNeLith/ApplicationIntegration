package ejb.implem;

import ejb.dao.DAOManager;
import ejb.dao.persons.ManagedPatient;
import ejb.face.AppointmentEJB;
import ejb.face.TimeSlotEJB;
import entities.persons.Patient;
import entities.timeslots.Appointment;
import entities.timeslots.TimeSlot;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
     * Patient EJB (injected).
     */
    @EJB
    private ManagedPatient m_patientEJB;

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
    public Appointment readAppointment(long id)
    {
        return readEntity(id, Appointment.class);
    }

    @Override
    public List<Appointment> readAllAppointmentsFromPatient(long id)
    {
        return getList("Appointment.findAllForPatient", Appointment.class, id);
    }

    @Override
    public boolean cancelAppointment(long id)
    {
        boolean ret = false;

        Appointment app = readEntity(id, Appointment.class);

        if (app != null)
        {
            TimeSlot containingTimeSlot = app.getTimeSlot();
            Patient relatedPatient = app.getPatient();

            // Update time slot => remove appointment
            containingTimeSlot.removeAppointment(id);
            m_timeSlotEJB.updateTimeSlot(containingTimeSlot);

            // Update patient => remove appointment
            relatedPatient.removeAppointment(id);
            m_patientEJB.updatePatient(relatedPatient);

            ret = deleteEntity(id, Appointment.class);
        }

        return ret;
    }

    @Override
    public boolean modifyAppointment(long appointmentId, long newPatientId)
    {
        boolean ret = false;

        Appointment app = readEntity(appointmentId, Appointment.class);
        Patient newPatient = m_patientEJB.readPatient(newPatientId);
        if (app != null && newPatient != null)
        {
            app.setPatient(newPatient);

            ret = true;
        }

        return ret;
    }

    @Override
    public boolean modifyAppointment(long id, Date newBegin, Date newEnd)
    {
        boolean ret = false;

        Appointment app = readEntity(id, Appointment.class);
        if (app != null)
        {
            TimeSlot containingTimeSlot = app.getTimeSlot();

            // If update fails => there is not enough free time in the time slot,
            // it means that the appointment should be change of time slot
            if (!containingTimeSlot.updateAppointment(id, newBegin, newEnd))
            {
                // Get the list of possible new time slots
                List<TimeSlot> possibleTimeSlots = m_timeSlotEJB.readAllTimeSlotsBetween(newBegin, newEnd);
                // Suppress the current containing time slot because it is not usable.
                possibleTimeSlots.removeIf(ts -> ts.getId().equals(containingTimeSlot.getId()));

                // Test all possible time slots, stop at the first time slot that fit new dates
                for (TimeSlot ts : possibleTimeSlots)
                {
                    Appointment newApp = ts.addAppointment(newBegin, newEnd, app.getPatient());

                    // New time slot found
                    if (newApp != null)
                    {
                        // Update time slot with the new appointment
                        m_timeSlotEJB.updateTimeSlot(ts);

                        // Remove appointment from previous time slot
                        cancelAppointment(id);

                        ret = true;

                        break;  // Stop search
                    }
                }
            }
            // Update has succeeded
            else
            {
                ret = true;
            }
        }

        return ret;
    }
}
