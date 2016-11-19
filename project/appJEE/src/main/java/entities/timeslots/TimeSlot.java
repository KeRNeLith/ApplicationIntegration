package entities.timeslots;

import entities.persons.Doctor;
import utils.TimeInterval;

import javax.persistence.*;
import java.util.*;

/**
 * Class that represent a time slot.
 * Created by kernelith on 13/11/16.
 */
@NamedQueries(
{
    @NamedQuery(
                name="TimeSlot.findAll",
                query="select timeslot from TimeSlot timeslot")
})
@Entity
@Table(name = "TimeSlot")
public class TimeSlot
{
    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "timeSlotId")
    private Long m_id;

    /**
     * Beginning date of the time slot.
     */
    @Column(name = "begin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date m_begin;

    /**
     * Ending date of the time slot.
     */
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date m_end;

    /**
     * List of appointments included in the time slot.
     */
    @OneToMany
    private List<Appointment> m_appointments;

    /**
     * Doctor providing the time slot.
     */
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor m_doctor;

    /**
     * Default constructor.
     */
    public TimeSlot()
    {
        this(new Date(), new Date(), new Doctor());
    }

    /**
     * Constructor.
     * @param begin Beginning date.
     * @param end Ending date.
     * @param doctor Concerned doctor.
     */
    public TimeSlot(Date begin, Date end, Doctor doctor)
    {
        setBegin(begin);
        setEnd(end);
        this.m_appointments = new ArrayList<>();
        setDoctor(doctor);
    }

    // Member functions
    /**
     * Check if the time slot is full.
     * @return True if there is no available slot.
     */
    public boolean isFull()
    {
        boolean ret = false;

        if (getAvailableSlots().size() <= 0)
        {
            ret = true;
        }

        return ret;
    }

    /**
     * Get the list of all available slot remaining in the current time slot.
     * @return List of free time interval.
     */
    public List<TimeInterval> getAvailableSlots()
    {
        // Sort appointment ordered by beginning date
        Collections.sort(m_appointments, (app1, app2) -> app1.getDate().compareTo(app2.getDate()));

        // Initial free slot (= entire time slot [m_begin, m_end])
        List<TimeInterval> freeSlots = new ArrayList<>();
        freeSlots.add(new TimeInterval(m_begin, m_end));

        // Loop on all busy time slots
        for (Appointment app : m_appointments)
        {
            freeSlots = occupSlot(freeSlots, app);
        }

        return freeSlots;
    }

    /**
     * Based on a list of free slots compute the remaining free slots after adding the given appointment.
     * @param freeSlots Free slots at the beginning.
     * @param appointment Appointment to add.
     * @return New list of free slots after adding the appointment.
     */
    public List<TimeInterval> occupSlot(List<TimeInterval> freeSlots, Appointment appointment)
    {
        List<TimeInterval> result = new ArrayList<>();

        // Appointment begin date
        Date appointmentBegin = appointment.getDate();

        // Appointment ending date
        Calendar cal = Calendar.getInstance();
        cal.setTime(appointmentBegin);
        cal.add(Calendar.MINUTE, appointment.getDuration());
        Date appointmentEnd = cal.getTime();

        // For each free slot
        for (TimeInterval freeSlot : freeSlots)
        {
            // If appointment date is before or equal the free slot beginning (<=)
            if (!appointmentBegin.after(freeSlot.getBegin()))
            {
                // If appointment ends before free slot begin
                if (appointmentEnd.before(freeSlot.getBegin()))
                {
                    result.add(freeSlot);
                }
                // If appointment ends before free slot end
                else if (appointmentEnd.before(freeSlot.getEnd()))
                {
                    result.add(new TimeInterval(appointmentEnd, freeSlot.getEnd()));
                }
                else
                {
                    // The whole slot is busy
                }
            }
            // If appointment date is strictly before the free slot beginning (<)
            else if (appointmentBegin.before(freeSlot.getBegin()))
            {
                // If appointment ends before free slot end
                if (appointmentEnd.before(freeSlot.getEnd()))
                {
                    // Split free slot in two part excluded the time need for the appointment
                    result.add(new TimeInterval(freeSlot.getBegin(), appointmentBegin));
                    result.add(new TimeInterval(appointmentEnd, freeSlot.getEnd()));
                }
                // Add to new free slots list the time between free slot begin and the beginning of the appointment
                else
                {
                    result.add(new TimeInterval(freeSlot.getBegin(), appointmentBegin));
                }
            }
            // Else appointment is after the free slot beginning date (>)
            else
            {
                result.add(freeSlot);
            }
        }

        return result;
    }

    // Accessors // Setters
    /**
     * Get the time slot database id.
     * @return Database id.
     */
    public Long getId()
    {
        return m_id;
    }

    /**
     * Get the beginning date of the time slot.
     * @return Beginning date of the time slot.
     */
    public Date getBegin()
    {
        return m_begin;
    }

    /**
     * Set the beginning date.
     * @param begin New beginning date.
     */
    public void setBegin(Date begin)
    {
        this.m_begin = begin;
    }

    /**
     * Get the ending date of the time slot.
     * @return Ending date of the time slot.
     */
    public Date getEnd()
    {
        return m_end;
    }

    /**
     * Set the ending date.
     * @param end New ending date.
     */
    public void setEnd(Date end)
    {
        this.m_end = end;
    }

    /**
     * Get the list of appointments included in the time slot.
     * @return List of appointments included in the time slot.
     */
    public List<Appointment> getAppointments()
    {
        return m_appointments;
    }

    /**
     * Get the doctor providing the time slot.
     * @return Doctor providing the time slot.
     */
    public Doctor getDoctor()
    {
        return m_doctor;
    }

    /**
     * Set the doctor providing the time slot.
     * @param doctor New doctor.
     */
    public void setDoctor(Doctor doctor)
    {
        this.m_doctor = doctor;
    }
}
