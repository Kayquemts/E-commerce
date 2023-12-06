package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// ...

public class Produto {
    private String nome;
    private String categoria;
    private double preco;
    private double desconto;
    private int quantidadeEmEstoque;
    private int pontos;

    public Produto(String nome, String categoria, double preco, double desconto, int quantidadeEmEstoque, int pontos) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.desconto = desconto;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.pontos = pontos;
    }

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



    // Construtores, getters e setters

    // Exemplo de construtor:


    // ...

    public static List<Produto> carregarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = carregarProdutosDoArquivo();
        List<Produto> produtosDaCategoria = new ArrayList<>();

        for (Produto produto : produtos) {
            if (produto.getCategoria().equalsIgnoreCase(categoria)) {
                produtosDaCategoria.add(produto);
            }
        }

        return produtosDaCategoria;
    }

    private static List<Produto> carregarProdutosDoArquivo() {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type produtoList = new TypeToken<List<Produto>>() {}.getType();
        Path caminhoArquivo = Paths.get(filePath, "Produtos.json");
        List<Produto> listaProdutos = new ArrayList<>();

        try (Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProdutos = gson.fromJson(reader, produtoList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaProdutos;
    }

    public void atualizarEstoque(int quantidadeVendida) {
        if (quantidadeVendida <= quantidadeEmEstoque) {
            quantidadeEmEstoque -= quantidadeVendida;
            System.out.println("Estoque atualizado para " + quantidadeEmEstoque + " unidades.");
        } else {
            System.out.println("Erro: Quantidade vendida maior que a disponível em estoque.");
        }
    }

    // ...
}