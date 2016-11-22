package utils;

import java.util.Date;

/**
 * Utility class that store an interval of time.
 * Created by kernelith on 19/11/16.
 */
public class TimeInterval
{
    /**
     * Interval's beginning date.
     */
    private Date m_begin;

    /**
     * Interval's ending date.
     */
    private Date m_end;

    /**
     * Constructor.
     * @param begin Beginning date.
     * @param end Ending date.
     */
    public TimeInterval(Date begin, Date end)
    {
        setBegin(begin);
        setEnd(end);
    }

    // Accessors // Setters

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
