package ejb.face;

import entities.persons.Doctor;
import entities.timeslots.TimeSlot;

import java.util.Date;
import java.util.List;

/**
 * Interface for the EJB handling time slots.
 * Created by kernelith on 18/11/16.
 */
public interface TimeSlotEJB
{
    /**
     * Create a TimeSlot entity.
     * @param begin Beginning date of the time slot.
     * @param end Ending date of the time slot.
     * @param doctor Doctor providing the time slot.
     */
    TimeSlot createTimeSlot(Date begin, Date end, Doctor doctor);

    /**
     * Get a TimeSlot entity from persistence context.
     * @param id Database time slot id.
     * @return TimeSlot entity.
     */
    TimeSlot readTimeSlot(long id);

    /**
     * Get the list of all time slots associated to the given doctor id.
     * @param id Doctor id.
     * @return List of TimeSlot.
     */
    List<TimeSlot> readAllTimeSlotsFromDoctor(long id);

    /**
     * Get the list of all time slots where the beginning date is after "begin" and end date is before "end".
     * @param begin Begin date.
     * @param end End date.
     * @return List of TimeSlot.
     */
    List<TimeSlot> readAllTimeSlotsBetween(Date begin, Date end);

    /**
     * Update data for the given time slot.
     * @param timeSlot TimeSlot entity.
     * @return Updated entity.
     */
    TimeSlot updateTimeSlot(TimeSlot timeSlot);

    /**
     * Delete the entity corresponding to the given id.
     * @param id Entity id.
     * @return True if the operation succeeded, otherwise false.
     */
    boolean deleteTimeSlot(long id);

    /**
     * Get the list of all available time slots.
     * @return List of time slots.
     */
    List<TimeSlot> getAvailableTimeSlots();
}
