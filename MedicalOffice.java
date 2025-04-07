import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicalOffice {
    private List<Doctor> doctorList;
    private List<Patient> patientList;
    private List<Appointment> appointmentList;

    public MedicalOffice() {
        this.doctorList = new ArrayList<>();
        this.patientList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    // Patient operations
    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    public Patient getPatientByCNP(String cnp) {
        return patientList.stream()
                .filter(patient -> patient.getCNP().equals(cnp))
                .findFirst()
                .orElse(null);
    }

    public void updatePatient(Patient updatedPatient) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getCNP().equals(updatedPatient.getCNP())) {
                patientList.set(i, updatedPatient);
                return;
            }
        }
    }

    public void deletePatient(String cnp) {
        patientList.removeIf(patient -> patient.getCNP().equals(cnp));
    }

    public List<Patient> findPatientsByName(String name) {
        return patientList.stream()
                .filter(patient -> patient.getName().equalsIgnoreCase(name) ||
                        patient.getPrenume().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Doctor operations
    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

    public Doctor getDoctorByCNP(String cnp) {
        return doctorList.stream()
                .filter(doctor -> doctor.getCNP().equals(cnp))
                .findFirst()
                .orElse(null);
    }

    public void updateDoctor(Doctor updatedDoctor) {
        for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getCNP().equals(updatedDoctor.getCNP())) {
                doctorList.set(i, updatedDoctor);
                return;
            }
        }
    }

    public void deleteDoctor(String cnp) {
        doctorList.removeIf(doctor -> doctor.getCNP().equals(cnp));
    }

    // Appointment operations
    public Appointment makeAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        Patient patient = getPatientByCNP(patientCNP);
        Doctor doctor = getDoctorByCNP(doctorCNP);

        if (patient == null || doctor == null) {
            return null;
        }

        // Check if doctor is available at this time
        if (!isDoctorAvailable(doctorCNP, dateTime)) {
            return null;
        }

        Appointment appointment = new Appointment(patient, doctor, dateTime);

        // Add to medical office list
        appointmentList.add(appointment);

        // Add to doctor's appointments
        doctor.addAppointment(appointment);

        return appointment;
    }

    public boolean rescheduleAppointment(String patientCNP, String doctorCNP,
                                         LocalDateTime oldDateTime, LocalDateTime newDateTime) {
        Appointment appointment = findAppointment(patientCNP, doctorCNP, oldDateTime);

        if (appointment == null || !appointment.getStatus().equals("scheduled")) {
            return false;
        }

        // Check if new time is available for doctor
        if (!isDoctorAvailable(doctorCNP, newDateTime)) {
            return false;
        }

        appointment.reschedule(newDateTime);
        return true;
    }

    public boolean cancelAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        Appointment appointment = findAppointment(patientCNP, doctorCNP, dateTime);

        if (appointment == null || !appointment.getStatus().equals("scheduled")) {
            return false;
        }

        appointment.cancel();
        return true;
    }

    public List<Appointment> getDoctorDailySchedule(String doctorCNP, LocalDate date) {
        Doctor doctor = getDoctorByCNP(doctorCNP);

        if (doctor == null || doctor.getAppointments() == null) {
            return new ArrayList<>();
        }

        return doctor.getAppointments().stream()
                .filter(appointment -> appointment.getDateTime().toLocalDate().equals(date))
                .filter(appointment -> appointment.getStatus().equals("scheduled"))
                .collect(Collectors.toList());
    }

    private Appointment findAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        return appointmentList.stream()
                .filter(a -> a.getPatient().getCNP().equals(patientCNP))
                .filter(a -> a.getDoctor().getCNP().equals(doctorCNP))
                .filter(a -> a.getDateTime().equals(dateTime))
                .findFirst()
                .orElse(null);
    }

    private boolean isDoctorAvailable(String doctorCNP, LocalDateTime dateTime) {
        Doctor doctor = getDoctorByCNP(doctorCNP);

        if (doctor == null || doctor.getAppointments() == null) {
            return true;
        }

        return doctor.getAppointments().stream()
                .filter(a -> a.getStatus().equals("scheduled"))
                .noneMatch(a -> a.getDateTime().equals(dateTime));
    }
}