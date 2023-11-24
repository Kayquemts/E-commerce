
package com.example.demo;

//Bibliotecas Utilizadas
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application; // a nossa clase extende da Application
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;



public class AdminTela extends Application {

    private Stage primaryStage;//janela principal que dar o nome do e-commerce e sair
    private VBox root; //criando a Vbox que organiza os elementos
    //ArrayList para armazenar os clientes
    private ArrayList<Cliente> listaClientes = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sistema de e-commerce");

        //configurando a Vbox
        root = new VBox();
        root.setSpacing(10); // aqui vai dar um espaço de 10 pixels
        root.setAlignment(Pos.CENTER); //aqui alinhamos ao centro


        setupAdminScene();

        primaryStage.setScene(new Scene(root, 300, 250));// largura e comprimento
        primaryStage.show();
    }

    //Função da Tela Inicial


    //Função do menu do Admin
    private void setupAdminScene() {
        root.getChildren().clear();
        System.out.println("Você se conectou como usuário ADMIN");

        Label msg3 = new Label("O que deseja fazer:");
        Button insertButton = new Button("Inserir produto");
        Button alterButton = new Button("Alterar produto");
        Button removeButton = new Button("Remover produto");
        Button updateButton = new Button("Atualizar estoque");
        Button admReportsButton = new Button("Exibir relatórios");
        Button admExitButton = new Button("Sair do menu admin");

        insertButton.setOnAction(event -> {
            Stage inserirProdutoStage = new Stage();
            inserirProdutoStage.setTitle("Inserir Produto");

            Label labelNome = new Label("Nome do Produto:");
            TextField textFieldNome = new TextField();
            //esse TextField é onde colocamos o texto

            Label labelCategoria = new Label("Categoria:");
            TextField textFieldCategoria = new TextField();

            Label labelPreco = new Label("Preço:");
            TextField textFieldPreco = new TextField();

            Label labelDesconto = new Label("Desconto:");
            TextField textFieldDesconto = new TextField();

            Label labelQuantidade = new Label("Quantidade em Estoque:");
            TextField textFieldQuantidade = new TextField();

            Label labelPontos = new Label("Pontos:");
            TextField textFieldPontos = new TextField();

            Button salvarButton = new Button("Salvar");

            VBox inserirProdutoLayout = new VBox(10);// esse 10 é a qtd de espaço
            //mesmo equema de organizar os botões na Vbox
            inserirProdutoLayout.getChildren().addAll(labelNome, textFieldNome,
                    labelCategoria, textFieldCategoria, labelPreco, textFieldPreco,
                    labelDesconto, textFieldDesconto, labelQuantidade,
                    textFieldQuantidade,labelPontos, textFieldPontos, salvarButton);
            inserirProdutoLayout.setAlignment(Pos.CENTER);

            inserirProdutoLayout.setAlignment(Pos.CENTER);

            salvarButton.setOnAction(saveEvent -> {
                //aqui usamos os get pra pegar o texto escrito
                String nome = textFieldNome.getText();
                String categoria = textFieldCategoria.getText();
                double preco = Double.parseDouble(textFieldPreco.getText());
                double desconto = Double.parseDouble(textFieldDesconto.getText());
                int quantidadeEmEstoque = Integer.parseInt(textFieldQuantidade.getText());
                int pontos = Integer.parseInt(textFieldPontos.getText());

                //aqui vai adiconando no nosso Array lá de Produtos
                Produto novoProduto = new Produto(nome, categoria, preco,
                        desconto, quantidadeEmEstoque, pontos);


                // Chamada da função de salvar no arquivo
                salvarProdutosEmArquivo(novoProduto);

                inserirProdutoStage.close();
            });
            //configuração de largura e comprimento
            Scene inserirProdutoScene = new Scene(inserirProdutoLayout, 500, 500);
            inserirProdutoStage.setScene(inserirProdutoScene);
            inserirProdutoStage.show();
        });

        alterButton.setOnAction(event -> {
            root.getChildren().clear();
            System.out.println("Você escolheu alterar um produto.");

            Label msgSubmenu = new Label("Escolha como deseja alterar o produto:");
            Button alterarPorNomeButton = new Button("Alterar por Nome");
            Button voltarButton = new Button("Voltar");

            alterarPorNomeButton.setOnAction(e -> {
                Stage alterarProdutoStage = new Stage();
                alterarProdutoStage.setTitle("Alterar Produto por Nome");

                Label labelNomeAntigo = new Label("Nome do Produto Antigo:");
                TextField textFieldNomeAntigo = new TextField();

                Label labelNomeNovo = new Label("Nome do Produto Novo:");
                TextField textFieldNomeNovo = new TextField();

                Label labelCategoria = new Label("Nova Categoria:");
                TextField textFieldCategoria = new TextField();

                Label labelPreco = new Label("Novo Preço:");
                TextField textFieldPreco = new TextField();

                Label labelDesconto = new Label("Novo Desconto:");
                TextField textFieldDesconto = new TextField();

                Label labelQuantidade = new Label("Nova Quantidade em Estoque:");
                TextField textFieldQuantidade = new TextField();

                Label labelPontos = new Label("Novos Pontos:");
                TextField textFieldPontos = new TextField();

                Button salvarButton = new Button("Salvar Alterações");

                VBox alterarProdutoLayout = new VBox(10);
                alterarProdutoLayout.getChildren().addAll(labelNomeAntigo,
                        textFieldNomeAntigo, labelNomeNovo,
                        textFieldNomeNovo, labelCategoria, textFieldCategoria,
                        labelPreco, textFieldPreco, labelDesconto,
                        textFieldDesconto, labelQuantidade, textFieldQuantidade,
                        labelPontos, textFieldPontos, salvarButton);
                alterarProdutoLayout.setAlignment(Pos.CENTER);

                alterarProdutoLayout.setAlignment(Pos.CENTER);

                salvarButton.setOnAction(saveEvent -> {

                    String nomeAntigo = textFieldNomeAntigo.getText();

                    String nomeNovo = textFieldNomeNovo.getText();
                    String categoria = textFieldCategoria.getText();
                    double preco = Double.parseDouble(textFieldPreco.getText());
                    double desconto = Double.parseDouble(textFieldDesconto.getText());
                    int quantidadeEmEstoque = Integer.parseInt(textFieldQuantidade.getText());
                    int pontos = Integer.parseInt(textFieldPontos.getText());

                    Produto produtoModificado = new Produto(nomeNovo, categoria,
                            preco, desconto, quantidadeEmEstoque, pontos);
                    alterarProdutoNoArquivo(nomeAntigo, produtoModificado);

                    alterarProdutoStage.close();
                });

                Scene alterarProdutoScene = new Scene(alterarProdutoLayout, 450, 450);
                alterarProdutoStage.setScene(alterarProdutoScene);
                alterarProdutoStage.show();
            });

            // alterarPorCategoriaButton.setOnAction(e -> {
            // Lógica para alterar por categoria
            // });

            voltarButton.setOnAction(e -> setupAdminScene());

            root.getChildren().addAll(msgSubmenu, alterarPorNomeButton, voltarButton);
        });

        removeButton.setOnAction(event -> {
            root.getChildren().clear();
            System.out.println("Você escolheu remover um produto.");

            Label msgSubmenu = new Label("Escolha como deseja remover o produto:");
            Button removerPorNomeButton = new Button("Remover por Nome");
            Button voltarButton = new Button("Voltar");

            removerPorNomeButton.setOnAction(e -> {
                Stage removerProdutoStage = new Stage();
                removerProdutoStage.setTitle("Remover Produto por Nome");

                Label labelNomeProduto = new Label("Nome do Produto:");
                TextField textFieldNomeProduto = new TextField();

                Button removerButton = new Button("Remover Produto");

                VBox removerProdutoLayout = new VBox(10);
                removerProdutoLayout.getChildren().addAll(labelNomeProduto,
                        textFieldNomeProduto, removerButton);
                removerProdutoLayout.setAlignment(Pos.CENTER);

                removerButton.setOnAction(removeEvent -> {
                    String nomeProduto = textFieldNomeProduto.getText();
                    removerProdutoNoArquivo(nomeProduto);
                    removerProdutoStage.close();
                });

                Scene removerProdutoScene = new Scene(removerProdutoLayout, 500, 500);
                removerProdutoStage.setScene(removerProdutoScene);
                removerProdutoStage.show();
            });

            voltarButton.setOnAction(e -> setupAdminScene());

            root.getChildren().addAll(msgSubmenu, removerPorNomeButton, voltarButton);
        });
        updateButton.setOnAction(event -> {
            Label labelNomeProduto = new Label("Nome do Produto:");
            TextField textFieldNomeProduto = new TextField();

            Label labelQuantidade = new Label("Quantidade a ser adicionada:");
            TextField textFieldQuantidade = new TextField();

            Button atualizarButton = new Button("Atualizar Estoque");

            VBox atualizarEstoqueLayout = new VBox(10);
            atualizarEstoqueLayout.getChildren().addAll(labelNomeProduto, textFieldNomeProduto,
                    labelQuantidade, textFieldQuantidade, atualizarButton);
            atualizarEstoqueLayout.setAlignment(Pos.CENTER);

            atualizarButton.setOnAction(updateEvent -> {
                String nomeProduto = textFieldNomeProduto.getText();
                int quantidadeAlterada = Integer.parseInt(textFieldQuantidade.getText());
                atualizarEstoqueNoArquivo(nomeProduto, quantidadeAlterada);

                // Volta para o submenu principal após a atualização
                setupAdminScene();
            });

            root.getChildren().clear();
            root.getChildren().add(atualizarEstoqueLayout);
        });

        admReportsButton.setOnAction(event -> {/* Lógica para exibir relatórios */});
        admExitButton.setOnAction(event -> setupAdminScene());

        root.getChildren().addAll(msg3, insertButton, alterButton, removeButton,
                updateButton, admReportsButton, admExitButton);
    }


