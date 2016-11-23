package ejb.implem;

import ejb.dao.DAOManager;
import ejb.face.TimeSlotEJB;
import entities.persons.Doctor;
import entities.timeslots.TimeSlot;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
    public TimeSlot updateTimeSlot(TimeSlot timeSlot)
    {
        return updateEntity(timeSlot);
    }

    @Override
    public void deleteTimeSlot(long id)
    {
        deleteEntity(id, TimeSlot.class);
    }

    @Override
    public List<TimeSlot> getAvailableTimeSlots()
    {
        Query query = m_manager.createNamedQuery("TimeSlot.findAllFollowing");
        query.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        // List of time slots
        List<TimeSlot> timeSlots = query.getResultList();

        // Collect time slots that have free slots
        List<TimeSlot> availableTimeSlots = timeSlots.stream().filter(timeSlot -> !timeSlot.isFull()).collect(Collectors.toList());

        return availableTimeSlots;
    }
}
