import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private String status; // scheduled, canceled, performed

    public Appointment() {
    }

    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = "scheduled";
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void reschedule(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }

    public void cancel() {
        this.status = "canceled";
    }

    public void markAsPerformed() {
        this.status = "performed";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Appointment{" +
                "patient=" + patient.getName() + " " + patient.getPrenume() +
                ", doctor=" + doctor.getName() + " " + doctor.getPrenume() +
                ", dateTime=" + dateTime.format(formatter) +
                ", status='" + status + '\'' +
                '}';
    }
}