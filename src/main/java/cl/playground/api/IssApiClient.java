package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.Utils;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A client for the Open Notify API that provides information about the current position of the International Space Station.
 *
 * @author Andres Sepulveda
 * @since 1.0
 */
public class IssApiClient implements ApiClient {

    /**
     * The base URL of the Open Notify API.
     */
    private static final String API = "http://api.open-notify.org";

    /**
     * The endpoint of the API for retrieving the current position of the International Space Station.
     */
    private static final String ENDPOINT = "iss-now.json";

    @Override
    /**
     * Retrieves the current position of the International Space Station.
     *
     * @return An ApiResponse object containing the current position of the International Space Station.
     */
    public ApiResponse getResponse() {
        String jsonResponse = Utils.jsonResponseObject(API, ENDPOINT);
        ApiResponse response = new ApiResponse();

        response.addData("message", Utils.extractValue(jsonResponse, "message"));

        Map<String, Object> issPosition = new LinkedHashMap<>();
        issPosition.put("latitude", Utils.extractValue(Utils.extractObject(jsonResponse, "iss_position"), "latitude"));
        issPosition.put("longitude", Utils.extractValue(Utils.extractObject(jsonResponse, "iss_position"), "longitude"));
        response.addData("iss_position", issPosition);

        response.addData("timestamp", Utils.extractNumericValue(jsonResponse, "timestamp"));

        return response;
    }
}