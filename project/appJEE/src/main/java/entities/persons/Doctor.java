package entities.persons;

import entities.timeslots.TimeSlot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a doctor.
 * Created by kernelith on 13/11/16.
 */
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
        super();
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
