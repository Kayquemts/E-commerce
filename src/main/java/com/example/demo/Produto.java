package com.example.demo;

public class Produto {
    // Atributos
    private String nome;
    private String categoria;
    private double preco;
    private double desconto;
    private int quantidadeEmEstoque;
    private int pontos;

    // Construtor
    public Produto(String nome, String categoria, double preco, double desconto, int quantidadeEmEstoque, int pontos) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.desconto = desconto;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.pontos = pontos; // Adicionando o atributo "pontos" ao construtor
    }
    // Métodos Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
