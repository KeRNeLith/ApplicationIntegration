package ejb.implem;

import ejb.dao.DAOManager;
import ejb.face.AppointmentEJB;
import ejb.face.TimeSlotEJB;
import entities.persons.Doctor;
import entities.timeslots.Appointment;
import entities.timeslots.TimeSlot;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Time slot EJB.
 * Created by kernelith on 18/11/16.
 */
@Stateless
public class TimeSlotEJBImplem extends DAOManager implements TimeSlotEJB
{
    /**
     * Appointment EJB (injected).
     */
    @EJB
    private AppointmentEJB m_appointmentEJB;

    @Override
    public TimeSlot createTimeSlot(Date begin, Date end, Doctor doctor)
    {
        TimeSlot timeSlot = null;

        try
        {
            timeSlot = new TimeSlot(begin, end, doctor);       // In Memory
            m_manager.persist(timeSlot);      // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (timeSlot);
    }

    @Override
    public TimeSlot readTimeSlot(long id)
    {
        return readEntity(id, TimeSlot.class);
    }

    @Override
    public List<TimeSlot> readAllTimeSlotsFromDoctor(long id)
    {
        return getList("TimeSlot.findAllForDoctor", TimeSlot.class, id);
    }

    @Override
    public List<TimeSlot> readAllTimeSlotsBetween(Date begin, Date end)
    {
        return getList("TimeSlot.findAllFollowingBetween", TimeSlot.class, begin, end);
    }

    @Override
    public TimeSlot updateTimeSlot(TimeSlot timeSlot)
    {
        return updateEntity(timeSlot);
    }

    @Override
    public boolean deleteTimeSlot(long id)
    {
        boolean ret = false;

        // Delete all appointment from the time slot => they are cancelled
        TimeSlot timeSlot = readTimeSlot(id);
        if (timeSlot != null)
        {
            // Use of a classical loop to avoid concurrent modification
            for (int i = 0 ; i < timeSlot.getAppointments().size() ; ++i)
            {
                Appointment appointment = timeSlot.getAppointments().get(i);
                m_appointmentEJB.cancelAppointment(appointment.getId());
            }

            ret = deleteEntity(id, TimeSlot.class);
        }

        return ret;
    }

    @Override
    public List<TimeSlot> getAvailableTimeSlots()
    {
        // List of time slots
        List<TimeSlot> timeSlots = getList("TimeSlot.findAllFollowing", TimeSlot.class, new Date());

        // Collect time slots that have free slots
        return timeSlots.stream().filter(timeSlot -> !timeSlot.isFull()).collect(Collectors.toList());
    }
}
