package entities.persons;

import entities.timeslots.Appointment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a patient.
 * Created by kernelith on 13/11/16.
 */
@Entity
@DiscriminatorValue("Patient")
@Table(name = "Patient")
@XmlRootElement
public class Patient extends Person
{
    /**
     * List of patient's appointments.
     */
    @OneToMany(mappedBy = "m_patient")
    private List<Appointment> m_appointments;

    /**
     * Default constructor.
     */
    public Patient()
    {
        this(null, null);
    }

    /**
     * Constructor.
     * @param firstname Doctor's firstname.
     * @param lastname Doctor's lastname.
     */
    public Patient(String firstname, String lastname)
    {
        super(firstname, lastname);
        this.m_appointments = new ArrayList<>();
    }

    /**
     * Get the list of patient's appointments.
     * @return List of patient's appointments.
     */
    public List<Appointment> getAppointments()
    {
        return m_appointments;
    }
}