import java.time.LocalDateTime;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private String status; //scheduled, canceled, performed
}
