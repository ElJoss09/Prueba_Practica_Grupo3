package com.uta.academico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArbolGeneral {
    private Unidad raiz;

    public ArbolGeneral() {
        this.raiz = null;
    }

    public ArbolGeneral(Unidad raiz) {
        this.raiz = raiz;
    }

    public Unidad getRaiz() {
        return raiz;
    }

    public boolean agregarUnidad(String codigoPadre, String codigoUnidad, String nombre) {
        if (codigoUnidad == null || codigoUnidad.isBlank()) {
            return false;
        }

        String padre = codigoPadre == null ? null : codigoPadre.trim();
        String nuevo = codigoUnidad.trim();
        if (nuevo.isBlank()) {
            return false;
        }
        if (padre != null && padre.equalsIgnoreCase(nuevo)) {
            return false;
        }

        if (buscarPorCodigo(nuevo) != null) {
            return false;
        }

        if (raiz == null) {
            if (!"UTA".equalsIgnoreCase(nuevo)) {
                return false;
            }
            raiz = new Unidad(nuevo, nombre == null || nombre.isBlank() ? "UTA" : nombre.trim());
            return true;
        }

        if (padre == null || padre.isBlank()) {
            return false;
        }

        Unidad nodoPadre = buscarPorCodigo(padre);
        if (nodoPadre == null) {
            return false;
        }

        nodoPadre.agregarHijo(new Unidad(nuevo, nombre == null || nombre.isBlank() ? nuevo : nombre.trim()));
        return true;
    }

    public Unidad buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }
        return buscarRec(raiz, codigo.trim());
    }

    private Unidad buscarRec(Unidad actual, String codigo) {
        if (actual == null) {
            return null;
        }
        if (actual.getCodigo().equalsIgnoreCase(codigo)) {
            return actual;
        }
        for (Unidad hijo : actual.getHijos()) {
            Unidad encontrado = buscarRec(hijo, codigo);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public int contarNodos() {
        return contarNodosRec(raiz);
    }

    private int contarNodosRec(Unidad actual) {
        if (actual == null) {
            return 0;
        }
        int total = 1;
        for (Unidad hijo : actual.getHijos()) {
            total += contarNodosRec(hijo);
        }
        return total;
    }

    public int contarHojas() {
        return contarHojasRec(raiz);
    }

    private int contarHojasRec(Unidad actual) {
        if (actual == null) {
            return 0;
        }
        if (actual.getHijos().isEmpty()) {
            return 1;
        }
        int total = 0;
        for (Unidad hijo : actual.getHijos()) {
            total += contarHojasRec(hijo);
        }
        return total;
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(Unidad actual) {
        if (actual == null) {
            return -1;
        }
        if (actual.getHijos().isEmpty()) {
            return 0;
        }
        int mayor = -1;
        for (Unidad hijo : actual.getHijos()) {
            mayor = Math.max(mayor, alturaRec(hijo));
        }
        return mayor + 1;
    }

    public int nivel(String codigo) {
        if (raiz == null) {
            return -1;
        }
        Integer nivel = nivelRec(raiz, codigo, 0);
        return nivel == null ? -1 : nivel;
    }

    private Integer nivelRec(Unidad actual, String codigo, int nivelActual) {
        if (actual == null) {
            return null;
        }
        if (actual.getCodigo().equalsIgnoreCase(codigo)) {
            return nivelActual;
        }
        for (Unidad hijo : actual.getHijos()) {
            Integer nivel = nivelRec(hijo, codigo, nivelActual + 1);
            if (nivel != null) {
                return nivel;
            }
        }
        return null;
    }

    public int grado(String codigo) {
        Unidad actual = buscarPorCodigo(codigo);
        return actual == null ? -1 : actual.getHijos().size();
    }

    public boolean eliminarUnidad(String codigo) {
        if (raiz == null || codigo == null || codigo.isBlank()) {
            return false;
        }
        if (raiz.getCodigo().equalsIgnoreCase(codigo.trim())) {
            return false;
        }

        Unidad padre = buscarPadre(raiz, codigo.trim());
        if (padre == null) {
            return false;
        }

        Unidad nodo = buscarPorCodigo(codigo.trim());
        if (nodo == null || !nodo.esHoja()) {
            return false;
        }

        return padre.eliminarHijo(nodo);
    }

    private Unidad buscarPadre(Unidad actual, String codigo) {
        if (actual == null) {
            return null;
        }
        for (Unidad hijo : actual.getHijos()) {
            if (hijo.getCodigo().equalsIgnoreCase(codigo)) {
                return actual;
            }
            Unidad encontrado = buscarPadre(hijo, codigo);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public boolean esJerarquiaValida() {
        if (raiz == null) {
            return true;
        }
        Set<String> visitados = new HashSet<>();
        return validarRec(raiz, visitados);
    }

    private boolean validarRec(Unidad actual, Set<String> visitados) {
        if (actual == null) {
            return true;
        }
        if (!visitados.add(actual.getCodigo().trim())) {
            return false;
        }
        if (actual.getCodigo().isBlank()) {
            return false;
        }
        for (Unidad hijo : actual.getHijos()) {
            if (hijo == null || hijo.getCodigo() == null || hijo.getCodigo().isBlank()) {
                return false;
            }
            if (!validarRec(hijo, visitados)) {
                return false;
            }
        }
        return true;
    }

    public void imprimirJerarquia() {
        if (raiz == null) {
            System.out.println("Arbol vacio");
            return;
        }
        imprimirRec(raiz, 0);
    }

    private void imprimirRec(Unidad actual, int nivel) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }
        System.out.println(sb + "- " + actual.getCodigo() + " (" + actual.getNombre() + ")");
        for (Unidad hijo : actual.getHijos()) {
            imprimirRec(hijo, nivel + 1);
        }
    }

    public List<String> jerarquiaConSangria() {
        List<String> salida = new ArrayList<>();
        if (raiz == null) {
            return salida;
        }
        construirJerarquia(raiz, 0, salida);
        return salida;
    }

    private void construirJerarquia(Unidad actual, int nivel, List<String> salida) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }
        sb.append(actual.getCodigo());
        if (actual.getNombre() != null && !actual.getNombre().isBlank()) {
            sb.append(" - ").append(actual.getNombre());
        }
        salida.add(sb.toString());
        for (Unidad hijo : actual.getHijos()) {
            construirJerarquia(hijo, nivel + 1, salida);
        }
    }

    public List<String> transformacionHijoIzquierdoHermanoDerecho() {
        List<String> salida = new ArrayList<>();
        if (raiz == null) {
            return salida;
        }
        registrarTransformacion(raiz, salida);
        return salida;
    }

    private void registrarTransformacion(Unidad actual, List<String> salida) {
        List<Unidad> hijos = actual.getHijos();
        if (!hijos.isEmpty()) {
            salida.add(actual.getCodigo() + " -> hijoIzquierdo=" + hijos.get(0).getCodigo());
            for (int i = 0; i < hijos.size() - 1; i++) {
                salida.add(hijos.get(i).getCodigo() + " -> hermanoDerecho=" + hijos.get(i + 1).getCodigo());
            }
        }
        for (Unidad hijo : hijos) {
            registrarTransformacion(hijo, salida);
        }
    }

    public static ArbolGeneral crearJerarquiaInicial() {
        ArbolGeneral arbol = new ArbolGeneral();
        arbol.agregarUnidad(null, "UTA", "Universidad Técnica de Ambato");
        arbol.agregarUnidad("UTA", "FISEI", "Facultad de Ingeniería en Sistemas");
        arbol.agregarUnidad("UTA", "FCHE", "Facultad de Ciencias Humanas");
        arbol.agregarUnidad("UTA", "FCS", "Facultad de Ciencias de la Salud");
        arbol.agregarUnidad("FISEI", "SOFTWARE", "Software");
        arbol.agregarUnidad("FISEI", "TI", "Tecnologías de la Información");
        arbol.agregarUnidad("SOFTWARE", "3A", "3A");
        arbol.agregarUnidad("SOFTWARE", "3B", "3B");
        arbol.agregarUnidad("TI", "4A", "4A");
        return arbol;
    }
}
