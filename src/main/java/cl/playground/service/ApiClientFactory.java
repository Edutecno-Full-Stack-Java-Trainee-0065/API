package cl.playground.service;

import cl.playground.api.*;

public class ApiClientFactory {

    public static ApiClient createApiClient(String type) {
        switch (type) {
            case "iss":
                return new IssApiClient();
            case "joke":
                return new JokeApiClient();
            case "ten":
                return new TenJokesApiClient();
            case "posts":
                return new TipyCodeApiClient();
            default:
                throw new IllegalArgumentException("Unknown API type: " + type);
        }
    }
}
