package entities.timeslots;

import javax.persistence.*;
import java.util.Date;

/**
 * Utility class that store an interval of time.
 * Created by kernelith on 19/11/16.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "entityType")
@DiscriminatorValue("TimeInterval")
@Table(name = "TimeInterval")
public class TimeInterval
{
    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "timeIntervalId")
    private Long m_id;

    /**
     * Interval's beginning date.
     */
    @Column(name = "begin")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date m_begin;

    /**
     * Interval's ending date.
     */
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date m_end;

    /**
     * Default Constructor.
     */
    TimeInterval()
    {
        this(new Date(), new Date());
    }

    /**
     * Constructor.
     * @param begin Beginning date.
     * @param end Ending date.
     */
    TimeInterval(Date begin, Date end)
    {
        setBegin(begin);
        setEnd(end);
    }

    // Accessors // Setters
    /**
     * Get the time slot database id.
     * @return Database id.
     */
    public Long getId()
    {
        return m_id;
    }

    /**
     * Get the beginning date.
     * @return Beginning date.
     */
    public Date getBegin()
    {
        return m_begin;
    }

    /**
     * Set the beginning date.
     * @param begin New beginning date.
     */
    public void setBegin(Date begin)
    {
        this.m_begin = begin;
    }

    /**
     * Get the ending date.
     * @return Ending date.
     */
    public Date getEnd()
    {
        return m_end;
    }

    /**
     * Set the ending date.
     * @param end New ending date.
     */
    public void setEnd(Date end)
    {
        this.m_end = end;
    }
}
