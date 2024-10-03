package cl.playground.util;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String extractValue(String json, String key) {
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

    public static String extractNumericValue(String json, String key) {
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

    public static String extractObject(String json, String key) {
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

    public static List<String> extractArrayElements(String json) {

        List<String> elements = new ArrayList<>();
        int arrayStartIndex = json.indexOf('[');
        int arrayEndIndex = json.lastIndexOf(']');

        if (arrayStartIndex == -1 || arrayEndIndex == -1 || arrayEndIndex <= arrayStartIndex) {
            return elements; // Retorna una lista vacía si no se encuentra un array válido
        }

        String arrayContent = json.substring(arrayStartIndex + 1, arrayEndIndex);
        int depth = 0;
        StringBuilder currentElement = new StringBuilder();

        for (char c : arrayContent.toCharArray()) {
            if (c == '{') {
                depth++;
            }
            if (depth > 0) {
                currentElement.append(c);
            }
            if (c == '}') {
                depth--;
                if (depth == 0) {
                    elements.add(currentElement.toString().trim());
                    currentElement = new StringBuilder();
                }
            }
        }

        return elements;
    }

    public static String formatJsonResponse(Object data) {
        return formatJsonResponse(data, 0);
    }

    private static String formatJsonResponse(Object data, int indent) {
        if (data instanceof List) {
            return formatJsonArray((List<?>) data, indent);
        } else if (data instanceof Map) {
            return formatJsonObject((Map<?, ?>) data, indent);
        } else {
            return data.toString();
        }
    }

    private static String formatJsonObject(Map<?, ?> map, int indent) {
        StringBuilder sb = new StringBuilder("{\n");
        String indentStr = "  ".repeat(indent + 1);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(indentStr).append("\"").append(entry.getKey()).append("\": ");
            if (entry.getValue() instanceof String) {
                sb.append("\"").append(entry.getValue()).append("\"");
            } else if (entry.getValue() instanceof Number) {
                sb.append(entry.getValue());
            } else {
                sb.append(formatJsonResponse(entry.getValue(), indent + 1));
            }
            sb.append(",\n");
        }
        if (sb.charAt(sb.length() - 2) == ',') {
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        sb.append("  ".repeat(indent)).append("}");
        return sb.toString();
    }

    private static String formatJsonArray(List<?> list, int indent) {
        StringBuilder sb = new StringBuilder("[\n");
        String indentStr = "  ".repeat(indent + 1);
        for (Object item : list) {
            sb.append(indentStr).append(formatJsonResponse(item, indent + 1)).append(",\n");
        }
        if (sb.charAt(sb.length() - 2) == ',') {
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        sb.append("  ".repeat(indent)).append("]");
        return sb.toString();
    }

    public static String jsonResponseObject(String api, String endpoint) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(api).path(endpoint);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response apiResponse = invocationBuilder.get();

        return apiResponse.readEntity(String.class);
    }

    public static String postJsonRequest(String url, Map<String, String> headers, String jsonBody) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        if ( headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                invocationBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        Response response = invocationBuilder.post(Entity.json(jsonBody));
        return response.readEntity(String.class);
    }

    public static String jsonResponseObject(String url, Map<String, String> headers) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        if ( headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                invocationBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        Response apiResponse = invocationBuilder.get();
        return apiResponse.readEntity(String.class);
    }
}
