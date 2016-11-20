package entities.persons;

import javax.persistence.*;

/**
 * Base class that provide data to represent a person.
 * Created by kernelith on 13/11/16.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
    private Integer m_id;

    /**
     * Person's first name.
     */
    @Column(name = "firstname")
    private String m_firstname;

    /**
     * Person's last name.
     */
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
     * Copy constructor.
     * @param p Person to copy.
     */
    public Person(Person p)
    {
        this.setFirstname(p.getFirstname());
        this.setLastname(p.getLastname());
    }

    /**
     * Get the person database id.
     * @return Database id.
     */
    public Integer getId()
    {
        return m_id;
    }

    /**
     * Get the person's first name.
     * @return Person's first name.
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
     * Get the person's last name.
     * @return Person's last name.
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
