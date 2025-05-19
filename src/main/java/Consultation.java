import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultation {
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private String diagnosis;
    private Prescription prescription;

    public Consultation() {
    }

    public Consultation(Doctor doctor, Patient patient, LocalDateTime dateTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Consultation{" +
                "doctor=" + doctor.getName() + " " + doctor.getPrenume() +
                ", patient=" + patient.getName() + " " + patient.getPrenume() +
                ", dateTime=" + dateTime.format(formatter) +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}