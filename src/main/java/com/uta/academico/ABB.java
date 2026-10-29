package com.uta.academico;

import java.util.ArrayList;
import java.util.List;

public class ABB {
    private NodoABB raiz;

    public ABB() {
        this.raiz = null;
    }

    public NodoABB getRaiz() {
        return raiz;
    }

    public boolean insertar(int matricula, String nombre, double promedio) {
        if (!esPromedioValido(promedio)) {
            return false;
        }
        if (buscar(matricula) != null) {
            return false;
        }
        raiz = insertarRec(raiz, matricula, nombre, promedio);
        return true;
    }

    private NodoABB insertarRec(NodoABB actual, int matricula, String nombre, double promedio) {
        if (actual == null) {
            return new NodoABB(matricula, nombre, promedio);
        }
        if (matricula < actual.getMatricula()) {
            actual.setIzquierda(insertarRec(actual.getIzquierda(), matricula, nombre, promedio));
        } else if (matricula > actual.getMatricula()) {
            actual.setDerecha(insertarRec(actual.getDerecha(), matricula, nombre, promedio));
        }
        return actual;
    }

    public Estudiante buscar(int matricula) {
        return buscarRec(raiz, matricula);
    }

    private Estudiante buscarRec(NodoABB actual, int matricula) {
        if (actual == null) {
            return null;
        }
        if (matricula == actual.getMatricula()) {
            return actual.getEstudiante();
        }
        if (matricula < actual.getMatricula()) {
            return buscarRec(actual.getIzquierda(), matricula);
        }
        return buscarRec(actual.getDerecha(), matricula);
    }

    public List<Integer> recorridoPreOrden() {
        List<Integer> salida = new ArrayList<>();
        preOrdenRec(raiz, salida);
        return salida;
    }

    private void preOrdenRec(NodoABB actual, List<Integer> salida) {
        if (actual == null) {
            return;
        }
        salida.add(actual.getMatricula());
        preOrdenRec(actual.getIzquierda(), salida);
        preOrdenRec(actual.getDerecha(), salida);
    }

    public List<Integer> recorridoInOrden() {
        List<Integer> salida = new ArrayList<>();
        inOrdenRec(raiz, salida);
        return salida;
    }

    private void inOrdenRec(NodoABB actual, List<Integer> salida) {
        if (actual == null) {
            return;
        }
        inOrdenRec(actual.getIzquierda(), salida);
        salida.add(actual.getMatricula());
        inOrdenRec(actual.getDerecha(), salida);
    }

    public List<Integer> recorridoPostOrden() {
        List<Integer> salida = new ArrayList<>();
        postOrdenRec(raiz, salida);
        return salida;
    }

    private void postOrdenRec(NodoABB actual, List<Integer> salida) {
        if (actual == null) {
            return;
        }
        postOrdenRec(actual.getIzquierda(), salida);
        postOrdenRec(actual.getDerecha(), salida);
        salida.add(actual.getMatricula());
    }

    public boolean modificarEstudiante(int matricula, String nuevoNombre, double nuevoPromedio) {
        if (!esPromedioValido(nuevoPromedio)) {
            return false;
        }
        NodoABB actual = buscarNodo(raiz, matricula);
        if (actual == null) {
            return false;
        }
        actual.getEstudiante().setNombre(nuevoNombre == null ? "" : nuevoNombre);
        actual.getEstudiante().setPromedio(nuevoPromedio);
        return true;
    }

    public boolean eliminar(int matricula) {
        if (raiz == null) {
            return false;
        }
        if (buscar(matricula) == null) return false;
        raiz = eliminarRec(raiz, matricula);
        return true;
    }

    private NodoABB eliminarRec(NodoABB actual, int matricula) {
        if (actual == null) {
            return null;
        }
        if (matricula < actual.getMatricula()) {
            actual.setIzquierda(eliminarRec(actual.getIzquierda(), matricula));
            return actual;
        }
        if (matricula > actual.getMatricula()) {
            actual.setDerecha(eliminarRec(actual.getDerecha(), matricula));
            return actual;
        }

        if (actual.getIzquierda() == null && actual.getDerecha() == null) {
            return null;
        }
        if (actual.getIzquierda() == null) {
            return actual.getDerecha();
        }
        if (actual.getDerecha() == null) {
            return actual.getIzquierda();
        }

        NodoABB sucesor = minimo(actual.getDerecha());
        actual.setEstudiante(new Estudiante(sucesor.getMatricula(), sucesor.getEstudiante().getNombre(), sucesor.getEstudiante().getPromedio()));
        actual.setDerecha(eliminarRec(actual.getDerecha(), sucesor.getMatricula()));
        return actual;
    }

    private NodoABB minimo(NodoABB actual) {
        while (actual != null && actual.getIzquierda() != null) {
            actual = actual.getIzquierda();
        }
        return actual;
    }

    private NodoABB buscarNodo(NodoABB actual, int matricula) {
        if (actual == null) {
            return null;
        }
        if (matricula == actual.getMatricula()) {
            return actual;
        }
        if (matricula < actual.getMatricula()) {
            return buscarNodo(actual.getIzquierda(), matricula);
        }
        return buscarNodo(actual.getDerecha(), matricula);
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoABB actual) {
        if (actual == null) {
            return -1;
        }
        int izq = alturaRec(actual.getIzquierda());
        int der = alturaRec(actual.getDerecha());
        return Math.max(izq, der) + 1;
    }

    public int totalNodos() {
        return contarNodosRec(raiz);
    }

    private int contarNodosRec(NodoABB actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + contarNodosRec(actual.getIzquierda()) + contarNodosRec(actual.getDerecha());
    }

