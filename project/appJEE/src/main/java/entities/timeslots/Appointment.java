package entities.timeslots;

import entities.persons.Patient;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Class that represent an appointment.
 * Created by kernelith on 13/11/16.
 */
@NamedQueries(
{
        @NamedQuery(
                name="Appointment.findAllForPatient",
                query="SELECT app FROM Appointment app WHERE app.m_patient.m_id = ?1")
})
@Entity
@Table(name = "Appointment")
public class Appointment extends TimeInterval
{
    /**
     * Time slot containing the appointment.
     */
    @ManyToOne
    @JoinColumn(name = "timeSlotId")
    private TimeSlot m_timeSlot;

    /**
     * Patient concerned by the appointment.
     */
    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient m_patient;

    /**
     * Default constructor.
     */
    public Appointment()
    {
        this(new Date(), new Date(), new TimeSlot(), new Patient());
    }

    /**
     * Constructor.
     * @param begin Date of the appointment.
     * @param end End date of the appointment.
     * @param timeSlot Time slot containing the appointment.
     * @param patient Concerned patient.
     */
    public Appointment(Date begin, Date end, TimeSlot timeSlot, Patient patient)
    {
        super(begin, end);
        setPatient(patient);
        this.m_timeSlot = timeSlot;
    }

    // Getters // Setters
    /**
     * Get the patient concerned by the appointment.
     * @return Patient concerned by the appointment.
     */
    @JsonIgnore
    public Patient getPatient()
    {
        return m_patient;
    }

    /**
     * Set the patient concerned by the appointment.
     * @param patient New concerned Patient.
     */
    public void setPatient(Patient patient)
    {
        this.m_patient = patient;
    }

    /**
     * Get the time slot containing the appointment.
     * @return Time slot.
     */
    @JsonIgnore
    public TimeSlot getTimeSlot()
    {
        return m_timeSlot;
    }

    /**
     * Set the timeSlot associated to the appointment.
     * @param timeSlot Associated Time slot.
     */
    protected void setTimeSlot(TimeSlot timeSlot) { m_timeSlot = timeSlot; }
}
