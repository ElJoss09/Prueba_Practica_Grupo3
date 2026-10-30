package com.uta.academico;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuAuditoria {
    private final Scanner scanner = new Scanner(System.in);
    private final ArbolGeneral arbolGeneral = ArbolGeneral.crearJerarquiaInicial();
    private final ABB abb = construirAbbBase();

    public static void main(String[] args) {
        MenuAuditoria menu = new MenuAuditoria();
        menu.iniciar();
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== SISTEMA DE AUDITORÍA JERÁRQUICA Y ACADÉMICA UTA ===");
            System.out.println("1. Mostrar jerarquía general");
            System.out.println("2. Agregar unidad");
            System.out.println("3. Buscar unidad");
            System.out.println("4. Eliminar unidad segura");
            System.out.println("5. Información general");
            System.out.println("6. Construir ABB base");
            System.out.println("7. Insertar matrícula");
            System.out.println("8. Buscar matrícula");
            System.out.println("9. Eliminar matrícula");
            System.out.println("10. Mostrar recorridos ABB");
            System.out.println("11. Auditoría ABB");
            System.out.println("12. Comparación de búsquedas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> mostrarJerarquiaGeneral();
                case 2 -> agregarUnidadMenu();
                case 3 -> buscarUnidadMenu();
                case 4 -> eliminarUnidadMenu();
                case 5 -> mostrarResumenGeneral();
                case 6 -> reconstruirAbb();
                case 7 -> agregarMatriculaMenu();
                case 8 -> buscarMatriculaMenu();
                case 9 -> eliminarMatriculaMenu();
                case 10 -> mostrarRecorridosAbb();
                case 11 -> mostrarAuditoriaAbb();
                case 12 -> compararBusqueda();
                case 0 -> {
                    System.out.println("Hasta luego.");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private ABB construirAbbBase() {
        ABB arbol = new ABB();
        int[] claves = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};
        for (int clave : claves) {
            arbol.insertar(clave, "Estudiante " + clave, 7.0 + (clave % 4));
        }
        return arbol;
    }

    private void mostrarJerarquiaGeneral() {
        List<String> jerarquia = arbolGeneral.jerarquiaConSangria();
        if (jerarquia.isEmpty()) {
            System.out.println("Árbol general vacío.");
            return;
        }
        for (String linea : jerarquia) {
            System.out.println(linea);
        }
    }

    private void agregarUnidadMenu() {
        System.out.print("Código del padre: ");
        String padre = scanner.nextLine();
        System.out.print("Código de la unidad: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        boolean ok = arbolGeneral.agregarUnidad(padre, codigo, nombre);
        System.out.println(ok ? "Unidad agregada correctamente." : "No se pudo agregar la unidad.");
    }

    private void buscarUnidadMenu() {
        System.out.print("Código a buscar: ");
        String codigo = scanner.nextLine();
        Unidad unidad = arbolGeneral.buscarPorCodigo(codigo);
        System.out.println(unidad == null ? "No encontrada." : "Encontrada: " + unidad.getCodigo() + " - " + unidad.getNombre());
    }

    private void eliminarUnidadMenu() {
        System.out.print("Código para eliminar: ");
        String codigo = scanner.nextLine();
        boolean ok = arbolGeneral.eliminarUnidad(codigo);
        System.out.println(ok ? "Unidad eliminada." : "No se pudo eliminar la unidad (solo hojas y no raíz). ");
    }

    private void mostrarResumenGeneral() {
        System.out.println("Total de unidades: " + arbolGeneral.contarNodos());
        System.out.println("Total de hojas: " + arbolGeneral.contarHojas());
        System.out.println("Altura general: " + arbolGeneral.altura());
        System.out.println("Jerarquía válida: " + arbolGeneral.esJerarquiaValida());
        System.out.println("Transformación hijo-izquierdo/hermano-derecho:");
        for (String linea : arbolGeneral.transformacionHijoIzquierdoHermanoDerecho()) {
            System.out.println("- " + linea);
        }
    }

    private void reconstruirAbb() {
        ABB nuevo = construirAbbBase();
        System.out.println("ABB reconstruido con la secuencia base.");
        mostrarLista(nuevo.recorridoInOrden());
    }

    private void agregarMatriculaMenu() {
        System.out.print("Matrícula: ");
        int matricula = leerEntero();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Promedio: ");
        double promedio = Double.parseDouble(scanner.nextLine());
        boolean ok = abb.insertar(matricula, nombre, promedio);
        System.out.println(ok ? "Matrícula insertada." : "La matrícula ya existe o el promedio es inválido.");
    }

    private void buscarMatriculaMenu() {
        System.out.print("Matrícula a buscar: ");
        int matricula = leerEntero();
        Estudiante estudiante = abb.buscar(matricula);
        System.out.println(estudiante == null ? "No encontrada." : estudiante);
    }

    private void eliminarMatriculaMenu() {
        System.out.print("Matrícula a eliminar: ");
        int matricula = leerEntero();
        boolean ok = abb.eliminar(matricula);
        System.out.println(ok ? "Matrícula eliminada." : "No se encontró la matrícula.");
    }

    private void mostrarRecorridosAbb() {
        System.out.println("Preorden: " + abb.recorridoPreOrden());
        System.out.println("Inorden: " + abb.recorridoInOrden());
        System.out.println("Postorden: " + abb.recorridoPostOrden());
    }

    private void mostrarAuditoriaAbb() {
        System.out.println("Total de nodos: " + abb.totalNodos());
        System.out.println("Total de hojas: " + abb.totalHojas());
        System.out.println("Altura ABB: " + abb.altura());
        System.out.println("Matrícula menor: " + abb.minimoMatricula());
        System.out.println("Matrícula mayor: " + abb.maximoMatricula());
        System.out.println("Promedio ponderado: " + abb.promedioPonderado());
        System.out.println("Invariante ABB válida: " + abb.esInvarianteValida());
    }

    private void compararBusqueda() {
        ABB ascendente = ABB.construirAscendente();
        ABB balanceado = ABB.construirEquilibrado();
        int[] exist = {50, 35, 80};
        int[] noExist = {15, 55, 90};
        ABB.ResultadoComparacionBusqueda r1 = ascendente.analizarBusqueda(exist, noExist);
        ABB.ResultadoComparacionBusqueda r2 = balanceado.analizarBusqueda(exist, noExist);
        System.out.println("Ascendente: exist=" + r1.getComparacionesExistente() + ", noExist=" + r1.getComparacionesNoExistente());
        System.out.println("Balanceado: exist=" + r2.getComparacionesExistente() + ", noExist=" + r2.getComparacionesNoExistente());
    }

    private void mostrarLista(List<Integer> lista) {
        System.out.println(lista);
    }

    private int leerEntero() {
        String entrada = scanner.nextLine();
        try {
            return Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
