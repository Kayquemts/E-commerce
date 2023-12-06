package com.example.demo;

public class Compra {
    private double totalCompra;
    private int pontosUsados;

    public Compra(double totalCompra, int pontosUsados) {
        this.totalCompra = totalCompra;
        this.pontosUsados = pontosUsados;
    }

    // Getters e setters, se necessário...

    @Override
    public String toString() {
        return "Compra{" +
                "totalCompra=" + totalCompra +
                ", pontosUsados=" + pontosUsados +
                '}';
    }
}
