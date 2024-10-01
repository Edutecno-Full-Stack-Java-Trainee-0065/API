package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.Utils;

import java.util.LinkedHashMap;
import java.util.Map;


public class IssApiClient implements ApiClient {

    private static final String API = "http://api.open-notify.org";
    private static final String ENDPOINT = "iss-now.json";

    @Override
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
