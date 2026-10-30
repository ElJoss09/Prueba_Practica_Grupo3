package com.uta.academico;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class ABBTest {

    @Test
    void insertarYBuscarFunciona() {
        ABB abb = new ABB();
        assertTrue(abb.insertar(50, "Ana", 8.5));
        assertNotNull(abb.buscar(50));
        assertEquals("Ana", abb.buscar(50).getNombre());
    }

    @Test
    void insertarDuplicadoRechaza() {
        ABB abb = new ABB();
        abb.insertar(40, "Ana", 8.0);
        assertFalse(abb.insertar(40, "Luis", 9.0));
    }

    @Test
    void recorridoInOrdenEsperado() {
        ABB abb = new ABB();
        int[] vals = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};
        for (int v : vals) {
            abb.insertar(v, "E" + v, 8.0);
        }
        assertEquals(List.of(20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85), abb.recorridoInOrden());
    }

    @Test
    void modificarSinCambiarClave() {
        ABB abb = new ABB();
        abb.insertar(35, "Ana", 8.0);
        assertTrue(abb.modificarEstudiante(35, "Ana M", 9.5));
        assertEquals("Ana M", abb.buscar(35).getNombre());
        assertEquals(35, abb.buscar(35).getMatricula());
    }

    @Test
    void eliminarHojaYHojaConUnHijo() {
        ABB abb = new ABB();
        int[] vals = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};
        for (int v : vals) {
            abb.insertar(v, "E" + v, 8.0);
        }
        assertTrue(abb.eliminar(20));
        assertTrue(abb.eliminar(60));
        assertTrue(abb.eliminar(70));
        assertEquals(List.of(30, 35, 40, 45, 50, 65, 75, 80, 85), abb.recorridoInOrden());
    }

    @Test
    void eliminarConDosHijosUsaSucesorInorden() {
        ABB abb = new ABB();
        int[] vals = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};
        for (int v : vals) {
            abb.insertar(v, "E" + v, 8.0);
        }
        assertTrue(abb.eliminar(70));
        assertFalse(abb.buscar(70) != null);
    }

    @Test
    void alturaYMetricasCuentanCorrectamente() {
        ABB abb = new ABB();
        int[] vals = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};
        for (int v : vals) {
            abb.insertar(v, "E" + v, 8.0);
        }
        assertEquals(3, abb.altura());
        assertEquals(12, abb.totalNodos());
        assertEquals(6, abb.totalHojas());
        assertEquals(20, abb.minimoMatricula());
        assertEquals(85, abb.maximoMatricula());
    }

    @Test
    void promedioPonderadoCalculaMedia() {
        ABB abb = new ABB();
        abb.insertar(1, "A", 8.0);
        abb.insertar(2, "B", 9.0);
        abb.insertar(3, "C", 10.0);
        assertEquals(9.0, abb.promedioPonderado(), 0.0001);
    }

    @Test
    void validarInvarianteABB() {
        ABB abb = new ABB();
        int[] vals = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};
        for (int v : vals) {
            abb.insertar(v, "E" + v, 8.0);
        }
        assertTrue(abb.esInvarianteValida());
    }

    @Test
    void arbolVacioYSingleton() {
        ABB vacio = new ABB();
        assertEquals(-1, vacio.altura());
        assertEquals(0, vacio.totalNodos());
        assertEquals(-1, vacio.minimoMatricula());

        ABB singleton = new ABB();
        singleton.insertar(7, "Solo", 9.0);
        assertEquals(0, singleton.altura());
        assertEquals(1, singleton.totalNodos());
    }

    @Test
    void promedioInvalidoNoInserta() {
        ABB abb = new ABB();
        assertFalse(abb.insertar(10, "A", 11.0));
        assertNull(abb.buscar(10));
    }

    @Test
    void noSePermiteModificarPromedioInvalido() {
        ABB abb = new ABB();
        abb.insertar(10, "A", 8.0);
        assertFalse(abb.modificarEstudiante(10, "B", -1.0));
    }
}
