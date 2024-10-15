package com.hectorgc.carrodecompra;

public class Producto {
    private String nombre;


    private int valor;

    public Producto(int valor, String nombre) {
        this.valor = valor;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
