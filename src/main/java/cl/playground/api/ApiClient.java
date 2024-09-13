package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.modelo.IssPosition;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class ApiClient {

    private static final String API = "http://api.open-notify.org";
    private static final String ENDPOINT = "iss-now.json";

    public static ApiResponse conexionApi() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(API).path(ENDPOINT);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response apiResponse = invocationBuilder.get();
        String jsonResponse = apiResponse.readEntity(String.class);

        String message = extractValue(jsonResponse, "message");
        String issPositionJson = extractObject(jsonResponse, "iss_position");
        String latitude = extractValue(issPositionJson, "latitude");
        String longitude = extractValue(issPositionJson, "longitude");
        String timestamp = extractNumericValue(jsonResponse, "timestamp");

        // CONSTRUYENDO EL OBJETO.
        IssPosition issPosition = new IssPosition(latitude, longitude);
        ApiResponse apiObject = new ApiResponse(message, issPosition, timestamp);

        return apiObject;
    }

    private static String extractValue(String json, String key) {
        String searchString = "\"" + key + "\""; // CREAMOS PATRON DE BUSQUEDA

        int keyIndex = json.indexOf(searchString); // BUSCAMOS LA POSICION DE LA LLAVE
        if (keyIndex == -1) {
            return null;
        }

        int colonIndex = json.indexOf(":", keyIndex); // BUSCA EL "COLON" PARA SEPARAR CLAVE Y VALOR
        if (colonIndex == -1) {
            return null;
        }

        int valueStartIndex = json.indexOf("\"", colonIndex); // BUSCAMOS EL INICIO DEL VALOR POSTERIOR AL "COLON"
        if (valueStartIndex == -1) {
            return null;
        }

        int valueEndIndex = json.indexOf("\"", valueStartIndex + 1); // BUSCAMOS LA POSICION FINAL DE LAS COMILLAS DE CIERRE.
        if (valueEndIndex == -1) {
            return null;
        }

        return json.substring(valueStartIndex + 1, valueEndIndex); // EXTRAER Y DEVOLVER EL VALOR
    }
    private static String extractNumericValue(String json, String key) {
        String searchString = "\"" + key + "\""; // CREAMOS PATRON DE BUSQUEDA

        int keyIndex = json.indexOf(searchString); // BUSCAMOS LA POSICION DE LA LLAVE
        if (keyIndex == -1) {
            return null;
        }

        int colonIndex = json.indexOf(":", keyIndex); // BUSCA EL "COLON" PARA SEPARAR CLAVE Y VALOR
        if (colonIndex == -1) {
            return null;
        }

        // Buscamos los valores posteriores a los "colons" y espacios
        int valueStartIndex = colonIndex + 1;

        // "Eliminar" espacios en blanco al principio del valor
        while (valueStartIndex < json.length() && json.charAt(valueStartIndex) == ' ') {
            valueStartIndex++;
        }

        char firstChar = json.charAt(valueStartIndex);
        if (firstChar == '"' || firstChar == '{' || firstChar == '[') {
            return null; // PORQUE NO ES UN NUMERO SINO UN CARACTER, ARREGLO U OBJETO
        }

        // Buscar valor final
        int valueEndIndex;
        if (firstChar == '-' || Character.isDigit(firstChar)) {

            // Si esto inicia con un "menos" o un numero pasa.
            valueEndIndex = json.indexOf(",", valueStartIndex);
            if (valueEndIndex == -1) {
                valueEndIndex = json.indexOf("}", valueStartIndex);
            }
        } else {
            return null;
        }

        if (valueEndIndex == -1) {
            valueEndIndex = json.length();
        }
        return json.substring(valueStartIndex, valueEndIndex).trim();
    }
    private static String extractObject(String json, String key) {
        String searchString = "\"" + key + "\""; // Creamos el patrón de búsqueda

        int keyIndex = json.indexOf(searchString); // Buscamos la posición de la clave
        if (keyIndex == -1) {
            return null;
        }

        int colonIndex = json.indexOf(":", keyIndex); // Busca el "colon" para separar clave y valor
        if (colonIndex == -1) {
            return null;
        }

        int objectStartIndex = json.indexOf("{", colonIndex); // Busca el inicio del objeto anidado
        int objectEndIndex = json.indexOf("}", objectStartIndex); // Busca el final del objeto anidado

        if (objectStartIndex == -1 || objectEndIndex == -1) {
            return null;
        }

        return json.substring(objectStartIndex, objectEndIndex + 1); // Extraer y devolver el objeto completo
    }
}
