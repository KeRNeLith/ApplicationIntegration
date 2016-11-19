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
public class Appointment
{
    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointmentId")
    private Long m_id;

    /**
     * Date of the appointment.
     */
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date m_date;

    /**
     * Duration of the appointment in minutes.
     */
    @Column(name = "duration")
    private int m_duration;

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
        this(new Date(), 1, new Patient());
    }

    /**
     * Constructor.
     * @param date Date of the appointment.
     * @param duration Duration of the appointment.
     * @param patient Concerned patient.
     */
    public Appointment(Date date, int duration, Patient patient)
    {
        setDate(date);
        setDuration(duration);
        setPatient(patient);
    }

    /**
     * Get the appointment database id.
     * @return Database id.
     */
    public Long getId()
    {
        return m_id;
    }

    /**
     * Get the date of the appointment.
     * @return Date of the appointment.
     */
    public Date getDate()
    {
        return m_date;
    }

    /**
     * Set the date of the appointment.
     * @param date New appointment date.
     */
    public void setDate(Date date)
    {
        this.m_date = date;
    }

    /**
     * Get the duration of the appointment.
     * @return Duration of the appointment.
     */
    public int getDuration()
    {
        return m_duration;
    }

    /**
     * Set the date of the appointment.
     * @param duration New appointment date.
     */
    public void setDuration(int duration)
    {
        this.m_duration = duration;
    }


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
}
