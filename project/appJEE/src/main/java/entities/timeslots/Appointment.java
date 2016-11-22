package entities.timeslots;

import entities.persons.Patient;

import javax.persistence.*;
import java.util.Date;

/**
 * Class that represent an appointment.
 * Created by kernelith on 13/11/16.
 */
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
        setTimeSlot(timeSlot);
        setPatient(patient);
    }

    // Getters // Setters
    /**
     * Get the patient concerned by the appointment.
     * @return Patient concerned by the appointment.
     */
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
    public TimeSlot getTimeSlot()
    {
        return m_timeSlot;
    }

    /**
     * Set the time slot containing the appointment.
     * @param timeSlot Time slot containing this appointment.
     */
    public void setTimeSlot(TimeSlot timeSlot)
    {
        this.m_timeSlot = timeSlot;
    }
}
