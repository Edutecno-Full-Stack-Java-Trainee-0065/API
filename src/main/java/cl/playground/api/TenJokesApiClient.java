package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenJokesApiClient implements ApiClient {

    private static final String API = "https://official-joke-api.appspot.com";
    private static final String ENDPOINT = "random_ten";

    @Override
    public ApiResponse getResponse() {
        String jsonResponse = Utils.jsonResponseObject(API, ENDPOINT);
        List<String> jokeList = Utils.extractArrayElements(jsonResponse);
        List<Map<String, Object>> formattedJokes = new ArrayList<>();

        for (String joke : jokeList) {
            Map<String, Object> jokeMap = new HashMap<>();
            jokeMap.put("type", Utils.extractValue(joke, "type"));
            jokeMap.put("setup", Utils.extractValue(joke, "setup"));
            jokeMap.put("punchline", Utils.extractValue(joke, "punchline"));
            jokeMap.put("id", Integer.parseInt(Utils.extractNumericValue(joke, "id")));
            formattedJokes.add(jokeMap);
        }

        ApiResponse response = new ApiResponse();
        response.addData("jokes", formattedJokes);

        return response;
    }
}
