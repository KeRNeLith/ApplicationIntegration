package entities.persons;

import entities.timeslots.TimeSlot;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a doctor.
 * Created by kernelith on 13/11/16.
 */
@NamedQueries(
{
    @NamedQuery(
            name="Doctor.findAll",
            query="select doctor from Doctor doctor")
})
@Entity
@DiscriminatorValue("Doctor")
@Table(name = "Doctor")
public class Doctor extends Person
{
    /**
     * List of time slots provided by the doctor.
     */
    @OneToMany(mappedBy = "m_doctor")
    private List<TimeSlot> m_timeSlots;

    /**
     * Default constructor.
     */
    public Doctor()
    {
        this(null, null);
    }

    /**
     * Constructor.
     * @param firstname Doctor's firstname.
     * @param lastname Doctor's lastname.
     */
    public Doctor(String firstname, String lastname)
    {
        super(firstname, lastname);
        this.m_timeSlots = new ArrayList<>();
    }

    /**
     * Get the list of time slots provided by the doctor.
     * @return List of time slots provided by the doctor.
     */
    public List<TimeSlot> getTimeSlots()
    {
        return m_timeSlots;
    }
}
