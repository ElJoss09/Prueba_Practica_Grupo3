package com.uta.academico;

public class Estudiante {
    private final int matricula;
    private String nombre;
    private double promedio;

    public Estudiante(int matricula, String nombre, double promedio) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.promedio = promedio;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return "Estudiante{matricula=" + matricula + ", nombre='" + nombre + '\'' + ", promedio=" + promedio + '}';
    }
}
