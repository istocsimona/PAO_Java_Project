import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Service service = new Service();
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addPatient();
                        break;
                    case "2":
                        viewAllPatients();
                        break;
                    case "3":
                        findPatientByCNP();
                        break;
                    case "4":
                        updatePatient();
                        break;
                    case "5":
                        deletePatient();
                        break;
                    case "6":
                        findPatientsByName();
                        break;
                    case "7":
                        viewPatientHistory();
                        break;
                    case "8":
                        addDoctor();
                        break;
                    case "9":
                        viewAllDoctors();
                        break;
                    case "10":
                        findDoctorByCNP();
                        break;
                    case "11":
                        updateDoctor();
                        break;
                    case "12":
                        deleteDoctor();
                        break;
                    case "13":
                        makeAppointment();
                        break;
                    case "14":
                        rescheduleAppointment();
                        break;
                    case "15":
                        cancelAppointment();
                        break;
                    case "16":
                        viewDoctorSchedule();
                        break;
                    case "0":
                        exit = true;
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== MEDICAL OFFICE MANAGEMENT SYSTEM =====");
        System.out.println("1. Add new patient");
        System.out.println("2. View all patients");
        System.out.println("3. Find patient by CNP");
        System.out.println("4. Update patient information");
        System.out.println("5. Delete patient");
        System.out.println("6. Find patients by name");
        System.out.println("7. View patient consultation history");
        System.out.println("8. Add new doctor");
        System.out.println("9. View all doctors");
        System.out.println("10. Find doctor by CNP");
        System.out.println("11. Update doctor information");
        System.out.println("12. Delete doctor");
        System.out.println("13. Make an appointment");
        System.out.println("14. Reschedule an appointment");
        System.out.println("15. Cancel an appointment");
        System.out.println("16. View doctor's daily schedule");
        System.out.println("0. Exit");
        System.out.println("==========================================");
    }

    // Patient operations
    private static void addPatient() {
        System.out.println("\n----- Add New Patient -----");
        Patient patient = new Patient();

        System.out.print("Enter last name: ");
        patient.setName(scanner.nextLine());

        System.out.print("Enter first name: ");
        patient.setPrenume(scanner.nextLine());

        System.out.print("Enter CNP: ");
        patient.setCNP(scanner.nextLine());

        System.out.print("Enter phone number: ");
        patient.setTelefon(scanner.nextLine());

        System.out.print("Enter email: ");
        patient.setEmail(scanner.nextLine());

        System.out.print("Enter address: ");
        patient.setAddress(scanner.nextLine());

        System.out.print("Enter blood group: ");
        patient.setBloodGroup(scanner.nextLine());

        service.addPatient(patient);
        System.out.println("Patient added successfully!");
    }

    private static void viewAllPatients() {
        System.out.println("\n----- All Patients -----");
        List<Patient> patients = service.getAllPatients();

        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }

        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static void findPatientByCNP() {
        System.out.print("Enter patient CNP: ");
        String cnp = scanner.nextLine();

        Patient patient = service.getPatientByCNP(cnp);
        if (patient != null) {
            System.out.println("Patient found: " + patient);
        } else {
            System.out.println("No patient found with CNP: " + cnp);
        }
    }

    private static void updatePatient() {
        Patient patient = selectPatientByName();
        if (patient == null) {
            System.out.println("No patient selected.");
            return;
        }

        System.out.println("Current details: " + patient);
        System.out.println("Enter new details (press Enter to keep current value):");

        System.out.print("Enter last name [" + patient.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            patient.setName(name);
        }

        System.out.print("Enter first name [" + patient.getPrenume() + "]: ");
        String prenume = scanner.nextLine();
        if (!prenume.isEmpty()) {
            patient.setPrenume(prenume);
        }

        System.out.print("Enter phone number [" + patient.getTelefon() + "]: ");
        String telefon = scanner.nextLine();
        if (!telefon.isEmpty()) {
            patient.setTelefon(telefon);
        }

        System.out.print("Enter email [" + patient.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            patient.setEmail(email);
        }

        System.out.print("Enter address [" + patient.getAddress() + "]: ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) {
            patient.setAddress(address);
        }

        System.out.print("Enter blood group [" + patient.getBloodGroup() + "]: ");
        String bloodGroup = scanner.nextLine();
        if (!bloodGroup.isEmpty()) {
            patient.setBloodGroup(bloodGroup);
        }

        service.updatePatient(patient);
        System.out.println("Patient updated successfully!");
    }

    private static void deletePatient() {
        Patient patient = selectPatientByName();
        if (patient == null) {
            System.out.println("No patient selected.");
            return;
        }


        System.out.println("Are you sure you want to delete this patient? (y/n)");
        System.out.println(patient);

        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            service.deletePatient(patient.getCNP());
            System.out.println("Patient deleted successfully!");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private static void findPatientsByName() {
        System.out.print("Enter patient name (first or last): ");
        String name = scanner.nextLine();

        List<Patient> patients = service.findPatientsByName(name);
        if (patients.isEmpty()) {
            System.out.println("No patients found with name: " + name);
            return;
        }

        System.out.println("Found " + patients.size() + " patient(s):");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static void viewPatientHistory() {
        Patient patient = selectPatientByName();
        if (patient == null) {
            System.out.println("No patient selected.");
            return;
        }
        List<Consultation> history = service.getPatientHistory(patient.getCNP());

        if (history == null || history.isEmpty()) {
            System.out.println("No consultation history found for patient with CNP: " + patient.getCNP());
            return;
        }

        System.out.println("Consultation history:");
        for (Consultation consultation : history) {
            System.out.println(consultation);
        }
    }

    // Doctor operations
    private static void addDoctor() {
        System.out.println("\n----- Add New Doctor -----");
        Doctor doctor = new Doctor();

        System.out.print("Enter last name: ");
        doctor.setName(scanner.nextLine());

        System.out.print("Enter first name: ");
        doctor.setPrenume(scanner.nextLine());

        System.out.print("Enter CNP: ");
        doctor.setCNP(scanner.nextLine());

        System.out.print("Enter phone number: ");
        doctor.setTelefon(scanner.nextLine());

        System.out.print("Enter email: ");
        doctor.setEmail(scanner.nextLine());

        System.out.print("Enter specialty: ");
        doctor.setSpecialty(scanner.nextLine());

        service.addDoctor(doctor);
        System.out.println("Doctor added successfully!");
    }

    private static void viewAllDoctors() {
        System.out.println("\n----- All Doctors -----");
        List<Doctor> doctors = service.getAllDoctors();

        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }

        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private static void findDoctorByCNP() {
        System.out.print("Enter doctor CNP: ");
        String cnp = scanner.nextLine();

        Doctor doctor = service.getDoctorByCNP(cnp);
        if (doctor != null) {
            System.out.println("Doctor found: " + doctor);
        } else {
            System.out.println("No doctor found with CNP: " + cnp);
        }
    }

    private static void updateDoctor() {
        Doctor doctor = selectDoctorByName();
        if (doctor == null) {
            System.out.println("No doctor selected.");
            return;
        }

        System.out.println("Current details: " + doctor);
        System.out.println("Enter new details (press Enter to keep current value):");

        System.out.print("Enter last name [" + doctor.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            doctor.setName(name);
        }

        System.out.print("Enter first name [" + doctor.getPrenume() + "]: ");
        String prenume = scanner.nextLine();
        if (!prenume.isEmpty()) {
            doctor.setPrenume(prenume);
        }

        System.out.print("Enter phone number [" + doctor.getTelefon() + "]: ");
        String telefon = scanner.nextLine();
        if (!telefon.isEmpty()) {
            doctor.setTelefon(telefon);
        }

        System.out.print("Enter email [" + doctor.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            doctor.setEmail(email);
        }

        System.out.print("Enter specialty [" + doctor.getSpecialty() + "]: ");
        String specialty = scanner.nextLine();
        if (!specialty.isEmpty()) {
            doctor.setSpecialty(specialty);
        }

        service.updateDoctor(doctor);
        System.out.println("Doctor updated successfully!");
    }

    private static void deleteDoctor() {
        Doctor doctor = selectDoctorByName();
        if (doctor == null) {
            System.out.println("No doctor selected.");
            return;
        }

        System.out.println("Are you sure you want to delete this doctor? (y/n)");
        System.out.println(doctor);

        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            service.deleteDoctor(doctor.getCNP());
            System.out.println("Doctor deleted successfully!");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    // Appointment operations
    private static void makeAppointment() {
    System.out.println("\n----- Make Appointment -----");

    Patient patient = selectPatientByName();
    if (patient == null) {
        System.out.println("No patient selected.");
        return;
    }

    if (patient == null) {
        System.out.println("Patient not found. Please add the patient first.");
        return;
    }

    Doctor doctor = selectDoctorByName();
    if (doctor == null) {
        System.out.println("No doctor selected.");
        return;
    }
    if (doctor == null) {
        System.out.println("Doctor not found. Please add the doctor first.");
        return;
    }

    LocalDateTime dateTime = null;
    while (true) {
        System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
            if (dateTime.isBefore(LocalDateTime.now())) {
                System.out.println("Appointment date and time must be in the future. Please try again.");
                continue;
            }
            break;
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm format.");
        }
    }

    try {
        Appointment appointment = service.makeAppointment(patient.getCNP(), doctor.getCNP(), dateTime);
        if (appointment != null) {
            System.out.println("Appointment created successfully: " + appointment);
        } else {
            System.out.println("Failed to create appointment. Doctor may not be available at this time.");
        }
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}
    private static void rescheduleAppointment() {
        System.out.println("\n----- Reschedule Appointment -----");

        Patient patient = selectPatientByName();
        if (patient == null) {
            System.out.println("No patient selected.");
            return;
        }

        Doctor doctor = selectDoctorByName();
        if (doctor == null) {
            System.out.println("No doctor selected.");
            return;
        }

        System.out.print("Enter current appointment date and time (yyyy-MM-dd HH:mm): ");
        String oldDateTimeStr = scanner.nextLine();

        System.out.print("Enter new appointment date and time (yyyy-MM-dd HH:mm): ");
        String newDateTimeStr = scanner.nextLine();

        try {
            LocalDateTime oldDateTime = LocalDateTime.parse(oldDateTimeStr, dateTimeFormatter);
            LocalDateTime newDateTime = LocalDateTime.parse(newDateTimeStr, dateTimeFormatter);

            boolean success = service.rescheduleAppointment(patient.getCNP(), doctor.getCNP(), oldDateTime, newDateTime);

            if (success) {
                System.out.println("Appointment rescheduled successfully.");
            } else {
                System.out.println("Failed to reschedule appointment. Please check the details and try again.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm format.");
        }
    }

    private static void cancelAppointment() {
        System.out.println("\n----- Cancel Appointment -----");

        Patient patient = selectPatientByName();
        if (patient == null) {
            System.out.println("No patient selected.");
            return;
        }

        Doctor doctor = selectDoctorByName();
        if (doctor == null) {
            System.out.println("No doctor selected.");
            return;
        }

        System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
        String dateTimeStr = scanner.nextLine();

        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);

            boolean success = service.cancelAppointment(patient.getCNP(), doctor.getCNP(), dateTime);

            if (success) {
                System.out.println("Appointment canceled successfully.");
            } else {
                System.out.println("Failed to cancel appointment. Please check the details and try again.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm format.");
        }
    }

    private static void viewDoctorSchedule() {
        System.out.println("\n----- View Doctor Schedule -----");

        Doctor doctor = selectDoctorByName();
        if (doctor == null) {
            System.out.println("No doctor selected.");
            return;
        }

        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();

        try {
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);

            List<Appointment> appointments = service.getDoctorDailySchedule(doctor.getCNP(), date);

            if (appointments.isEmpty()) {
                System.out.println("No appointments scheduled for " + doctor.getName() + " " + doctor.getPrenume() + " on " + dateStr);
            } else {
                System.out.println("Appointments for " + doctor.getName() + " " + doctor.getPrenume() + " on " + dateStr + ":");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
        }
    }

    private static Patient selectPatientByName() {
        System.out.print("Enter patient last name or first name: ");
        String name = scanner.nextLine();
        List<Patient> patients = service.findPatientsByName(name);

        if (patients.isEmpty()) {
            System.out.println("No patients found with name: " + name);
            return null;
        } else if (patients.size() == 1) {
            Patient patient = patients.get(0);
            System.out.println("Is this who you are looking for? " + patient + " (y/n)");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                return patient;
            } else {
                return null;
            }
        } else {
            System.out.println("Multiple patients found:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.println((i + 1) + ". " + patients.get(i));
            }
            System.out.print("Enter the number of the patient you want: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= patients.size()) {
                    return patients.get(choice - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
            return null;
        }
    }

    private static Doctor selectDoctorByName() {
        System.out.print("Enter doctor last name or first name: ");
        String name = scanner.nextLine();
        List<Doctor> doctors = service.findDoctorsByName(name);

        if (doctors.isEmpty()) {
            System.out.println("No doctors found with name: " + name);
            return null;
        } else if (doctors.size() == 1) {
            Doctor doctor = doctors.get(0);
            System.out.println("Is this who you are looking for? " + doctor + " (y/n)");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                return doctor;
            } else {
                return null;
            }
        } else {
            System.out.println("Multiple doctors found:");
            for (int i = 0; i < doctors.size(); i++) {
                System.out.println((i + 1) + ". " + doctors.get(i));
            }
            System.out.print("Enter the number of the doctor you want: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= doctors.size()) {
                    return doctors.get(choice - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
            return null;
        }
    }

    
}