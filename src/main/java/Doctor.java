import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    private String specialty;
    private List<Appointment> appointments;

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
        return "Doctor{" +
                "name='" + name + '\'' +
                ", prenume='" + prenume + '\'' +
                ", CNP='" + CNP + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}