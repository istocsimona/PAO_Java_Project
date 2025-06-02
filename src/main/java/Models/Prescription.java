package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private List<String> pills;
    private LocalDateTime issueDate;

    public Prescription() {
        this.pills = new ArrayList<>();
    }

    public List<String> getPills() {
        return pills;
    }

    public void setPills(List<String> pills) {
        this.pills = pills;
    }

    public void addPill(String pill) {
        if (this.pills == null) {
            this.pills = new ArrayList<>();
        }
        this.pills.add(pill);
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Models.Prescription{" +
                "pills=" + pills +
                ", issueDate=" + issueDate.format(formatter) +
                '}';
    }
}