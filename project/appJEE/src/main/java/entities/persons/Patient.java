package entities.persons;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class that represent a patient.
 * Created by kernelith on 13/11/16.
 */
@Entity
@DiscriminatorValue("Patient")
@Table(name = "Patient")
public class Patient extends Person
{
    /**
     * Default constructor.
     */
    public Patient()
    {
        super();
    }
}