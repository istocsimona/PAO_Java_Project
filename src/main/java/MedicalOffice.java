import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalOffice {

    public List<Patient> findPatientsByName(String name) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE LOWER(name) LIKE ? OR LOWER(prenume) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            pstmt.setString(2, "%" + name.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setName(rs.getString("name"));
                patient.setPrenume(rs.getString("prenume"));
                patient.setCNP(rs.getString("cnp"));
                patient.setTelefon(rs.getString("telefon"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setBloodGroup(rs.getString("blood_group"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }


    public List<Doctor> findDoctorsByName(String name) {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor WHERE LOWER(name) LIKE ? OR LOWER(prenume) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            pstmt.setString(2, "%" + name.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setName(rs.getString("name"));
                doctor.setPrenume(rs.getString("prenume"));
                doctor.setCNP(rs.getString("cnp"));
                doctor.setTelefon(rs.getString("telefon"));
                doctor.setEmail(rs.getString("email"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public MedicalOffice() {
    }

    public List<Doctor> getDoctorList() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setName(rs.getString("name"));
                doctor.setPrenume(rs.getString("prenume"));
                doctor.setCNP(rs.getString("cnp"));
                doctor.setTelefon(rs.getString("telefon"));
                doctor.setEmail(rs.getString("email"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public void setDoctorList(List<Doctor> doctorList) {
    }

    public List<Patient> getPatientList() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setName(rs.getString("name"));
                patient.setPrenume(rs.getString("prenume"));
                patient.setCNP(rs.getString("cnp"));
                patient.setTelefon(rs.getString("telefon"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setBloodGroup(rs.getString("blood_group"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void setPatientList(List<Patient> patientList) {
    }

    public List<Appointment> getAppointmentList() {
        updatePastAppointmentsToPerformed();
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient patient = getPatientByCNP(rs.getString("patient_cnp"));
                Doctor doctor = getDoctorByCNP(rs.getString("doctor_cnp"));
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                String status = rs.getString("status");
                Appointment appointment = new Appointment(patient, doctor, dateTime);
                appointment.setStatus(status);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
    }

    // Patient operations
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, prenume, cnp, telefon, email, address, blood_group) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getPrenume());
            pstmt.setString(3, patient.getCNP());
            pstmt.setString(4, patient.getTelefon());
            pstmt.setString(5, patient.getEmail());
            pstmt.setString(6, patient.getAddress());
            pstmt.setString(7, patient.getBloodGroup());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient getPatientByCNP(String cnp) {
        String sql = "SELECT * FROM patient WHERE cnp = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cnp);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setName(rs.getString("name"));
                patient.setPrenume(rs.getString("prenume"));
                patient.setCNP(rs.getString("cnp"));
                patient.setTelefon(rs.getString("telefon"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setBloodGroup(rs.getString("blood_group"));
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePatient(Patient updatedPatient) {
        String sql = "UPDATE patient SET name=?, prenume=?, telefon=?, email=?, address=?, blood_group=? WHERE cnp=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedPatient.getName());
            pstmt.setString(2, updatedPatient.getPrenume());
            pstmt.setString(3, updatedPatient.getTelefon());
            pstmt.setString(4, updatedPatient.getEmail());
            pstmt.setString(5, updatedPatient.getAddress());
            pstmt.setString(6, updatedPatient.getBloodGroup());
            pstmt.setString(7, updatedPatient.getCNP());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(String cnp) {
        String sql = "DELETE FROM patient WHERE cnp=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cnp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    public List<Appointment> getAppointmentsByPatientAndStatus(String patientCNP, String status) {
        updatePastAppointmentsToPerformed();
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE patient_cnp=? AND status=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientCNP);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = getPatientByCNP(rs.getString("patient_cnp"));
                Doctor doctor = getDoctorByCNP(rs.getString("doctor_cnp"));
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                Appointment appointment = new Appointment(patient, doctor, dateTime);
                appointment.setStatus(status);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }      

    

    // Doctor operations
    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctor (name, prenume, cnp, telefon, email, specialty) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getPrenume());
            pstmt.setString(3, doctor.getCNP());
            pstmt.setString(4, doctor.getTelefon());
            pstmt.setString(5, doctor.getEmail());
            pstmt.setString(6, doctor.getSpecialty());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Doctor getDoctorByCNP(String cnp) {
        String sql = "SELECT * FROM doctor WHERE cnp = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cnp);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setName(rs.getString("name"));
                doctor.setPrenume(rs.getString("prenume"));
                doctor.setCNP(rs.getString("cnp"));
                doctor.setTelefon(rs.getString("telefon"));
                doctor.setEmail(rs.getString("email"));
                doctor.setSpecialty(rs.getString("specialty"));
                return doctor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDoctor(Doctor updatedDoctor) {
        String sql = "UPDATE doctor SET name=?, prenume=?, telefon=?, email=?, specialty=? WHERE cnp=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedDoctor.getName());
            pstmt.setString(2, updatedDoctor.getPrenume());
            pstmt.setString(3, updatedDoctor.getTelefon());
            pstmt.setString(4, updatedDoctor.getEmail());
            pstmt.setString(5, updatedDoctor.getSpecialty());
            pstmt.setString(6, updatedDoctor.getCNP());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDoctor(String cnp) {
        String sql = "DELETE FROM doctor WHERE cnp=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cnp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    // Appointment operations
    public Appointment makeAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
    if (dateTime.isBefore(LocalDateTime.now())) {
        throw new IllegalArgumentException("Appointment date and time must be in the future.");
    }
    Patient patient = getPatientByCNP(patientCNP);
    Doctor doctor = getDoctorByCNP(doctorCNP);

    if (patient == null || doctor == null) {
        return null;
    }

    if (!isDoctorAvailable(doctorCNP, dateTime)) {
        return null;
    }

    String sql = "INSERT INTO appointment (patient_cnp, doctor_cnp, date_time, status) VALUES (?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, patientCNP);
        pstmt.setString(2, doctorCNP);
        pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(dateTime));
        pstmt.setString(4, "scheduled");
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }

    Appointment appointment = new Appointment(patient, doctor, dateTime);
    appointment.setStatus("scheduled");
    return appointment;
}

    public boolean rescheduleAppointment(String patientCNP, String doctorCNP,
                                         LocalDateTime oldDateTime, LocalDateTime newDateTime) {
        // Check if new time is available for doctor
        if (!isDoctorAvailable(doctorCNP, newDateTime)) {
            return false;
        }

        String sql = "UPDATE appointment SET date_time=? WHERE patient_cnp=? AND doctor_cnp=? AND date_time=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(newDateTime));
            pstmt.setString(2, patientCNP);
            pstmt.setString(3, doctorCNP);
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(oldDateTime));
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelAppointment(String patientCNP, String doctorCNP, LocalDateTime dateTime) {
        String sql = "UPDATE appointment SET status='canceled' WHERE patient_cnp=? AND doctor_cnp=? AND date_time=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientCNP);
            pstmt.setString(2, doctorCNP);
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(dateTime));
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Appointment> getDoctorDailySchedule(String doctorCNP, LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE doctor_cnp=? AND DATE(date_time)=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctorCNP);
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient patient = getPatientByCNP(rs.getString("patient_cnp"));
                Doctor doctor = getDoctorByCNP(rs.getString("doctor_cnp"));
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                String status = rs.getString("status");
                Appointment appointment = new Appointment(patient, doctor, dateTime);
                appointment.setStatus(status);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    private void updatePastAppointmentsToPerformed() {
    String sql = "UPDATE appointment SET status='performed' WHERE status='scheduled' AND date_time < ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private boolean isDoctorAvailable(String doctorCNP, LocalDateTime dateTime) {
        String sql = "SELECT COUNT(*) FROM appointment WHERE doctor_cnp=? AND date_time=? AND status='scheduled'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctorCNP);
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(dateTime));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}