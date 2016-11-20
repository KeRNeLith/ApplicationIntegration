package entities.persons;

import entities.timeslots.TimeSlot;

import javax.persistence.*;
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
@XmlRootElement
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
        super();
        this.m_timeSlots = new ArrayList<>();
    }

    /**
     * Copy constructor.
     * @param doc Doctor to copy.
     */
    public Doctor(Doctor doc)
    {
        super(doc);
        this.m_timeSlots = doc.getTimeSlots();
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
