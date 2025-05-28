import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Service {
    private MedicalOffice medicalOffice;

    private AuditService audit = AuditService.getInstance();

    public Service() {
        this.medicalOffice = new MedicalOffice();
    }

    // 1. CRUD for Patient
    public void addPatient(Patient patient) {
        audit.logAction("S-a adaugat pacientul " + patient.getName() + " " + patient.getPrenume());
        medicalOffice.addPatient(patient);
    }

    public List<Patient> getAllPatients() {
        audit.logAction("S-a afisat lista pacientilor");
        return medicalOffice.getPatientList();
    }

    public Patient getPatientByCNP(String cnp) {
        Patient patient = medicalOffice.getPatientByCNP(cnp);
        if (patient != null) {
            audit.logAction("S-a cautat pacientul " + patient.getName() + " " + patient.getPrenume() + " cu CNP " + cnp);
        } else {
            audit.logAction("S-a incercat căutarea unui pacient inexistent cu CNP " + cnp);
        }
        return patient;
    }

    public void updatePatient(Patient updatedPatient) {
        audit.logAction("S-a actualizat pacientul " + updatedPatient.getName() + " " + updatedPatient.getPrenume());
        medicalOffice.updatePatient(updatedPatient);
    }

    public void deletePatient(String cnp) {
        Patient patient = medicalOffice.getPatientByCNP(cnp);
        if (patient != null) {
            audit.logAction("S-a sters pacientul " + patient.getName() + " " + patient.getPrenume());
        } else {
            audit.logAction("S-a incercat stergerea unui pacient inexistent cu CNP " + cnp);
        }
        medicalOffice.deletePatient(cnp);
    }

    // 2. CRUD for Doctor
    public void addDoctor(Doctor doctor) {
        audit.logAction("S-a adaugat doctorul " + doctor.getName() + " " + doctor.getPrenume());
        medicalOffice.addDoctor(doctor);
    }

    public java.util.SortedSet<Doctor> getAllDoctorsSorted() {
        audit.logAction("S-a afisat lista doctorilor");
        return medicalOffice.getAllDoctorsSorted();
    }

    public Doctor getDoctorByCNP(String cnp) {
        Doctor doctor = medicalOffice.getDoctorByCNP(cnp);
        if (doctor != null) {
            audit.logAction("S-a cautat doctorul " + doctor.getName() + " " + doctor.getPrenume() + " cu CNP " + cnp);
        } else {
            audit.logAction("S-a incercat cautarea unui doctor inexistent cu CNP " + cnp);
        }
        return doctor;
    }

    public void updateDoctor(Doctor updatedDoctor) {
        audit.logAction("S-a actualizat doctorul " + updatedDoctor.getName() + " " + updatedDoctor.getPrenume());
        medicalOffice.updateDoctor(updatedDoctor);
    }

    public void deleteDoctor(String cnp) {
        Doctor doctor = medicalOffice.getDoctorByCNP(cnp);
        if (doctor != null) {
            audit.logAction("S-a sters doctorul " + doctor.getName() + " " + doctor.getPrenume());
        } else {
            audit.logAction("S-a incercat stergerea unui doctor inexistent cu CNP " + cnp);
        }
        medicalOffice.deleteDoctor(cnp);
    }

    // 3. Show the history of a patient
    public List<Appointment> getPatientHistory(String patientCNP) {
        Patient patient = medicalOffice.getPatientByCNP(patientCNP);
        if (patient != null) {
            audit.logAction("S-a afisat istoricul pacientului " + patient.getName() + " " + patient.getPrenume());
        } else {
            audit.logAction("S-a incercat afisarea istoricului unui pacient inexistent cu CNP " + patientCNP);
        }
        return medicalOffice.getAppointmentsByPatientAndStatus(patientCNP, "performed");
    }

    // 4. Make an appointment for a consultation
    public Appointment makeAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        Patient patient = medicalOffice.getPatientByCNP(patientCNP);
        Doctor doctor = medicalOffice.getDoctorByCNP(doctorCNP);
        if (patient != null && doctor != null) {
            audit.logAction("S-a programat pacientul " + patient.getName() + " " + patient.getPrenume() +
                    " la doctorul " + doctor.getName() + " " + doctor.getPrenume() +
                    " la data " + dateTime.toString());
        } else {
            audit.logAction("S-a incercat programarea cu date invalide (CNP pacient: " + patientCNP + ", CNP doctor: " + doctorCNP + ")");
        }
        return medicalOffice.makeAppointment(patientCNP, doctorCNP, dateTime);
    }

    // 5. Reschedule an appointment
    public boolean rescheduleAppointment(String patientCNP, String doctorCNP,
                                         LocalDateTime oldDateTime, LocalDateTime newDateTime) {
        Patient patient = medicalOffice.getPatientByCNP(patientCNP);
        Doctor doctor = medicalOffice.getDoctorByCNP(doctorCNP);
        if (patient != null && doctor != null) {
            audit.logAction("S-a reprogramat pacientul " + patient.getName() + " " + patient.getPrenume() +
                    " la doctorul " + doctor.getName() + " " + doctor.getPrenume() +
                    " de la data " + oldDateTime.toString() + " la data " + newDateTime.toString());
        } else {
            audit.logAction("S-a incercat reprogramarea cu date invalide (CNP pacient: " + patientCNP + ", CNP doctor: " + doctorCNP + ")");
        }
        return medicalOffice.rescheduleAppointment(patientCNP, doctorCNP, oldDateTime, newDateTime);
    }

    // 6. Cancel an appointment
    public boolean cancelAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        Patient patient = medicalOffice.getPatientByCNP(patientCNP);
        Doctor doctor = medicalOffice.getDoctorByCNP(doctorCNP);
        if (patient != null && doctor != null) {
            audit.logAction("S-a anulat programarea pacientului " + patient.getName() + " " + patient.getPrenume() +
                    " la doctorul " + doctor.getName() + " " + doctor.getPrenume() +
                    " la data " + dateTime.toString());
        } else {
            audit.logAction("S-a incercat anularea unei programări cu date invalide (CNP pacient: " + patientCNP + ", CNP doctor: " + doctorCNP + ")");
        }
        return medicalOffice.cancelAppointment(patientCNP, doctorCNP, dateTime);
    }

    //Mark appointment as done
    public boolean markAppointmentAsDone(String patientCNP, String doctorCNP, LocalDateTime dateTime, String diagnosis, String prescription) {
        audit.logAction("S-a marcat ca efectuata programarea cu diagnostic si reteta pentru pacientul cu CNP " + patientCNP +
                " la doctorul cu CNP " + doctorCNP + " la data " + dateTime.toString());
        return medicalOffice.markAppointmentAsDone(patientCNP, doctorCNP, dateTime, diagnosis, prescription);
    }

    // 7. Show the program for a Doctor for a day
    public List<Appointment> getDoctorDailySchedule(String doctorCNP, LocalDate date) {
        Doctor doctor = medicalOffice.getDoctorByCNP(doctorCNP);
        if (doctor != null) {
            audit.logAction("S-a afisat programul doctorului " + doctor.getName() + " " + doctor.getPrenume() +
                    " pentru data " + date.toString());
        } else {
            audit.logAction("S-a incercat afișarea programului unui doctor inexistent cu CNP " + doctorCNP);
        }
        return medicalOffice.getDoctorDailySchedule(doctorCNP, date);
    }

    // 8. Find a specific patient and doctor
    public List<Patient> findPatientsByName(String name) {
        audit.logAction("S-a cautat pacientul cu numele: " + name);
        return medicalOffice.findPatientsByName(name);
    }
    public List<Doctor> findDoctorsByName(String name) {
        audit.logAction("S-a cautat doctorul cu numele: " + name);
        return medicalOffice.findDoctorsByName(name);
    }

    //9. Get availabla time for doctor
    public List<LocalDateTime> getAvailableHoursForDoctor(String doctorCNP, LocalDate date) {
        return medicalOffice.getAvailableHoursForDoctor(doctorCNP, date);
    }
}