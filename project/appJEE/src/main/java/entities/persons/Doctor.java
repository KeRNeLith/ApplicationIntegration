package entities.persons;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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
     * Default constructor.
     */
    public Doctor()
    {
        super();
    }
}
