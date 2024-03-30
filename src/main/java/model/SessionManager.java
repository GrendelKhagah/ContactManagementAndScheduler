package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Session manager for tracking active user across application
 * 
 * @author Taylor Ketterling
 */
public class SessionManager {
    private static SessionManager instance;
    private User activeUser;                      
    private static String currentLanguage;
    private Map<String, String> languageMap;


    private SessionManager() {
        languageMap = new HashMap<>();
        languageMap.put("English", "en");
        languageMap.put("French", "fr");
        languageMap.put("Espanol", "es"); // later expansion
        
        currentLanguage = "en"; // Default language code
    }

    /**
     * returns the instance of itself
     * 
     * @return 
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Sets the active user(used after login)
     * 
     * @param user 
     */
    public void setActiveUser(User user) {
        this.activeUser = user;
    }

    /**
     * Gets active user, used when needing user data
     * 
     * @return 
     */
    public User getActiveUser() {
        return this.activeUser;
    }
    
    /**
     * Sets active user to null. in essence logging the user out
     * 
     */
    public void logOut(){
        this.activeUser = null;
    }
    
    /**
     * Gets the Language code of the user
     * default is en
     * 
     * 
     * @return String current language
     */
    public String getCurrentLanguage() {
        System.out.println(currentLanguage);
        return currentLanguage;
    }

    /**
     * Sets the current language code
     * 
     * @param language language code of user
     */
    public void setCurrentLanguage(String language) {
        currentLanguage = languageMap.getOrDefault(language, "en");        
    }
}
