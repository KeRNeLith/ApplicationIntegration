package entities.timeslots;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

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
     * Default constructor.
     */
    public TimeSlot()
    {
        Date now = new Date();
        setBegin(new Timestamp(now.getTime()));
        setEnd(new Timestamp(now.getTime()));
    }

    /**
     * Beginning date of the time slot.
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
     * Ending date of the time slot.
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
}
