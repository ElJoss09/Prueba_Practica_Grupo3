package com.uta.academico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Unidad {
    private final String codigo;
    private final String nombre;
    private final List<Unidad> hijos;

    public Unidad(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre == null ? "" : nombre;
        this.hijos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Unidad> getHijos() {
        return Collections.unmodifiableList(hijos);
    }

    public void agregarHijo(Unidad hijo) {
        if (hijo == null) {
            throw new IllegalArgumentException("No se puede agregar un hijo nulo");
        }
        hijos.add(hijo);
    }

    public boolean eliminarHijo(Unidad hijo) {
        return hijos.remove(hijo);
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    @Override
    public String toString() {
        return codigo + "(" + nombre + ")";
    }
}
