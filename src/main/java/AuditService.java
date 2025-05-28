import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String FILE_NAME = "audit.csv";
    private static AuditService instance;

    private AuditService() {
        // Scrie header dacă fișierul nu există
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
                writer.append("action,date_time\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String action) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            writer.append(timestamp).append(' ').append(action).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}