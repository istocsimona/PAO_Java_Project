package Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import Audit.AuditService;
import Models.*;

public class AppointmentService {
    private MedicalOffice medicalOffice = new MedicalOffice();
    private AuditService audit = AuditService.getInstance();


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

    // 7. Show the program for a Models.Doctor for a day
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

    

    //9. Get availabla time for doctor
    public List<LocalDateTime> getAvailableHoursForDoctor(String doctorCNP, LocalDate date) {
        return medicalOffice.getAvailableHoursForDoctor(doctorCNP, date);
    }
}
