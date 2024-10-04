package cl.playground.api;

import cl.playground.modelo.ApiResponse;

/**
 * Interfaz para clientes de API.
 * 
 * <p>
 * La interfaz ApiClient define un contrato para implementar clientes de API que
 * devuelven una respuesta de la API en formato {@link ApiResponse}. Los clientes
 * de API que implementen esta interfaz deber an sobreescribir el m todo
 * {@link #getResponse()} para que devuelva una instancia de {@link ApiResponse}
 * con la respuesta de la API.
 * 
 * @author Andres Sepulveda
 * @since 1.0
 */
public interface ApiClient {
    /**
     * Obtiene la respuesta de la API.
     * 
     * @return ApiResponse con la respuesta de la API.
     */
    ApiResponse getResponse();
}