package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Produto> produtos;
    private List<Integer> quantidades;
    private int num_produtos;

    public Carrinho() {
        this.produtos = new ArrayList<>();
        this.quantidades = new ArrayList<>();
        this.num_produtos =0;
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
}
