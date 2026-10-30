package com.uta.academico;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class ArbolGeneralTest {

    @Test
    void debeCrearRaizUTA() {
        ArbolGeneral arbol = new ArbolGeneral();
        assertTrue(arbol.agregarUnidad(null, "UTA", "Universidad Técnica de Ambato"));
        assertEquals("UTA", arbol.getRaiz().getCodigo());
    }

    @Test
    void noPermitePadreInexistente() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertFalse(arbol.agregarUnidad("INEXISTENTE", "NUEVA", "Nueva unidad"));
    }

    @Test
    void noPermiteDuplicados() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertFalse(arbol.agregarUnidad("UTA", "FISEI", "Duplicado"));
    }

    @Test
    void buscarPorCodigoRetornaUnidad() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        Unidad unidad = arbol.buscarPorCodigo("3B");
        assertNotNull(unidad);
        assertEquals("3B", unidad.getCodigo());
    }

    @Test
    void nivelYGradoSonCorrectos() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertEquals(0, arbol.nivel("UTA"));
        assertEquals(1, arbol.nivel("FISEI"));
        assertEquals(3, arbol.nivel("3B"));
        assertEquals(2, arbol.grado("FISEI"));
        assertEquals(2, arbol.grado("SOFTWARE"));
    }

    @Test
    void alturaNodosYHojasDeJerarquiaInicial() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertEquals(9, arbol.contarNodos());
        assertEquals(5, arbol.contarHojas());
        assertEquals(3, arbol.altura());
    }

    @Test
    void eliminarUnidadNoHojaRechaza() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertFalse(arbol.eliminarUnidad("FISEI"));
        assertNotNull(arbol.buscarPorCodigo("FISEI"));
    }

    @Test
    void eliminarUnidadHojaFunciona() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertTrue(arbol.eliminarUnidad("4A"));
        assertNull(arbol.buscarPorCodigo("4A"));
    }

    @Test
    void noPermiteEliminarRaiz() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        assertFalse(arbol.eliminarUnidad("UTA"));
    }

    @Test
    void jerarquiaConSangriaGeneraSalidas() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        List<String> salida = arbol.jerarquiaConSangria();
        assertFalse(salida.isEmpty());
        assertTrue(salida.get(0).startsWith("UTA"));
        assertTrue(salida.stream().anyMatch(linea -> linea.contains("3B")));
    }

    @Test
    void transformacionHijoIzquierdoHermanoDerecho() {
        ArbolGeneral arbol = ArbolGeneral.crearJerarquiaInicial();
        List<String> salida = arbol.transformacionHijoIzquierdoHermanoDerecho();
        assertTrue(salida.stream().anyMatch(linea -> linea.contains("UTA -> hijoIzquierdo=FISEI")));
        assertTrue(salida.stream().anyMatch(linea -> linea.contains("FISEI -> hermanoDerecho=FCHE")) || salida.stream().anyMatch(linea -> linea.contains("FISEI -> hijoIzquierdo=SOFTWARE")));
    }

    @Test
    void jerarquiaVaciaEsValida() {
        ArbolGeneral arbol = new ArbolGeneral();
        assertTrue(arbol.esJerarquiaValida());
        assertEquals(0, arbol.contarNodos());
    }
}