    public int totalHojas() {
        return contarHojasRec(raiz);
    }

    private int contarHojasRec(NodoABB actual) {
        if (actual == null) {
            return 0;
        }
        if (actual.getIzquierda() == null && actual.getDerecha() == null) {
            return 1;
        }
        return contarHojasRec(actual.getIzquierda()) + contarHojasRec(actual.getDerecha());
    }

    public int minimoMatricula() {
        if (raiz == null) {
            return -1;
        }
        return minimo(raiz).getMatricula();
    }

    public int maximoMatricula() {
        if (raiz == null) {
            return -1;
        }
        NodoABB actual = raiz;
        while (actual.getDerecha() != null) {
            actual = actual.getDerecha();
        }
        return actual.getMatricula();
    }

    public double promedioPonderado() {
        if (raiz == null) {
            return 0.0;
        }
        return sumaPromediosRec(raiz) / (double) totalNodos();
    }

    private double sumaPromediosRec(NodoABB actual) {
        if (actual == null) {
            return 0.0;
        }
        return actual.getEstudiante().getPromedio() + sumaPromediosRec(actual.getIzquierda()) + sumaPromediosRec(actual.getDerecha());
    }

    public boolean esInvarianteValida() {
        return esInvarianteRec(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean esInvarianteRec(NodoABB actual, int min, int max) {
        if (actual == null) {
            return true;
        }
        if (actual.getMatricula() <= min || actual.getMatricula() >= max) {
            return false;
        }
        return esInvarianteRec(actual.getIzquierda(), min, actual.getMatricula())
                && esInvarianteRec(actual.getDerecha(), actual.getMatricula(), max);
    }

    public static ABB construirAscendente() {
        ABB arbol = new ABB();
        for (int v = 1; v <= 127; v++) arbol.insertar(v, "Estudiante" + v, 8.0);
        return arbol;
    }

    public static ABB construirEquilibrado() {
        ABB arbol = new ABB();
        insertarRangoEquilibrado(arbol, 1, 127);
        return arbol;
    }

    private static void insertarRangoEquilibrado(ABB arbol, int inicio, int fin) {
        if (inicio > fin) return;
        int medio = inicio + (fin - inicio) / 2;
        arbol.insertar(medio, "Estudiante" + medio, 8.0);
        insertarRangoEquilibrado(arbol, inicio, medio - 1);
        insertarRangoEquilibrado(arbol, medio + 1, fin);
    }

    public static class ResultadoComparacionBusqueda {
        private final int comparacionesExistente;
        private final int comparacionesNoExistente;
        private final double promedioExistente;
        private final double promedioNoExistente;

        public ResultadoComparacionBusqueda(int comparacionesExistente, int comparacionesNoExistente,
                                          double promedioExistente, double promedioNoExistente) {
            this.comparacionesExistente = comparacionesExistente;
            this.comparacionesNoExistente = comparacionesNoExistente;
            this.promedioExistente = promedioExistente;
            this.promedioNoExistente = promedioNoExistente;
        }

        public int getComparacionesExistente() {
            return comparacionesExistente;
        }

        public int getComparacionesNoExistente() {
            return comparacionesNoExistente;
        }

        public double getPromedioExistente() {
            return promedioExistente;
        }

        public double getPromedioNoExistente() {
            return promedioNoExistente;
        }
    }

    public ResultadoComparacionBusqueda analizarBusqueda(int[] clavesExistentes, int[] clavesNoExistentes) {
        int totalExist = 0;
        int totalNoExist = 0;
        for (int clave : clavesExistentes) {
            totalExist += contarComparacionesBusqueda(raiz, clave, true);
        }
        for (int clave : clavesNoExistentes) {
            totalNoExist += contarComparacionesBusqueda(raiz, clave, false);
        }
        return new ResultadoComparacionBusqueda(totalExist, totalNoExist,
                clavesExistentes.length == 0 ? 0 : totalExist / (double) clavesExistentes.length,
                clavesNoExistentes.length == 0 ? 0 : totalNoExist / (double) clavesNoExistentes.length);
    }

    private int contarComparacionesBusqueda(NodoABB actual, int clave, boolean busquedaExistente) {
        int comparaciones = 0;
        NodoABB temp = actual;
        while (temp != null) {
            comparaciones++;
            if (clave == temp.getMatricula()) {
                return comparaciones;
            }
            if (clave < temp.getMatricula()) {
                temp = temp.getIzquierda();
            } else {
                temp = temp.getDerecha();
            }
        }
        return comparaciones;
    }

    public static boolean esPromedioValido(double promedio) {
        return promedio >= 0.0 && promedio <= 10.0;
    }

    public static class NodoABB {
        private int matricula;
        private Estudiante estudiante;
        private NodoABB izquierda;
        private NodoABB derecha;

        public NodoABB(int matricula, String nombre, double promedio) {
            this.matricula = matricula;
            this.estudiante = new Estudiante(matricula, nombre, promedio);
        }

        public int getMatricula() {
            return matricula;
        }

        public Estudiante getEstudiante() {
            return estudiante;
        }

        public void setEstudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
            if (estudiante != null) {
                this.matricula = estudiante.getMatricula();
            }
        }

        public NodoABB getIzquierda() {
            return izquierda;
        }

        public void setIzquierda(NodoABB izquierda) {
            this.izquierda = izquierda;
        }

        public NodoABB getDerecha() {
            return derecha;
        }

        public void setDerecha(NodoABB derecha) {
            this.derecha = derecha;
        }
    }
}
