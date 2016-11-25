package entities.timeslots;

import entities.persons.Doctor;
import entities.persons.Patient;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that represent a time slot.
 * Created by kernelith on 13/11/16.
 */
@NamedQueries(
{
    @NamedQuery(
                name="TimeSlot.findAllFollowing",
                query="SELECT ts FROM TimeSlot ts WHERE ts.m_end >= ?1 AND ts.m_doctor IS NOT NULL"),
    @NamedQuery(
                name="TimeSlot.findAllForDoctor",
                query="SELECT ts FROM TimeSlot ts WHERE ts.m_doctor.m_id = ?1")
})
@Entity
@Table(name = "TimeSlot")
public class TimeSlot extends TimeInterval
{
    /**
     * List of appointments included in the time slot.
     */
    @OneToMany(mappedBy = "m_timeSlot")
    private List<Appointment> m_appointments;

    /**
     * Doctor providing the time slot.
     */
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor m_doctor;

    /**
     * List of remaining available slots.
     */
    @Transient
    private List<TimeInterval> m_freeSlots;

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
        super(begin, end);
        this.m_appointments = new ArrayList<>();
        setDoctor(doctor);
        this.m_freeSlots = null;
    }

    // Member functions
    /**
     * Check if the time slot is full.
     * @return True if there is no available slot.
     */
    @JsonIgnore
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
        if (m_freeSlots == null)
        {
            computeFreeSlots();
        }

        return m_freeSlots;
    }

    /**
     * Compute all free slots remaining in the time slot.
     */
    private void computeFreeSlots()
    {
        // Sort appointment ordered by beginning date
        Collections.sort(m_appointments, (app1, app2) -> app1.getBegin().compareTo(app2.getBegin()));

        // Initial free slot (= entire time slot [m_begin, m_end])
        m_freeSlots = new ArrayList<>();
        m_freeSlots.add(new TimeInterval(m_begin, m_end));

        // Loop on all busy time slots
        for (Appointment app : m_appointments)
        {
            // Appointment is not considered when the patient is not affected
            // But this is to keep a trace of appointments even if the patient has been deleted.
            if (app.getPatient() != null)
            {
                m_freeSlots = occupSlot(m_freeSlots, app);
            }
        }
    }

    /**
     * Based on a list of free slots compute the remaining free slots after adding the given appointment.
     * @param freeSlots Free slots at the beginning.
     * @param appointment Appointment to add.
     * @return New list of free slots after adding the appointment.
     */
    private List<TimeInterval> occupSlot(List<TimeInterval> freeSlots, Appointment appointment)
    {
        List<TimeInterval> result = new ArrayList<>();

        // Appointment begin and end date
        Date appointmentBegin = appointment.getBegin();
        Date appointmentEnd = appointment.getEnd();

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
            else if (appointmentBegin.before(freeSlot.getEnd()))
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

    /**
     * Add if possible the appointment to the current time slot.
     * @param begin Beginning date of the appointment.
     * @param end Ending date of the appointment.
     * @param patient Patient concerned.
     * @return Appointment instance created if possible otherwise null.
     */
    public Appointment addAppointment(Date begin, Date end, Patient patient)
    {
        Appointment app = null;

        // Check if the given time slot can receive the appointment
        Optional<TimeInterval> availableSlot = getAvailableSlots()  .stream()
                                                                    .filter(tm -> !begin.before(tm.getBegin())
                                                                                && end.before(tm.getEnd()))
                                                                    .findFirst();

        // At least one time interval available
        if (availableSlot.isPresent())
        {
            app = new Appointment(begin, end, this, patient);
            m_appointments.add(app);

            m_freeSlots = null; // For lazy computing => will compute free slots only on next getAvailableSlots call
        }

        return app;
    }

    /**
     * Remove the appointment matching the given id.
     * @param id Appointment id.
     */
    public void removeAppointment(long id)
    {
        boolean supp = m_appointments.removeIf(a -> a.getId() == id);

        if (supp)
        {
            m_freeSlots = null; // For lazy computing => will compute free slots only on next getAvailableSlots call
        }
    }

    // Accessors // Setters
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
    @JsonIgnore
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