//-------------------------------------------------------------------------------
    //DAQUI PRA BAIXO É SÓ FUNÇÕES CAMARADAS
//--------------------------------------------------------------------------------

    //FUNÇÃO PARA SALVAR NO ARQUIVO
    private void salvarProdutosEmArquivo(Produto p) {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type produtoList = new TypeToken<List<Produto>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Produtos.json");
        List<Produto> listaProduto = new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProduto = gson.fromJson(reader, produtoList);
            listaProduto.add(p);

        }catch (IOException e) {
            e.printStackTrace();
        }
        gson = new Gson();
        String json = gson.toJson(listaProduto);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
            System.out.println("Deu certo");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------------------
    //FUNÇÃO PARA ALTERAR OS DADOS NO ARQUIVO
    private void alterarProdutoNoArquivo(String nomeProdutoAntigo, Produto produtoModificado) {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type produtoList = new TypeToken<List<Produto>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Produtos.json");
        List<Produto> listaProduto = new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProduto = gson.fromJson(reader, produtoList);


        }catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < listaProduto.size();i++){
            if(listaProduto.get(i).getNome().equals(nomeProdutoAntigo)){
                listaProduto.set(i,produtoModificado);
            }
        }
        gson = new Gson();
        String json = gson.toJson(listaProduto);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
            System.out.println("Deu certo");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------------------
    // FUNÇÃO PARA REMOVER
    private void removerProdutoNoArquivo(String nomeProduto) {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type produtoList = new TypeToken<List<Produto>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Produtos.json");
        List<Produto> listaProduto = new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProduto = gson.fromJson(reader, produtoList);


        }catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < listaProduto.size();i++){
            if(listaProduto.get(i).getNome().equals(nomeProduto)){
                listaProduto.remove(i);
            }
        }
        gson = new Gson();
        String json = gson.toJson(listaProduto);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
            System.out.println("Deu certo");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------------------
    //FUNÇÃO PARA ATUALIZAR O ESTOQUE
    private void atualizarEstoqueNoArquivo(String nomeProduto, int quantidadeAlterada) {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type produtoList = new TypeToken<List<Produto>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Produtos.json");
        List<Produto> listaProduto = new ArrayList<>();
        System.out.println("teste");

        try(Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProduto = gson.fromJson(reader, produtoList);

        }catch (IOException e) {
            e.printStackTrace();
        }



        for(int i = 0; i < listaProduto.size();i++){
            if(listaProduto.get(i).getNome().equals(nomeProduto)){
                listaProduto.get(i).setQuantidadeEmEstoque(listaProduto.get(i).getQuantidadeEmEstoque()+quantidadeAlterada);
            }
        }

        gson = new Gson();
        String json = gson.toJson(listaProduto);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
            System.out.println("Deu certo");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }



    //-----------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}
