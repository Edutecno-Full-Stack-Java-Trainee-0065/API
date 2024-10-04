package cl.playground.service;

import cl.playground.api.*;

/**
 * Factory class to create instances of different API clients.
 */
public class ApiClientFactory {

    /**
     * Creates an instance of the specified API client.
     *
     * @param type the type of API client to create. Supported values are:
     *             <ul>
     *             <li>iss: {@link IssApiClient}</li>
     *             <li>joke: {@link JokeApiClient}</li>
     *             <li>ten: {@link TenJokesApiClient}</li>
     *             <li>posts: {@link TipyCodeApiClient}</li>
     *             <li>picking: {@link PickingApiClient}</li>
     *             </ul>
     * @return the created API client
     * @throws IllegalArgumentException if the specified type is not supported
     */
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
            case "picking":
                return new PickingApiClient();
            default:
                throw new IllegalArgumentException("Unknown API type: " + type);
        }
    }
}