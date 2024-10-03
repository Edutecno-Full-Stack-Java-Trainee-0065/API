package cl.playground;

import cl.playground.api.ApiClient;
import cl.playground.api.PickingApiClient;
import cl.playground.modelo.ApiResponse;
import cl.playground.service.ApiClientFactory;

public class App {

    private static PickingApiClient pickingApiClient;

    public static void main(String[] args) {
        try {
            initializeClient();

            // Descomentar la opción que desees ejecutar
            //loginAdmin();
            //getAllFiltros();
            getAllUsers(0, 10);
            // createUser("newuser", "password123", "newuser@example.com", new String[]{"USER"});
            // updateUser(5, "updateduser", "updated@example.com", new String[]{"USER", "OPERATIONS"});
            // changeUserStatus(5, false);
            // getUserById(5);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeClient() {
        ApiClient api = ApiClientFactory.createApiClient("picking");
        if (api instanceof PickingApiClient) {
            pickingApiClient = (PickingApiClient) api;
        } else {
            throw new RuntimeException("Failed to initialize PickingApiClient");
        }
    }

    private static void loginAdmin() {
        System.out.println("Attempting to login...");
        pickingApiClient.login("operations", "password");
        System.out.println("Login successful.");
    }

    private static void getAllFiltros() {
        System.out.println("Fetching all filtros...");
        ApiResponse response = pickingApiClient.getResponse();
        System.out.println(response.toString());
    }

    private static void getAllUsers(int page, int size) {
        System.out.println("Fetching all users...");
        ApiResponse response = pickingApiClient.getAllUsers(page, size);
        System.out.println(response.toString());
    }
    /*
    private static void createUser(String username, String password, String email, String[] roles) {
        System.out.println("Creating new user...");
        ApiResponse response = pickingApiClient.createUser(username, password, email, roles);
        System.out.println("User created:");
        System.out.println(response.toString());
    }

    private static void updateUser(int userId, String username, String email, String[] roles) {
        System.out.println("Updating user...");
        ApiResponse response = pickingApiClient.updateUser(userId, username, email, roles);
        System.out.println("User updated:");
        System.out.println(response.toString());
    }

    private static void changeUserStatus(int userId, boolean isActive) {
        System.out.println("Changing user status...");
        ApiResponse response = pickingApiClient.changeUserStatus(userId, isActive);
        System.out.println("User status changed:");
        System.out.println(response.toString());
    }

    private static void getUserById(int userId) {
        System.out.println("Fetching user by ID...");
        ApiResponse response = pickingApiClient.getUserById(userId);
        System.out.println("User received:");
        System.out.println(response.toString());
    }
    */
}