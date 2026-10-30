package com.uta.academico;

/** Nodo de la representación hijo-izquierdo/hermano-derecho de un árbol general. */
public class NodoBinarioGeneral {
    private final String codigo;
    private final String nombre;
    private NodoBinarioGeneral hijoIzquierdo;
    private NodoBinarioGeneral hermanoDerecho;

    public NodoBinarioGeneral(String codigo, String nombre) { this.codigo = codigo; this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public NodoBinarioGeneral getHijoIzquierdo() { return hijoIzquierdo; }
    public NodoBinarioGeneral getHermanoDerecho() { return hermanoDerecho; }
    void setHijoIzquierdo(NodoBinarioGeneral nodo) { hijoIzquierdo = nodo; }
    void setHermanoDerecho(NodoBinarioGeneral nodo) { hermanoDerecho = nodo; }
}
