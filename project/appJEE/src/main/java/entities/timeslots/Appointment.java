package entities.timeslots;

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
     * Default constructor.
     */
    public Appointment()
    {
        setDate(new Timestamp(new Date().getTime()));
    }

    /**
     * Date of the appointment.
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
}
