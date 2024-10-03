package cl.playground.util;

import java.io.*;
import java.util.Properties;

public class TokenManager {

    private static final String TOKEN_FILE =  "auth_token.properties";

    public static void saveToken(String token, String role) {
        Properties prop = new Properties();
        prop.setProperty("token", token);
        prop.setProperty("role", role);

        try (OutputStream output = new FileOutputStream(TOKEN_FILE)) {
            prop.store(output, "Authentication Information");
            System.out.println("Token saved successfully.");
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Failed to save token: " + io.getMessage());
        }
    }

    public static String getToken() {
        return getProperty("token");
    }

    public static String getRole() {
        return getProperty("role");
    }

    private static String getProperty(String key) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(TOKEN_FILE)) {
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException ex) {
            System.out.println("Failed to read token: " + ex.getMessage());
            return null;
        }
    }
}

