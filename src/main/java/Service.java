import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private MedicalOffice medicalOffice;

    private AuditService audit = AuditService.getInstance();

    public Service() {
        this.medicalOffice = new MedicalOffice();
    }

    // 1. CRUD for Patient
    public void addPatient(Patient patient) {
        audit.logAction("addPatient");
        medicalOffice.addPatient(patient);
    }

    public List<Patient> getAllPatients() {
        audit.logAction("getAllPatients");
        return medicalOffice.getPatientList();
    }

    public Patient getPatientByCNP(String cnp) {
        audit.logAction("getPatientByCNP");
        return medicalOffice.getPatientByCNP(cnp);
    }

    public void updatePatient(Patient updatedPatient) {
        audit.logAction("updatePatient");
        medicalOffice.updatePatient(updatedPatient);
    }

    public void deletePatient(String cnp) {
        audit.logAction("deletePatient");
        medicalOffice.deletePatient(cnp);
    }

    // 2. CRUD for Doctor
    public void addDoctor(Doctor doctor) {
        audit.logAction("addDoctor");
        medicalOffice.addDoctor(doctor);
    }

    public List<Doctor> getAllDoctors() {
        audit.logAction("getAllDoctors");
        return medicalOffice.getDoctorList();
    }

    public Doctor getDoctorByCNP(String cnp) {
        audit.logAction("getDoctorByCNP");
        return medicalOffice.getDoctorByCNP(cnp);
    }

    public void updateDoctor(Doctor updatedDoctor) {
        audit.logAction("updateDoctor");
        medicalOffice.updateDoctor(updatedDoctor);
    }

    public void deleteDoctor(String cnp) {
        audit.logAction("deleteDoctor");
        medicalOffice.deleteDoctor(cnp);
    }

    // 3. Show the history of a patient
    public List<Consultation> getPatientHistory(String patientCNP) {
        audit.logAction("getPatientHistory");
        List<Consultation> history = new ArrayList<>();
        List<Appointment> appointments = medicalOffice.getAppointmentsByPatientAndStatus(patientCNP, "performed");
        for (Appointment appt : appointments) {
            Consultation cons = new Consultation(appt.getDoctor(), appt.getPatient(), appt.getDateTime());
            history.add(cons);
        }
        return history;
    }

    // 4. Make an appointment for a consultation
    public Appointment makeAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        audit.logAction("makeAppointment");
        return medicalOffice.makeAppointment(patientCNP, doctorCNP, dateTime);
    }

    // 5. Reschedule an appointment
    public boolean rescheduleAppointment(String patientCNP, String doctorCNP,
                                         LocalDateTime oldDateTime, LocalDateTime newDateTime) {
        audit.logAction("rescheduleAppointment");
        return medicalOffice.rescheduleAppointment(patientCNP, doctorCNP, oldDateTime, newDateTime);
    }

    // 6. Cancel an appointment
    public boolean cancelAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        audit.logAction("cancelAppointment");
        return medicalOffice.cancelAppointment(patientCNP, doctorCNP, dateTime);
    }

    // 7. Show the program for a Doctor for a day
    public List<Appointment> getDoctorDailySchedule(String doctorCNP, LocalDate date) {
        audit.logAction("getDoctorDailySchedule");
        return medicalOffice.getDoctorDailySchedule(doctorCNP, date);
    }

    // 8. Find a specific patient and doctor
    public List<Patient> findPatientsByName(String name) {
        audit.logAction("findPatientsByName");
        return medicalOffice.findPatientsByName(name);
    }
    public List<Doctor> findDoctorsByName(String name) {
        audit.logAction("findDoctorsByName");
        return medicalOffice.findDoctorsByName(name);
    }
}