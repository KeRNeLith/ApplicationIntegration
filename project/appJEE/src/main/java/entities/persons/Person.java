package entities.persons;

import javax.persistence.*;

/**
 * Base class that provide data to represent a person.
 * Created by kernelith on 13/11/16.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "entityType")
@DiscriminatorValue("Person")
@Table(name = "Person")
public abstract class Person
{
    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personId")
    private Long m_id;

    /**
     * Person's first name.
     */
    @Column(name = "firstname")
    private String m_firstname;

    @Column(name = "lastname")
    private String m_lastname;

    /**
     * Default constructor.
     */
    public Person()
    {
        this.setFirstname("Default");
        this.setLastname("Default");
    }

    /**
     * Person's first name.
     */
    public String getFirstname()
    {
        return m_firstname;
    }

    /**
     * Set the first name.
     * @param firstname New firstname.
     */
    public void setFirstname(String firstname)
    {
        this.m_firstname = firstname;
    }

    /**
     * Person's last name.
     */
    public String getLastname()
    {
        return m_lastname;
    }

    /**
     * Set the last name.
     * @param lastname New last name.
     */
    public void setLastname(String lastname)
    {
        this.m_lastname = lastname;
    }
}
