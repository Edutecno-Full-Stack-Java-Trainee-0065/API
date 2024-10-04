package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.Utils;

/**
 * A client for the Official Joke API that provides a random joke.
 *
 * @author Andres Sepulveda
 * @since 1.0
 */
public class JokeApiClient implements ApiClient {

    private static final String API = "https://official-joke-api.appspot.com";
    private static final String ENDPOINT = "random_joke";

    @Override
    /**
     * Retrieves a random joke from the API.
     *
     * @return An ApiResponse object containing the joke.
     */
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