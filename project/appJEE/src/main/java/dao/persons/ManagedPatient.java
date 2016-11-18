package dao.persons;

import dao.DAOManager;
import entities.persons.Patient;

import javax.ejb.Stateless;

/**
 * Class that provide implementation of CRUD for Patient entity.
 * Created by kernelith on 16/11/16.
 */
@Stateless
public class ManagedPatient extends DAOManager
{
    /**
     * Create a new managed patient entity.
     * @return Created entity.
     */
    public Patient createPatient()
    {
        Patient patient = null;

        try
        {
            patient = new Patient(); // In Memory

            m_manager.persist(patient);   // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (patient);
    }

    /**
     * Get a patient entity from persistence context.
     * @param id Database patient id.
     * @return Patient entity.
     */
    public Patient readPatient(int id)
    {
        return readEntity(id, Patient.class);
    }

    /**
     * Update data for the given patient.
     * @param patient Patient entity.
     * @return Updated entity.
     */
    public Patient updateDoctor(Patient patient)
    {
        return updateEntity(patient);
    }

    /**
     * Delete the entity corresponding to the given id.
     * @param id Entity id.
     */
    public void deletePatient(int id)
    {
        deleteEntity(id, Patient.class);
    }
}