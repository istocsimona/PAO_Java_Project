package Models;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person implements Comparable<Doctor>{
    private String specialty;
    private List<Appointment> appointments;


    @Override
    public int compareTo(Doctor other) {
        int cmp = this.getName().compareToIgnoreCase(other.getName());
        if (cmp == 0) {
            cmp = this.getPrenume().compareToIgnoreCase(other.getPrenume());
        }
        if (cmp == 0) {
            cmp = this.getCNP().compareTo(other.getCNP());
        }
        return cmp;
    }

    public Doctor() {
        this.appointments = new ArrayList<>();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) {
        if (this.appointments == null) {
            this.appointments = new ArrayList<>();
        }
        this.appointments.add(appointment);
    }

    @Override
    public String toString() {
        return  name + " " + prenume + ", " +
                "CNP= " + CNP + ", " +
                "specialy= " + specialty;
    }
}