package Service;

import java.util.List;

import Audit.AuditService;
import Models.*;

public class PatientService {
    private MedicalOffice medicalOffice = new MedicalOffice();
    private AuditService audit = AuditService.getInstance();

    public PatientService() {
        this.medicalOffice = new MedicalOffice();
    }

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

    
    public List<Patient> findPatientsByName(String name) {
        audit.logAction("S-a cautat pacientul cu numele: " + name);
        return medicalOffice.findPatientsByName(name);
    }
    

}
