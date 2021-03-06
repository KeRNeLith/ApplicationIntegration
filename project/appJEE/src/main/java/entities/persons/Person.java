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
    private Long m_id;

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
    protected Person()
    {
        this(null, null);
    }

    /**
     * Constructor.
     * @param firstname Person's firstname.
     * @param lastname Person's lastname.
     */
    Person(String firstname, String lastname)
    {
        this.setFirstname(firstname);
        this.setLastname(lastname);
    }

    /**
     * Get the person database id.
     * @return Database id.
     */
    public Long getId()
    {
        return m_id;
    }

    /**
     * Set the person id.
     * @param id new id.
     */
    protected void setId(Long id) { m_id = id; }

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
