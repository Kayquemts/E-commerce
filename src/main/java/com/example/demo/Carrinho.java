package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private String cliente;
    private List<Produto> produtos;
    private List<Integer> quantidades;
    private String dataAtual;
    private int num_produtos;

    public Carrinho(String cliente) {
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
        this.quantidades = new ArrayList<>();

        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Converter a data para string
        this.dataAtual = data.format(formatter);

        this.num_produtos = 0;
    }

    public String getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(String dataAtual) {
        this.dataAtual = dataAtual;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Produto getProdutos(int i) {
        return produtos.get(i);
    }

    public void setProdutos(Produto produto) {
        this.produtos.add(produto);
        this.num_produtos++;
    }

    public void setProdutos(Produto produto, Integer quantidade) {
        this.produtos.add(produto);
        this.quantidades.add(quantidade);
        this.num_produtos++;
    }
    public int getQuantProd(){
        return num_produtos;
    }



    public void setQuantidades(List<Integer> quantidades) {
        this.quantidades = quantidades;
    }

    public int getQuantidades(int i) {
        return quantidades.get(i);
    }

    public void setQuantidades(Integer quantidade) {
        this.quantidades.add(quantidade);
    }
    public void clear(){
        this.produtos.clear();
        this.quantidades.clear();
    }
    public double calcularTotalGasto(){
        double total = 0;

        for(int i = 0; i < this.num_produtos;i++){
            total += this.getProdutos(i).getPreco()*this.quantidades.get(i);
        }
        return total;
    }

    public int calcularTotalPontos(){
        int total = 0;

        for(int i = 0; i < num_produtos; i++){
            total += getProdutos(i).getPontos()*quantidades.get(i);
        }
        return total;
    }
}
