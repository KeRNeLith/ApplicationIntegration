package ejb.dao.persons;

import ejb.dao.DAOManager;
import ejb.face.TimeSlotEJB;
import entities.persons.Doctor;
import entities.timeslots.TimeSlot;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.StringJoiner;

/**
 * Class that provide implementation of CRUD for Doctor entity.
 * Created by kernelith on 16/11/16.
 */
@Stateless
public class ManagedDoctor extends DAOManager
{
    /**
     * Time slot EJB (injected).
     */
    @EJB
    private TimeSlotEJB m_timeSlotEJB;

    /**
     * Create a new managed doctor entity.
     * @param name Doctor's name.
     * @param surname Doctor's surname.
     * @return Created entity.
     */
    public Doctor createDoctor(String name, String surname)
    {
        Doctor doctor = null;

        try
        {
            doctor = new Doctor(name, surname); // In Memory
            m_manager.persist(doctor);          // Managed
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

    /**
     * Get the list of all doctors.
     * @return List of doctors.
     */
    public List<Doctor> readAllDoctors() { return getList("Doctor.findAll"); }

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
     * @return True if the operation succeeded, otherwise false.
     */
    public boolean deleteDoctor(long id)
    {
        // Before deleting entity set time slots associated to the doctor to NULL (prevent constraint violation)
        // Keep doctor's previous time slots for history
        List<TimeSlot> timeSlots = m_timeSlotEJB.readAllTimeSlotsFromDoctor(id);
        for (TimeSlot ts : timeSlots)
        {
            ts.setDoctor(null);
        }

        // Delete entity
        return deleteEntity(id, Doctor.class);
    }
}