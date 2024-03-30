package helper;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Activity logger, writes all attempts to a file login_activity.txt 
 * 
 * @author Taylor Ketterling
 */
public class LoginActivityLogger {
    
    /**
     * method takes a username and success status. Logs this information with current date and time
     * stores the date in UTC
     * Log is @ login_activity.txt
     * 
     * @param username
     * @param loginSuccessful 
     */
    public static void logLoginAttempt(String username, boolean loginSuccessful) {
        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String successStatus = loginSuccessful ? "SUCCESSFUL" : "FAILED";
            
            printWriter.println(now.format(formatter) + " UTC - Login attempt for user '" + username + "': " + successStatus);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
