package entities.timeslots;

import entities.persons.Patient;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Timestamp m_date;

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
        setDate(new Timestamp(new Date().getTime()));
        setPatient(new Patient());
    }

    /**
     * Get the date of the appointment.
     * @return Date of the appointment.
     */
    public Timestamp getDate()
    {
        return m_date;
    }

    /**
     * Set the date of the appointment.
     * @param date New appointment date.
     */
    public void setDate(Timestamp date)
    {
        this.m_date = date;
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
