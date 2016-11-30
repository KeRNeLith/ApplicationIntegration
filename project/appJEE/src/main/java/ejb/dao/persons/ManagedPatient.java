package ejb.dao.persons;

import ejb.dao.DAOManager;
import ejb.face.AppointmentEJB;
import entities.persons.Patient;
import entities.timeslots.Appointment;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Class that provide implementation of CRUD for Patient entity.
 * Created by kernelith on 16/11/16.
 */
@Stateless
public class ManagedPatient extends DAOManager
{
    /**
     * Appointment EJB (injected).
     */
    @EJB
    private AppointmentEJB m_appointmentEJB;

    /**
     * Create a new managed patient entity.
     * @param name Patient's name.
     * @param surname Patient's surname.
     * @return Created entity.
     */
    public Patient createPatient(String name, String surname)
    {
        Patient patient = null;

        try
        {
            patient = new Patient(name, surname);   // In Memory
            m_manager.persist(patient);             // Managed
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
    public Patient readPatient(long id)
    {
        return readEntity(id, Patient.class);
    }

    /**
     * Get the list of all patients.
     * @return List of patients.
     */
    public List<Patient> readAllPatients() { return getList("Patient.findAll", Patient.class); }

    /**
     * Update data for the given patient.
     * @param patient Patient entity.
     * @return Updated entity.
     */
    public Patient updatePatient(Patient patient)
    {
        return updateEntity(patient);
    }

    /**
     * Delete the entity corresponding to the given id.
     * @param id Entity id.
     * @return True if the operation succeeded otherwise false.
     */
    public boolean deletePatient(long id)
    {
        // Before deleting entity set all appointments associated to the patient to NULL
        // Keep trace of previous appointments made
        List<Appointment> appointments = m_appointmentEJB.readAllAppointmentsFromPatient(id);
        for (Appointment app : appointments)
        {
            app.setPatient(null);
        }

        return deleteEntity(id, Patient.class);
    }
}