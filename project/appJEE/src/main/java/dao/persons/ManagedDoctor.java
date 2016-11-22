package dao.persons;

import dao.DAOManager;
import entities.persons.Doctor;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Class that provide implementation of CRUD for Doctor entity.
 * Created by kernelith on 16/11/16.
 */
@Stateless
public class ManagedDoctor extends DAOManager
{
    /**
     * Create a new managed doctor entity.
     * @return Created entity.
     */
    public Doctor createDoctor()
    {
        Doctor doctor = null;

        try
        {
            doctor = new Doctor();      // In Memory
            m_manager.persist(doctor);  // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (doctor);
    }

    /**
     * Create a new managed doctor entity by copying.
     * @param doc Doctor to copy.
     * @return Created entity.
     */
    public Doctor createDoctor(Doctor doc)
    {
        Doctor doctor = null;

        try
        {
            doctor = new Doctor(doc);       // In Memory
            m_manager.persist(doctor);      // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (doctor);
    }

    /**
     * Get a doctor entity from persistence context.
     * @param id Database doctor id.
     * @return Doctor entity.
     */
    public Doctor readDoctor(long id)
    {
        return readEntity(id, Doctor.class);
    }

    public List<Doctor> getList() { return getList(Doctor.class, "Doctor.findAll"); }

    /**
     * Update data for the given doctor.
     * @param doctor Doctor entity.
     * @return Updated entity.
     */
    public Doctor updateDoctor(Doctor doctor)
    {
        return updateEntity(doctor);
    }

    /**
     * Delete the entity corresponding to the given id.
     * @param id Entity id.
     */
    public void deleteDoctor(int id)
    {
        deleteEntity(id, Doctor.class);
    }
}