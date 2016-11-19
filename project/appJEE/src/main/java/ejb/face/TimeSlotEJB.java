package ejb.face;

import entities.timeslots.TimeSlot;

import java.util.List;

/**
 * Interface for the EJB handling time slots.
 * Created by kernelith on 18/11/16.
 */
public interface TimeSlotEJB
{
    /**
     * Get the list of all available time slots.
     * @return List of time slots.
     */
    List<TimeSlot> getAvailableTimeSlots();
}
