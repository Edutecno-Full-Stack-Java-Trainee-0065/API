package cl.playground;

import cl.playground.api.ApiClient;
import cl.playground.service.ApiClientFactory;

public class App {

    public static void main( String[] args ) {


        ApiClient API = ApiClientFactory.createApiClient("ten");
        System.out.println(API.getResponse());
    }
}
