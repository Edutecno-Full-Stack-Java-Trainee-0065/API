package cl.playground.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Caso Calculadora")
public class CalculadoraTest {

    private final Calculadora calculadora = new Calculadora();

    @Test
    @DisplayName("Test de suma")
    public void testSumar() {
        assertEquals(100, calculadora.sumar(70, 30));
    }

    @Test
    @DisplayName("Test de resta")
    public void testRestar() {
        assertEquals(10, calculadora.restar(70, 60));
    }

    @Test
    @DisplayName("Test de multiplicar")
    public void testMultiplicar() {
        assertEquals(20, calculadora.multiplicar(4, 5));
    }

    @Test
    @DisplayName("Test de dividir")
    public void testDividir() {
        assertEquals(10, calculadora.dividir(40, 4));
    }
}