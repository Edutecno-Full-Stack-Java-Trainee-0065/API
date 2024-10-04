package cl.playground.util;

import java.io.*;
import java.util.Properties;

/**
 * Clase que se encarga de guardar y obtener informaci n de autenticaci n.
 * La informaci n se almacena en un archivo de propiedades llamado
 * "auth_token.properties".
 *
 * @author Andres Sepulveda
 *
 */
public class TokenManager {

    /**
     * Nombre del archivo donde se almacena la informaci n de autenticaci n.
     */
    private static final String TOKEN_FILE =  "auth_token.properties";

    /**
     * Guarda la informaci n de autenticaci n en el archivo de propiedades.
     * La informaci n se almacena con las claves "token" y "role".
     *
     * @param token Token de autenticaci n.
     * @param role Rol del usuario autenticado.
     */
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

    /**
     * Obtiene el token de autenticaci n guardado.
     *
     * @return Token de autenticaci n.
     */
    public static String getToken() {
        return getProperty("token");
    }

    /**
     * Obtiene el rol del usuario autenticado.
     *
     * @return Rol del usuario autenticado.
     */
    public static String getRole() {
        return getProperty("role");
    }

    /**
     * Obtiene el valor de una propiedad de la informaci n de autenticaci n.
     *
     * @param key Clave de la propiedad a obtener.
     * @return Valor de la propiedad obtenida, o nulo si no se encuentra.
     */
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
