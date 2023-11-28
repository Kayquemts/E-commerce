package com.example.demo;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application; // a nossa clase extende da Application
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class ClienteTela extends Application {

    private Stage primaryStage;
    private VBox root;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Seja bem vindo");

        System.out.println(Cliente.clienteLogado.getNome());

        root = new VBox();
        root.setSpacing(10); // aqui vai dar um espaço de 10 pixels
        root.setAlignment(Pos.CENTER); //aqui alinhamos ao centro


        setupClientScene();

        primaryStage.setScene(new Scene(root, 300, 250));// largura e comprimento
        primaryStage.show();

        /*



         */
    }


    private void setupClientScene() {
        root.getChildren().clear();
        System.out.println("Você se conectou como usuário Cliente");
        Label msg4 = new Label("O que deseja fazer:");
        //Button insertDataButton = new Button("Inserir dados de cadastro");
        Button alterDataButton = new Button("Alterar dados de cadastro");
        Button compraButton = new Button("Realizar uma compra");
        //Button clientReportsButton = new Button("Exibir relatórios");
        //Button clientExitButton = new Button("Sair do menu Cliente");


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
        root.getChildren().addAll(msg4, alterDataButton);
    }

    //Funções de acões

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
    public static void main(String[] args) {
        launch(args);
    }
}
