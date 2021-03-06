package entities.persons;

import entities.timeslots.Appointment;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a patient.
 * Created by kernelith on 13/11/16.
 */
@NamedQueries(
{
    @NamedQuery(
            name="Patient.findAll",
            query="select patient from Patient patient")
})
@Entity
@DiscriminatorValue("Patient")
@Table(name = "Patient")
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

    public boolean removeAppointment(long id)
    {
        return m_appointments.removeIf(a -> a.getId() == id);
    }
}