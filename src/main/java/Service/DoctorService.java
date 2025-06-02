package Service;


import java.util.List;

import Audit.AuditService;
import Models.*;

public class DoctorService {
    private MedicalOffice medicalOffice = new MedicalOffice();
    private AuditService audit = AuditService.getInstance();

    
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

    public List<Doctor> findDoctorsByName(String name) {
        audit.logAction("S-a cautat doctorul cu numele: " + name);
        return medicalOffice.findDoctorsByName(name);
    }

}
