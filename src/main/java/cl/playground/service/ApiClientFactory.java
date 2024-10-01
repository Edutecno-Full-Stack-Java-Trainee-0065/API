package cl.playground.service;

import cl.playground.api.ApiClient;
import cl.playground.api.IssApiClient;
import cl.playground.api.JokeApiClient;
import cl.playground.api.TenJokesApiClient;

public class ApiClientFactory {

    public static ApiClient createApiClient(String type) {
        switch (type) {
            case "iss":
                return new IssApiClient();
            case "joke":
                return new JokeApiClient();
            case "ten":
                return new TenJokesApiClient();
            default:
                throw new IllegalArgumentException("Unknown API type: " + type);
        }
    }
}
