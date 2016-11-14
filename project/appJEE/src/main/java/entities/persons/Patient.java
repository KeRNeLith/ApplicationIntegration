package entities.persons;

import entities.timeslots.Appointment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
     * List of patient's appointments.
     */
    @OneToMany(mappedBy = "m_patient")
    private List<Appointment> m_appointments;

    /**
     * Default constructor.
     */
    public Patient()
    {
        super();
        this.m_appointments = new ArrayList<>();
    }

    /**
     * Get the list of patient's appointments.
     * @return List of patient's appointments.
     */
    public List<Appointment> getAppointments()
    {
        return m_appointments;
    }
}