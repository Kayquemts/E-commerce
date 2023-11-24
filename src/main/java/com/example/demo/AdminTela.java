
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

    //Função do Menu doCliente
    private void setupClientScene() {
        root.getChildren().clear();
        System.out.println("Você se conectou como usuário Cliente");
        Label msg4 = new Label("O que deseja fazer:");
        Button insertDataButton = new Button("Inserir dados de cadastro");
        Button alterDataButton = new Button("Alterar dados de cadastro");
        Button compraButton = new Button("Realizar uma compra");
        Button clientReportsButton = new Button("Exibir relatórios");
        Button clientExitButton = new Button("Sair do menu Cliente");

        insertDataButton.setOnAction(event -> {
            Stage cadastrarClienteStage = new Stage();
            cadastrarClienteStage.setTitle("Cadastrar Cliente");

            Label labelNome = new Label("Nome:");
            TextField textFieldNome = new TextField();

            Label labelCPF = new Label("CPF:");
            TextField textFieldCPF = new TextField();

            Label labelIdade = new Label("Idade:");
            TextField textFieldIdade = new TextField();

            Button salvarButton = new Button("Salvar");

            VBox cadastrarClienteLayout = new VBox(10);
            cadastrarClienteLayout.getChildren().addAll(labelNome, textFieldNome, labelCPF,
                    textFieldCPF, labelIdade, textFieldIdade, salvarButton);
            cadastrarClienteLayout.setAlignment(Pos.CENTER);

            salvarButton.setOnAction(saveEvent -> {
                String nome = textFieldNome.getText();
                String cpf = textFieldCPF.getText();
                int idade = Integer.parseInt(textFieldIdade.getText());

                Cliente novoCliente = new Cliente(nome, cpf, idade);
                // Adicionar o novo cliente ao array de clientes
                listaClientes.add(novoCliente);

                // Chamada da função para salvar os clientes no arquivo
                salvarClientesEmArquivo();

                cadastrarClienteStage.close();
            });

            Scene cadastrarClienteScene = new Scene(cadastrarClienteLayout,500, 500);
            cadastrarClienteStage.setScene(cadastrarClienteScene);
            cadastrarClienteStage.show();
        });
        alterDataButton.setOnAction(event -> {
            Stage alterarClienteStage = new Stage();
            alterarClienteStage.setTitle("Alterar Dados do Cliente");

            Label labelNomeAntigo = new Label("Nome do Cliente a ser Alterado:");
            TextField textFieldNomeAntigo = new TextField();

            Label labelNovoNome = new Label("Novo Nome:");
            TextField textFieldNovoNome = new TextField();

            Label labelNovoCPF = new Label("Novo CPF:");
            TextField textFieldNovoCPF = new TextField();

            Label labelNovaIdade = new Label("Nova Idade:");
            TextField textFieldNovaIdade = new TextField();

            Button salvarButton = new Button("Salvar Alterações");

            VBox alterarClienteLayout = new VBox(10);
            alterarClienteLayout.getChildren().addAll(labelNomeAntigo, textFieldNomeAntigo,
                    labelNovoNome, textFieldNovoNome, labelNovoCPF, textFieldNovoCPF, labelNovaIdade,
                    textFieldNovaIdade, salvarButton);
            alterarClienteLayout.setAlignment(Pos.CENTER);

            salvarButton.setOnAction(saveEvent -> {
                String nomeAntigo = textFieldNomeAntigo.getText();
                String novoNome = textFieldNovoNome.getText();
                String novoCPF = textFieldNovoCPF.getText();
                int novaIdade = Integer.parseInt(textFieldNovaIdade.getText());

                // Chamada da função para alterar os dados do cliente no arquivo
                alterarClienteNoArquivo(nomeAntigo, novoNome, novoCPF, novaIdade);

                alterarClienteStage.close();
            });

            Scene alterarClienteScene = new Scene(alterarClienteLayout, 500, 500);
            alterarClienteStage.setScene(alterarClienteScene);
            alterarClienteStage.show();
        });

        // Ação do botão de compra
        compraButton.setOnAction(event -> {
            // Criação do Submenu
            VBox comprarMenu = new VBox();
            comprarMenu.setSpacing(10);
            comprarMenu.setAlignment(Pos.CENTER);

            Label nomeLabel = new Label("Digite seu nome:");
            TextField nomeField = new TextField();

            Label cpfLabel = new Label("Digite seu CPF (formato: xxx.xxx.xxx-xx):");
            TextField cpfField = new TextField();

            Button verificarButton = new Button("Verificar");

            verificarButton.setOnAction(verificarEvent -> {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();

                if (verificarCliente(nome, cpf)) {
                    System.out.println("Cliente verificado! Pode prosseguir com as compras.");

                    // Chamando as funções para obter e exibir as categorias
                    ArrayList<String> categorias = obterCategorias();
                    exibirCategorias(categorias);
                } else {
                    System.out.println("Cliente não encontrado. Verifique os dados e tente novamente.");
                }
            });

            comprarMenu.getChildren().addAll(nomeLabel, nomeField, cpfLabel, cpfField, verificarButton);
            root.getChildren().clear();
            root.getChildren().add(comprarMenu);
        });
        clientReportsButton.setOnAction(event -> {/* Lógica para exibir relatórios */});
        clientExitButton.setOnAction(event -> setupAdminScene());

        root.getChildren().addAll(msg4, insertDataButton, alterDataButton, compraButton, clientReportsButton, clientExitButton);
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

    //-------------------------------------------------------------------------------------
    //>> ESTA PARTE É RESERVADA PARA AS FUNÇÕES DO MENU DO CLIENTE
    private void salvarClientesEmArquivo() {
        System.out.println("Salvar Cliente aqui");

    }
    //-------------------------------------------------------------------------------------
    // FUNÇÃO PARA ALTERAR OS DADOS DO CLIENTE NO ARQUIVO
    private void alterarClienteNoArquivo(String nomeAntigo, String novoNome, String novoCPF, int novaIdade) {
        System.out.println("alterarClienteNoArquivo aqui");

    }

//------------------------------------------------------------------------------------
    //FUNÇÃO DE VERIFICAÇÃO DE CLIENTE CADASTRADO

    private boolean verificarCliente(String nome, String cpf) {

        try {
            String diretorioAtual = System.getProperty("user.dir");
            File diretorio = new File(diretorioAtual);
            File arquivoClientes = new File(diretorio, "clientes.txt");

            Scanner scanner = new Scanner(arquivoClientes);
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dadosCliente = linha.split(";");

                // Remover espaços extras nos nomes antes da verificação
                String nomeArquivo = dadosCliente[0].trim(); // Remove espaços extras

                // Verificar se nome e cpf correspondem a algum cliente no arquivo
                if (dadosCliente.length >= 2 && nomeArquivo.equals(nome) && dadosCliente[1].equals(cpf)) {
                    scanner.close();
                    return true; // O cliente foi encontrado no arquivo
                }

                // Imprimir os dados lidos para debug (remova esta linha após a verificação)
                //System.out.println("Dados lidos: " + linha);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // O cliente não foi encontrado no arquivo
    }

    //-------------------------------------------------------------------------------
    // Função para ler o arquivo e retornar as categorias
    private ArrayList<String> obterCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        try {
            String diretorioAtual = System.getProperty("user.dir");
            File diretorio = new File(diretorioAtual);
            File arquivoProdutos = new File(diretorio, "produtos.txt");

            Scanner scanner = new Scanner(arquivoProdutos);
            while (scanner.hasNextLine()) {
                String[] dadosProduto = scanner.nextLine().split(";");
                String categoria = dadosProduto[1]; // O índice 1 contém a categoria do produto
                if (!categorias.contains(categoria)) {
                    categorias.add(categoria);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categorias;
    }
    //-------------------------------------------------------------------------------
    // Função para exibir as categorias em um menu para o cliente selecionar
    private void exibirCategorias(ArrayList<String> categorias) {
        VBox categoriasMenu = new VBox();
        categoriasMenu.setSpacing(10);
        categoriasMenu.setAlignment(Pos.CENTER);

        Label categoriasLabel = new Label("Escolha a categoria:");

        ToggleGroup toggleGroup = new ToggleGroup();

        for (String categoria : categorias) {
            RadioButton radioButton = new RadioButton(categoria);
            radioButton.setToggleGroup(toggleGroup);
            categoriasMenu.getChildren().add(radioButton);
        }

        Button selecionarCategoriaButton = new Button("Selecionar Categoria");

        selecionarCategoriaButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String categoriaSelecionada = selectedRadioButton.getText();
                System.out.println("Categoria selecionada: " + categoriaSelecionada);
                // função para exibir os produtos da categoria selecionada
                exibirProdutosDaCategoria(categoriaSelecionada);
            } else {
                System.out.println("Por favor, selecione uma categoria.");
            }
        });

        categoriasMenu.getChildren().addAll(categoriasLabel, selecionarCategoriaButton);
        root.getChildren().clear();
        root.getChildren().add(categoriasMenu);
    }

    //------------------------------------------------------------------------------
    //função para exibir os produtos da categoria selecionada
    private void exibirProdutosDaCategoria(String categoriaSelecionada) {
        VBox produtosMenu = new VBox();
        produtosMenu.setSpacing(10);
        produtosMenu.setAlignment(Pos.CENTER);

        Label produtosLabel = new Label("Produtos disponíveis na categoria " + categoriaSelecionada + ":");

        try {
            String diretorioAtual = System.getProperty("user.dir");
            File diretorio = new File(diretorioAtual);
            File arquivoProdutos = new File(diretorio, "produtos.txt");

            Scanner scanner = new Scanner(arquivoProdutos);
            while (scanner.hasNextLine()) {
                String[] dadosProduto = scanner.nextLine().split(";");
                String categoria = dadosProduto[1];
                String nomeProduto = dadosProduto[0];
                double quantidadeDouble = Double.parseDouble(dadosProduto[2]);
                int quantidade = (int) Math.round(quantidadeDouble); // Convertendo para int

                if (categoria.equals(categoriaSelecionada) && quantidade > 0) {
                    VBox produtoEntry = new VBox();
                    produtoEntry.setPrefWidth(400); // Define a largura desejada
                    produtoEntry.setPrefHeight(300); // Define a altura desejada
                    produtoEntry.setSpacing(10);
                    Label produtoLabel = new Label(nomeProduto);
                    Spinner<Integer> quantidadeSpinner = new Spinner<>(0, quantidade, 0); // Spinner para selecionar a quantidade
                    Button adicionarProdutoButton = new Button("Adicionar ao Carrinho");

                    adicionarProdutoButton.setOnAction(event -> {
                        int quantidadeSelecionada = quantidadeSpinner.getValue();
                        if (quantidadeSelecionada > 0) {
                            // Adicionar lógica para adicionar o produto selecionado ao carrinho
                            // (por exemplo, criar um objeto CarrinhoDeCompras e adicionar o produto com a quantidade ao carrinho)
                            System.out.println("Produto: " + nomeProduto + ", Quantidade: " + quantidadeSelecionada);
                        } else {
                            System.out.println("Selecione uma quantidade válida.");
                        }
                    });

                    produtoEntry.getChildren().addAll(produtoLabel, quantidadeSpinner, adicionarProdutoButton);
                    produtosMenu.getChildren().add(produtoEntry);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.getChildren().clear();
        root.getChildren().add(produtosMenu);
    }


    //-----------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}
