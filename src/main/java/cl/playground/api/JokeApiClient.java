package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.Utils;

public class JokeApiClient implements ApiClient {

    private static final String API = "https://official-joke-api.appspot.com";
    private static final String ENDPOINT = "random_joke";

    @Override
    public ApiResponse getResponse() {
        String jsonResponse = Utils.jsonResponseObject(API, ENDPOINT);
        ApiResponse response = new ApiResponse();

        response.addData("type", Utils.extractValue(jsonResponse, "type"));
        response.addData("setup", Utils.extractValue(jsonResponse, "setup"));
        response.addData("punchline", Utils.extractValue(jsonResponse, "punchline"));
        response.addData("id", Utils.extractNumericValue(jsonResponse, "id"));

        return response;
    }
}
