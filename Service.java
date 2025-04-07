import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Service {
    private MedicalOffice medicalOffice;

    public Service() {
        this.medicalOffice = new MedicalOffice();
    }




    // 1. CRUD for Patient
    public void addPatient(Patient patient) {
        medicalOffice.addPatient(patient);
    }

    public List<Patient> getAllPatients() {
        return medicalOffice.getPatientList();
    }

    public Patient getPatientByCNP(String cnp) {
        return medicalOffice.getPatientByCNP(cnp);
    }

    public void updatePatient(Patient updatedPatient) {
        medicalOffice.updatePatient(updatedPatient);
    }

    public void deletePatient(String cnp) {
        medicalOffice.deletePatient(cnp);
    }




    // 2. CRUD for Doctor
    public void addDoctor(Doctor doctor) {
        medicalOffice.addDoctor(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return medicalOffice.getDoctorList();
    }

    public Doctor getDoctorByCNP(String cnp) {
        return medicalOffice.getDoctorByCNP(cnp);
    }

    public void updateDoctor(Doctor updatedDoctor) {
        medicalOffice.updateDoctor(updatedDoctor);
    }

    public void deleteDoctor(String cnp) {
        medicalOffice.deleteDoctor(cnp);
    }



    // 3. Show the history of a patient
    public List<Consultation> getPatientHistory(String patientCNP) {
        Patient patient = medicalOffice.getPatientByCNP(patientCNP);
        if (patient != null) {
            return patient.getConsultationHistory();
        }
        return null;
    }




    // 4. Make an appointment for a consultation
    public Appointment makeAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        return medicalOffice.makeAppointment(patientCNP, doctorCNP, dateTime);
    }




    // 5. Reschedule an appointment
    public boolean rescheduleAppointment(String patientCNP, String doctorCNP,
                                         LocalDateTime oldDateTime, LocalDateTime newDateTime) {
        return medicalOffice.rescheduleAppointment(patientCNP, doctorCNP, oldDateTime, newDateTime);
    }




    // 6. Cancel an appointment
    public boolean cancelAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        return medicalOffice.cancelAppointment(patientCNP, doctorCNP, dateTime);
    }




    // 7. Show the program for a Doctor for a day
    public List<Appointment> getDoctorDailySchedule(String doctorCNP, LocalDate date) {
        return medicalOffice.getDoctorDailySchedule(doctorCNP, date);
    }




    // 8. Find a specific patient
    public List<Patient> findPatientsByName(String name) {
        return medicalOffice.findPatientsByName(name);
    }
}
