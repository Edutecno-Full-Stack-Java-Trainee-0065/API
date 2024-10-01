package cl.playground.api;

import cl.playground.modelo.ApiResponse;
import cl.playground.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipyCodeApiClient implements ApiClient {

    private static final String API = "https://jsonplaceholder.typicode.com";
    private static final String ENDPOINT = "posts";

    @Override
    public ApiResponse getResponse() {
        String jsonResponse = Utils.jsonResponseObject(API, ENDPOINT);
        List<String> postList = Utils.extractArrayElements(jsonResponse);
        List<Map<String, Object>> formattedPost = new ArrayList<>();

        for (String post : postList) {
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("userId", Integer.parseInt(Utils.extractNumericValue(post, "userId")));
            postMap.put("id", Integer.parseInt(Utils.extractNumericValue(post, "id")));
            postMap.put("title", Utils.extractValue(post, "title"));
            postMap.put("body", Utils.extractValue(post, "body"));

            formattedPost.add(postMap);
        }

        ApiResponse response = new ApiResponse();
        response.addData("post´s", formattedPost);

        return response;
    }
}
