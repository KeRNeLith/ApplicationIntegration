package dao.persons;

import dao.DAOManager;
import entities.persons.Doctor;

import javax.ejb.Stateless;

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
            doctor = new Doctor(); // In Memory

            m_manager.persist(doctor);   // Managed
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
    public Doctor readDoctor(int id)
    {
        return readEntity(id, Doctor.class);
    }

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