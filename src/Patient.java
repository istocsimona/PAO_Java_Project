import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private String address;
    private String bloodGroup;
    private List<Consultation> consultationHistory;

    public Patient() {
        this.consultationHistory = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<Consultation> getConsultationHistory() {
        return consultationHistory;
    }

    public void setConsultationHistory(List<Consultation> consultationHistory) {
        this.consultationHistory = consultationHistory;
    }

    public void addConsultation(Consultation consultation) {
        if (this.consultationHistory == null) {
            this.consultationHistory = new ArrayList<>();
        }
        this.consultationHistory.add(consultation);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", prenume='" + prenume + '\'' +
                ", CNP='" + CNP + '\'' +
                ", address='" + address + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                '}';
    }
}