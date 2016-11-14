package entities.timeslots;

import entities.persons.Doctor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that represent a time slot.
 * Created by kernelith on 13/11/16.
 */
@Entity
@Table(name = "TimeSlot")
public class TimeSlot
{
    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "timeSlotId")
    private Long m_id;

    /**
     * Beginning date of the time slot.
     */
    @Column(name = "begin")
    private Timestamp m_begin;

    /**
     * Ending date of the time slot.
     */
    @Column(name = "end")
    private Timestamp m_end;

    /**
     * List of appointments included in the time slot.
     */
    @OneToMany
    private List<Appointment> m_appointments;

    /**
     * Doctor providing the time slot.
     */
    @ManyToOne
    @Column(name = "doctorId")
    private Doctor m_doctor;

    /**
     * Default constructor.
     */
    public TimeSlot()
    {
        Date now = new Date();
        setBegin(new Timestamp(now.getTime()));
        setEnd(new Timestamp(now.getTime()));
        this.m_appointments = new ArrayList<>();
        setDoctor(new Doctor());
    }

    /**
     * Get the beginning date of the time slot.
     * @return Beginning date of the time slot.
     */
    public Timestamp getBegin()
    {
        return m_begin;
    }

    /**
     * Set the beginning date.
     * @param begin New beginning date.
     */
    public void setBegin(Timestamp begin)
    {
        this.m_begin = begin;
    }

    /**
     * Get the ending date of the time slot.
     * @return Ending date of the time slot.
     */
    public Timestamp getEnd()
    {
        return m_end;
    }

    /**
     * Set the ending date.
     * @param end New ending date.
     */
    public void setEnd(Timestamp end)
    {
        this.m_end = end;
    }

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
