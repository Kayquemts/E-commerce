package com.example.demo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application; // a nossa clase extende da Application
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class ClienteTela extends Application {
    private ObservableList<Produto> carrinho = FXCollections.observableArrayList();
    private Carrinho car;
    private List<List<Produto>> historicoCompras = new ArrayList<>();

    private int pontosDoCliente = 0;
    private Stage primaryStage;
    private VBox root;

    @Override
    public void start(Stage primaryStage){
        //carregarHistoricoCompras();
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Seja bem vindo");

        car = new Carrinho(Cliente.clienteLogado.getNome());


        root = new VBox();
        root.setSpacing(10); // aqui vai dar um espaço de 10 pixels
        root.setAlignment(Pos.CENTER); //aqui alinhamos ao centro


        setupClientScene();

        primaryStage.setScene(new Scene(root, 300, 250));// largura e comprimento
        primaryStage.show();

    }


    private void setupClientScene() {
        root.getChildren().clear();
        System.out.println("Você se conectou como usuário Cliente");
        Label msg4 = new Label("O que deseja fazer:");
        //Button insertDataButton = new Button("Inserir dados de cadastro");
        Button alterDataButton = new Button("Alterar dados de cadastro");
        Button compraButton = new Button("Realizar uma compra");
        Button relatorioButton = new Button("Exibir relatórios");

        relatorioButton.setOnAction(event -> {
            String filePath = System.getProperty("user.dir");
            Gson gson = new Gson();

            Type historicoType = new TypeToken<List<Carrinho>>(){}.getType();
            Path caminhoArquivo = Paths.get(filePath,"Compra.json");

            List<Carrinho> historico = new ArrayList<>();

            try(Reader reader = new FileReader(caminhoArquivo.toFile())){
                historico = gson.fromJson(reader,historicoType);
            }catch (Exception e){
                e.printStackTrace();
            }

            Stage relatorio = new Stage();
            relatorio.setTitle("Relatorio");

            VBox relatorioLayout = new VBox(10);

            for(int i = 0; i < historico.size(); i++){
                if(historico.get(i).getCliente().equals(Cliente.clienteLogado.getNome())){
                    Label data = new Label(historico.get(i).getDataAtual());

                    relatorioLayout.getChildren().addAll(data);
                    for(int j = 0; j < historico.get(i).getQuantProd(); j++){
                        Label valorUnitario = new Label(historico.get(i).getProdutos(j).getNome() + " :"+ historico.get(i).getProdutos(j).getPreco());
                        Label pontos = new Label("Total de Pontos: " + historico.get(i).calcularTotalPontos());
                        relatorioLayout.getChildren().addAll(valorUnitario);
                    }
                    Label total = new Label("Total : " + historico.get(i).calcularTotalGasto());
                    relatorioLayout.getChildren().addAll(total);
                }


            }



            relatorioLayout.setAlignment(Pos.CENTER);

            /*
            valor de pontos ganhos por compra, e valor total de pontos ganhos.
             */


            Scene scene = new Scene(relatorioLayout, 400, 300);


            relatorio.setScene(scene);

            relatorio.show();
        });


        alterDataButton.setOnAction(event -> {
            Stage alterarClienteStage = new Stage();
            alterarClienteStage.setTitle("Alterar Dados do Cliente");

            Label labelNovoNome = new Label("Novo Nome:");
            TextField textFieldNovoNome = new TextField();

            Label labelNovoCPF = new Label("Novo CPF:");
            TextField textFieldNovoCPF = new TextField();

            Label labelNovaIdade = new Label("Nova Idade:");
            TextField textFieldNovaIdade = new TextField();

            Label labelNovaSenha = new Label("Nova Senha");
            PasswordField passwordFieldSenha = new PasswordField();

            Button salvarButton = new Button("Salvar Alterações");

            VBox alterarClienteLayout = new VBox(10);

            alterarClienteLayout.getChildren().addAll( labelNovoNome, textFieldNovoNome,
                    labelNovoCPF, textFieldNovoCPF,
                    labelNovaIdade, textFieldNovaIdade,
                    labelNovaSenha, passwordFieldSenha,
                    salvarButton);

            alterarClienteLayout.setAlignment(Pos.CENTER);

            salvarButton.setOnAction(saveEvent -> {
                String novoNome = textFieldNovoNome.getText();
                String novoCPF = textFieldNovoCPF.getText();
                int novaIdade = Integer.parseInt(textFieldNovaIdade.getText());
                String novaSenha = passwordFieldSenha.getText();

                // Chamada da função para alterar os dados do cliente no arquivo
                //alterarClienteNoArquivo(nomeAntigo, novoNome, novoCPF, novaIdade);
                Cliente cliente = new Cliente(novoNome, novoCPF, novaIdade,novaSenha);
                alterarClienteArquivo(cliente);

                alterarClienteStage.close();
            });

            Scene alterarClienteScene = new Scene(alterarClienteLayout, 500, 500);
            alterarClienteStage.setScene(alterarClienteScene);
            alterarClienteStage.show();
        });
        compraButton.setOnAction(event -> {
            Stage comprarProdutosStage = new Stage();
            comprarProdutosStage.setTitle("Comprar");

            Button informaticaButton = new Button("Informática");
            Button livrosButton = new Button("Livros");
            Button higieneButton = new Button("Higiene");
            Button limpezaButton = new Button("Limpeza");

            VBox comprarProdutosLayout = new VBox(10);

            informaticaButton.setOnAction(informaticaEvent -> {
                exibirProdutos("informatica", comprarProdutosStage);
            });

            livrosButton.setOnAction(livrosEvent -> {
                exibirProdutos("Livros", comprarProdutosStage);
            });

            higieneButton.setOnAction(higieneEvent -> {
                exibirProdutos("Higiene", comprarProdutosStage);
            });

            limpezaButton.setOnAction(limpezaEvent -> {
                exibirProdutos("Limpeza", comprarProdutosStage);
            });

            comprarProdutosLayout.getChildren().addAll(informaticaButton, livrosButton, higieneButton, limpezaButton);

            comprarProdutosLayout.setAlignment(Pos.CENTER);

            Scene comprarProdutosScene = new Scene(comprarProdutosLayout, 500, 500);
            comprarProdutosStage.setScene(comprarProdutosScene);
            comprarProdutosStage.show();
        });

        Button visualizarCarrinhoButton = new Button("Visualizar Carrinho");
        visualizarCarrinhoButton.setOnAction(event -> exibirCarrinho());

        root.getChildren().addAll(msg4, alterDataButton, compraButton, visualizarCarrinhoButton,relatorioButton);
    }

    private void efetuarCompra() {
        if (!verificarEstoqueSuficiente()) {
            System.out.println("Não foi possível efetuar a compra devido a estoque insuficiente.");
            return;
        }
        // Lógica para efetuar a compra
        atualizarEstoque();
        historicoCompras.add(new ArrayList<>(carrinho));
        carrinho.clear();

        System.out.println("Compra efetuada com sucesso!");
        salvarHistoricoCompras();
        car.clear();
    }

    private void atualizarEstoque() {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type produtoListType = new TypeToken<List<Produto>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath, "Produtos.json");

        List<Produto> listaProdutos = new ArrayList<>();

        try (Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProdutos = gson.fromJson(reader, produtoListType);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //System.out.println(car.getProdutos(i).getNome());

        for(int i = 0; i < car.getQuantProd(); i++){
            for(Produto produto : listaProdutos){
                if(car.getProdutos(i).getNome().equals(produto.getNome())){
                    car.getProdutos(i).setQuantidadeEmEstoque(car.getProdutos(i).getQuantidadeEmEstoque() - car.getQuantidades(i));
                    listaProdutos.set(i,car.getProdutos(i));
                }
            }
        }


        // Salva as alterações no arquivo Produtos.json
        try (FileWriter writer = new FileWriter(caminhoArquivo.toFile())) {
            gson.toJson(listaProdutos, writer);
            System.out.println("Estoque atualizado e persistido em Produtos.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void exibirCarrinho() {
        Stage carrinhoStage = new Stage();
        carrinhoStage.setTitle("Carrinho");

        VBox carrinhoLayout = new VBox(10);

        double totalCompra = 0.0;
        int totalPontos = 0;
        double preco = 0;
        for(int i = 0; i < car.getQuantProd(); i++){
            Label labelProduto = new Label(car.getProdutos(i).getNome());
            Label labelQuantidade = new Label("Quantidade: "+car.getQuantidades(i));
            Label labelPrecoProd = new Label("Valor: "+ car.getQuantidades(i)*car.getProdutos(i).getPreco());


                preco += (car.getQuantidades(i)*car.getProdutos(i).getPreco());
                totalCompra += (car.getQuantidades(i)*car.getProdutos(i).getPreco()) -((car.getQuantidades(i)*car.getProdutos(i).getPreco()) *car.getProdutos(i).getDesconto())/100;

            //double desconto = (car.getQuantidades(i)*car.getProdutos(i).getPreco())*car.getProdutos(i).getDesconto())/100;

             totalPontos += car.getProdutos(i).getPontos()*car.getQuantidades(i);

             carrinhoLayout.getChildren().addAll(labelProduto, labelQuantidade, labelPrecoProd);
        }

        Label labelPreco = new Label("Preço da Compra" + preco);
        Label labelTotalCompra = new Label("Preço final da Compra: R$" + totalCompra);

        Label labelTotalPontos = new Label("Total de Pontos: " + totalPontos);


        Button relacionadosButton = new Button("Ver produtos Similares");
        relacionadosButton.setOnAction(event -> {
            Stage relacionados = new Stage();
            relacionados.setTitle("Produtos Similares");

            List<Carrinho> historico = lerJson();

            List<Produto> produtos = Produto.carregarProdutosPorCategoria("informatica");



            Map<String, Integer> quantidadePorProduto = contarQuantidadePorProduto(historico);

            List<Map.Entry<String, Integer>> produtosMaisComprados = obterProdutosMaisComprados(quantidadePorProduto, 3);

            VBox similaresLayout = new VBox(5);

            for (Map.Entry<String, Integer> produto : produtosMaisComprados) {
                Label labelProduto = new Label(produto.getKey());
                similaresLayout.getChildren().addAll(labelProduto);
            }



            //List<Produto> produtos = Produto.carregarProdutosPorCategoria("Livro");





            Scene similareScene = new Scene(similaresLayout, 500,500);

            relacionados.setScene(similareScene);
            relacionados.show();
        });

        Button efetuarCompraButton = new Button("Efetuar Compra");
        int finalTotalPontos = totalPontos;
        double finalTotalCompra = totalCompra;
        double desconto = preco-totalCompra;

        efetuarCompraButton.setOnAction(event -> realizarPagamento(finalTotalCompra,finalTotalPontos, desconto));
        carrinhoLayout.getChildren().addAll(labelPreco, labelTotalCompra, labelTotalPontos, efetuarCompraButton,relacionadosButton);

        Scene carrinhoScene = new Scene(carrinhoLayout, 300, 300);
        carrinhoStage.setScene(carrinhoScene);
        carrinhoStage.show();
    }



    private void exibirDetalhesPagamento(double valorAPagar, double valorDesconto, int numeroParcelas, double valorParcela) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Pagamento");
        alert.setHeaderText("Detalhes do Pagamento:");
        alert.setContentText("Valor a Pagar: R$" + valorAPagar + "\n" +
                "Desconto: R$" + valorDesconto + "\n" +
                "Número de Parcelas: " + numeroParcelas + "\n" +
                "Valor da Parcela: R$" + valorParcela);

        alert.showAndWait();
    }
    private void exibirMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }
    private void realizarPagamento(double totalCompra, int totalPontos, double preco) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Forma de Pagamento");
        alert.setHeaderText("Escolha a forma de pagamento:");
        alert.setContentText("Como você deseja fazez o pagamento?");
        CheckBox ponto = new CheckBox("Usar pontos?");

        // Criar um GridPane e adicionar o CheckBox a ele
        GridPane gridPane = new GridPane();
        gridPane.add(ponto, 0, 0);

        // Adicionar o GridPane ao conteúdo do Alert
        alert.getDialogPane().setContent(gridPane);


        ButtonType botaoAVista = new ButtonType("À Vista");
        ButtonType botaoParcelado = new ButtonType("Parcelado em 2x", ButtonBar.ButtonData.CANCEL_CLOSE);



        alert.getButtonTypes().setAll( botaoAVista, botaoParcelado);
        //alert.getDialogPane().getChildren().add(ponto);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == botaoAVista ) {
                // Pagamento à vista
                pagarCompra(totalCompra, 1, ponto.isSelected(), totalPontos, preco);

            } else if (buttonType == botaoParcelado) {
                // Pagamento parcelado em 2x
                if (totalCompra >= 50.0) {
                    pagarCompra(totalCompra, 2, ponto.isSelected(), totalPontos,preco);  // Número de parcelas = 2, pontos utilizados = 0
                } else {
                    // Total da compra não atende aos requisitos para parcelamento
                    exibirMensagem("Parcelamento disponível apenas para compras a partir de R$50.");
                }

            }
        });
    }
    private void pagarCompra(double totalCompra, int numeroParcelas,boolean utilizarPontos, int pontosUtilizados, double preco) {

        double valorAPagar = totalCompra;
        double valorDesconto = 0;


        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type clienteList = new TypeToken<List<Cliente>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Cliente.json");
        List<Cliente> listaCliente= new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaCliente = gson.fromJson(reader, clienteList);

        }catch (IOException e) {
            e.printStackTrace();
        }


        if(utilizarPontos){

            for(int i = 0; i < listaCliente.size();i++){
                if(listaCliente.get(i).getNome().equals(Cliente.clienteLogado.getNome())){
                    pontosUtilizados += listaCliente.get(i).getPontosAcumulados();
                    listaCliente.get(i).setPontosAcumulados(0);
                }
            }

            valorDesconto = pontosUtilizados / 10.0;  // Cada 10 pontos equivalem a R$1
            valorAPagar -= valorDesconto;
        }else {


            for(int i = 0; i < listaCliente.size();i++){
                if(listaCliente.get(i).getNome().equals(Cliente.clienteLogado.getNome())){
                    Cliente.clienteLogado.setPontosAcumulados(pontosUtilizados);
                    listaCliente.get(i).setPontosAcumulados(pontosUtilizados);
                }
            }


        }

        gson = new Gson();
        String json = gson.toJson(listaCliente);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
            System.out.println("Deu certo");
        }catch(IOException e) {
            e.printStackTrace();
        }

        double valorParcela = valorAPagar / numeroParcelas;
        valorDesconto += preco;

        exibirDetalhesPagamento(valorAPagar, valorDesconto, numeroParcelas, valorParcela);
        efetuarCompra();
    }

    //Funções de acões

    private void salvarHistoricoCompras() {

        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();

        Type historicoType = new TypeToken<List<Carrinho>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Compra.json");

        List<Carrinho> historico = new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())){
            historico = gson.fromJson(reader,historicoType);
        }catch (Exception e){
            e.printStackTrace();
        }

        historico.add(car);

        gson = new Gson();
        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            gson.toJson(historico,writer);

        }catch (IOException e){
            e.printStackTrace();
        }

        /*
        Type produtoListType = new TypeToken<List<Produto>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath, "Produtos.json");

        List<Produto> listaProdutos = new ArrayList<>();

        try (Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaProdutos = gson.fromJson(reader, produtoListType);
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Salva as alterações no arquivo Produtos.json
        try (FileWriter writer = new FileWriter(caminhoArquivo.toFile())) {
            gson.toJson(listaProdutos, writer);
            System.out.println("Estoque atualizado e persistido em Produtos.json");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private void carregarHistoricoCompras() {
        // Especificar o caminho do arquivo onde você deseja carregar o histórico de compras
        String caminhoArquivo = "Compra.json";

        try (Reader reader = new FileReader(caminhoArquivo)) {
            // Carregar o histórico de compras do arquivo
            Gson gson = new Gson();
            historicoCompras = gson.fromJson(reader, new ArrayList<List<Produto>>().getClass());
            if (historicoCompras == null) {
                historicoCompras = new ArrayList<>();
            }
            System.out.println("Histórico de compras carregado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarEstoqueSuficiente() {
        for (Produto produto : carrinho) {
            // Verifica se a quantidade desejada está disponível em estoque
            if (produto.getQuantidadeEmEstoque() < 1) {
                System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
                return false;
            }
        }
        return true;
    }

    /*private void adicionarAoCarrinho(Produto produto, int quantidade) {
        // Verifica se há estoque suficiente para o produto
        if (produto.getQuantidadeEmEstoque() > quantidade) {
            carrinho.add(produto);
            System.out.println(produto.getNome() + " adicionado ao carrinho.");
        } else {
            System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
        }
    }*/
    private void exibirProdutos(String categoria, Stage stage) {
        List<Produto> produtos = Produto.carregarProdutosPorCategoria(categoria);

        VBox produtosLayout = new VBox(10);

        for (Produto produto : produtos) {
            Label labelProduto = new Label(produto.getNome() + ": R$" + produto.getPreco());
            Spinner<Integer> quantidadeSpinner = new Spinner<>(0,produto.getQuantidadeEmEstoque(),0);

            Button comprarButton = new Button("Comprar");
            comprarButton.setOnAction(comprarEvent -> {car.setProdutos(produto,quantidadeSpinner.getValue());});

            VBox produtoLayout = new VBox(5);
            produtoLayout.getChildren().addAll(labelProduto,quantidadeSpinner, comprarButton);

            produtosLayout.getChildren().add(produtoLayout);
        }

        Scene produtosScene = new Scene(produtosLayout, 500, 500);
        stage.setScene(produtosScene);
        stage.setTitle("Produtos - " + categoria);
    }
    public void alterarClienteArquivo(Cliente clienteModificado){
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type clienteList = new TypeToken<List<Cliente>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Cliente.json");
        List<Cliente> listaCliente= new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())) {
            listaCliente = gson.fromJson(reader, clienteList);

        }catch (IOException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < listaCliente.size();i++){
            if(listaCliente.get(i).getNome().equals(Cliente.clienteLogado.getNome())){
                listaCliente.set(i,clienteModificado);
                Cliente.clienteLogado = clienteModificado;
            }
        }

        gson = new Gson();
        String json = gson.toJson(listaCliente);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
            System.out.println("Deu certo");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    private static List<Carrinho> lerJson() {
        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();

        Type historicoType = new TypeToken<List<Carrinho>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Compra.json");

        List<Carrinho> historico = new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())){
            historico = gson.fromJson(reader,historicoType);
            return historico;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static Map<String, Integer> contarQuantidadePorProduto(List<Carrinho> compras) {
        Map<String, Integer> quantidadePorProduto = new HashMap<>();

        for (Carrinho compra : compras) {
            for(int i = 0; i < compra.getQuantProd() ;i++){
                String nomeProduto = compra.getProdutos(i).getNome();
                int quantidade = quantidadePorProduto.getOrDefault(nomeProduto, 0);
                quantidadePorProduto.put(nomeProduto, quantidade + 1);
            }

        }

        return quantidadePorProduto;
    }

    private static List<Map.Entry<String, Integer>> obterProdutosMaisComprados(Map<String, Integer> quantidadePorProduto, int n) {
        List<Map.Entry<String, Integer>> produtosOrdenados = new ArrayList<>(quantidadePorProduto.entrySet());

        // Ordenar a lista pelo valor (quantidade) em ordem decrescente
        produtosOrdenados.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Retornar os n primeiros elementos da lista (os mais comprados)
        return produtosOrdenados.subList(0, Math.min(n, produtosOrdenados.size()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
