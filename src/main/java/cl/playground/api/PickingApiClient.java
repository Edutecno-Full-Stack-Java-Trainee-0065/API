package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.TokenManager;
import cl.playground.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa la interfaz ApiClient para interactuar con la API de
 * Picking.
 */
public class PickingApiClient implements ApiClient {

    /**
     * URL base de la API de Picking.
     */
    private static final String API_BASE_URL = "http://54.174.216.183:8080/api";

    /**
     * Obtiene la respuesta de la API.
     *
     * @return ApiResponse con la respuesta de la API.
     */
    @Override
    public ApiResponse getResponse() {
        return getAllFiltros();
    }

    /**
     * Realiza un login en la API de Picking.
     *
     * @param username Username del usuario.
     * @param password Contrase a del usuario.
     */
    public void login(String username, String password) {
        String loginEndpoint = "/auth/login";
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        System.out.println("Sending login request to: " + API_BASE_URL + loginEndpoint);
        String jsonResponse = Utils.postJsonRequest(API_BASE_URL + loginEndpoint, null, jsonBody);
        System.out.println("Received response: " + jsonResponse);

        String role = Utils.extractValue(jsonResponse, "role");
        String token = Utils.extractValue(jsonResponse, "jwt");

        System.out.println("Extracted role: " + role);
        System.out.println("Extracted token: " + token);

        if (token != null && role != null) {
            TokenManager.saveToken(token, role);
            System.out.println("Token and role saved successfully.");
        } else {
            System.out.println("Failed to extract token or role from response.");
            throw new RuntimeException("Token not found");
        }
    }

    /**
     * Obtiene todos los filtros de la API de Picking.
     *
     * @return ApiResponse con la lista de filtros.
     */
    public ApiResponse getAllFiltros() {
        String endpoint = "/filtros";
        Map<String, String> headers = getAuthHeaders();

        String jsonResponse = Utils.jsonResponseObject(API_BASE_URL + endpoint, headers);
        List<String> filtros = Utils.extractArrayElements(jsonResponse);
        List<Map<String, Object>> formattedFiltros = new ArrayList<>();

        for (String filtro : filtros) {
            Map<String, Object> filtroMap = new HashMap<>();
            filtroMap.put("lpn", Utils.extractValue(filtro, "lpn"));
            filtroMap.put("numeroReserva", Integer.parseInt(Utils.extractNumericValue(filtro, "numeroReserva")));
            filtroMap.put("subTipoServicio", Utils.extractValue(filtro, "subTipoServicio"));
            filtroMap.put("numeroDetalle", Integer.parseInt(Utils.extractNumericValue(filtro, "numeroDetalle")));
            filtroMap.put("estadoReserva", Utils.extractValue(filtro, "estadoReserva"));
            filtroMap.put("estadoDetalleReserva", Utils.extractValue(filtro, "estadoDetalleReserva"));
            filtroMap.put("bloqueado", Boolean.parseBoolean(Utils.extractValue(filtro, "bloqueado")));
            filtroMap.put("sinStock", Boolean.parseBoolean(Utils.extractValue(filtro, "sinStock")));
            filtroMap.put("sku", Utils.extractValue(filtro, "sku"));
            filtroMap.put("producto", Utils.extractValue(filtro, "producto"));
            filtroMap.put("fechaIngreso", Utils.extractValue(filtro, "fechaIngreso"));
            filtroMap.put("fechaPactada", Utils.extractValue(filtro, "fechaPactada"));
            filtroMap.put("fechaReservadoTienda", Utils.extractValue(filtro, "fechaReservadoTienda"));
            filtroMap.put("horaPickingTienda", Utils.extractValue(filtro, "horaPickingTienda"));
            filtroMap.put("horaPreparadoTienda", Utils.extractValue(filtro, "horaPreparadoTienda"));
            filtroMap.put("unidades", Integer.parseInt(Utils.extractNumericValue(filtro, "unidades")));

            formattedFiltros.add(filtroMap);
        }

        ApiResponse response = new ApiResponse();
        response.addData("filtros", formattedFiltros);

        return response;
    }

    /**
     * Obtiene todos los usuarios de la API de Picking.
     *
     * @param page  N mero de p gina.
     * @param size  N mero de elementos por p gina.
     * @return ApiResponse con la lista de usuarios.
     */
    public ApiResponse getAllUsers(int page, int size) {
        String endpoint = "/users?page=" + page + "&size=" + size;
        Map<String, String> headers = getAuthHeaders();

        System.out.println("Sending request to: " + (API_BASE_URL + endpoint));
        System.out.println("Headers: " + headers);

        String jsonResponse = Utils.jsonResponseObject(API_BASE_URL + endpoint, headers);
        System.out.println("Received response: " + jsonResponse);

        ApiResponse response = new ApiResponse();

        // Extraer la lista de usuarios
        List<String> usersList = Utils.extractArrayElements(Utils.extractObject(jsonResponse, "users"));
        List<Map<String, Object>> formattedUsers = new ArrayList<>();

        for (String user : usersList) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", Utils.extractNumericValue(user, "userId"));
            userMap.put("username", Utils.extractValue(user, "username"));
            userMap.put("email", Utils.extractValue(user, "email"));
            userMap.put("roles", Utils.extractArrayElements(Utils.extractObject(user, "roles")));
            userMap.put("active", Boolean.parseBoolean(Utils.extractValue(user, "active")));
            formattedUsers.add(userMap);
        }

        response.addData("users", formattedUsers);

        // Extraer informaci n de paginaci n
        response.addData("totalPages", Utils.extractNumericValue(jsonResponse, "totalPages"));
        response.addData("totalElements", Utils.extractNumericValue(jsonResponse, "totalElements"));

        return response;
    }

    /**
     * Genera un Map con los headers de autenticaci n necesarios para las peticiones a la API.
     *
     * @return Map con los headers de autenticaci n. El Map contiene un header llamado "Authorization" con el valor "Bearer <token>" donde <token> es el token de autenticaci n guardado por {@link TokenManager}.
     *         Si el token no ha sido guardado, se devuelve un Map vac o.
     *
     * @see TokenManager#saveToken(String, String)
     * @see TokenManager#getToken()
     */
    private Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        String token = TokenManager.getToken();
        if (token != null) {
            headers.put("Authorization", "Bearer " + token);
        }
        return headers;
    }
}