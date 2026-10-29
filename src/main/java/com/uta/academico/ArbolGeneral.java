package com.uta.academico;

import java.util.ArrayList;
import java.util.List;

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
            if (padre != null && !padre.isBlank()) return false;
            raiz = new Unidad(nuevo, nombre == null || nombre.isBlank() ? nuevo : nombre.trim());
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
        return validarRec(raiz, new ArrayList<>(), new ArrayList<>());
    }

    private boolean validarRec(Unidad actual, List<String> visitados, List<Unidad> camino) {
        if (actual == null || actual.getCodigo() == null || actual.getCodigo().isBlank()) return false;
        String codigo = actual.getCodigo().trim().toUpperCase();
        if (camino.contains(actual) || visitados.contains(codigo)) return false;
        visitados.add(codigo);
        camino.add(actual);
        for (Unidad hijo : actual.getHijos()) {
            if (hijo == null || !validarRec(hijo, visitados, camino)) return false;
        }
        camino.remove(camino.size() - 1);
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

    /** Construye y devuelve el árbol binario equivalente, no solo una descripción textual. */
    public NodoBinarioGeneral convertirHijoIzquierdoHermanoDerecho() {
        return convertirRec(raiz);
    }

    private NodoBinarioGeneral convertirRec(Unidad unidad) {
        if (unidad == null) return null;
        NodoBinarioGeneral nodo = new NodoBinarioGeneral(unidad.getCodigo(), unidad.getNombre());
        NodoBinarioGeneral anterior = null;
        for (Unidad hijo : unidad.getHijos()) {
            NodoBinarioGeneral convertido = convertirRec(hijo);
            if (anterior == null) nodo.setHijoIzquierdo(convertido);
            else anterior.setHermanoDerecho(convertido);
            anterior = convertido;
        }
        return nodo;
    }

    public List<String> transformacionHijoIzquierdoHermanoDerecho() {
        List<String> salida = new ArrayList<>();
        NodoBinarioGeneral binario = convertirHijoIzquierdoHermanoDerecho();
        imprimirBinarioRec(binario, salida);
        return salida;
    }

    private void imprimirBinarioRec(NodoBinarioGeneral nodo, List<String> salida) {
        if (nodo == null) return;
        if (nodo.getHijoIzquierdo() != null) salida.add(nodo.getCodigo() + " -> hijoIzquierdo=" + nodo.getHijoIzquierdo().getCodigo());
        if (nodo.getHermanoDerecho() != null) salida.add(nodo.getCodigo() + " -> hermanoDerecho=" + nodo.getHermanoDerecho().getCodigo());
        imprimirBinarioRec(nodo.getHijoIzquierdo(), salida);
        imprimirBinarioRec(nodo.getHermanoDerecho(), salida);
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
