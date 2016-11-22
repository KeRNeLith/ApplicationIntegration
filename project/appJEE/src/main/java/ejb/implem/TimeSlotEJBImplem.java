package ejb.implem;

import ejb.face.TimeSlotEJB;
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
public class TimeSlotEJBImplem implements TimeSlotEJB
{
    /**
     * Entity manager (injected).
     */
    @PersistenceContext
    protected EntityManager m_manager;

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
