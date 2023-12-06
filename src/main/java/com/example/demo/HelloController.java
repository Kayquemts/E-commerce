package com.example.demo;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import com.google.gson.Gson;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelloController {
    @FXML
    private Label errorMessage;
    @FXML
    private TextField nome;
    @FXML
    private PasswordField senha;
    @FXML
    private RadioButton cliente;
    @FXML
    private RadioButton admin;

    @FXML
    public void onHelloButtonClick(ActionEvent event) {
        if(cliente.isSelected() && admin.isSelected()){
            errorMessage.setText("Selecione so um campo");
        }else if(cliente.isSelected()) {

            String filePath = System.getProperty("user.dir");
            Type listaCliente = new TypeToken<List<Cliente>>() {}.getType();
            Path caminhoArquivo = Paths.get(filePath,"Cliente.json");

            try(Reader reader = new FileReader(caminhoArquivo.toFile())){
                Gson gson = new Gson();
                List<Cliente> clientes = gson.fromJson(reader, listaCliente);

                for(Cliente a: clientes){
                    if(a.getNome().equals(nome.getText()) && a.getSenha().equals(senha.getText())) {
                        System.out.println("Login feito com sucesso");

                        ClienteTela clienteTela = new ClienteTela();
                        Stage stage = new Stage();

                        Cliente.clienteLogado = a;

                        clienteTela.start(stage);
                        return;
                    }
                }
            }catch(Exception e){
                System.out.println("Deu Erro "+e);
            }

        }else{
            String filePath = System.getProperty("user.dir");
            Type listaCliente = new TypeToken<List<Admin>>() {}.getType();
            Path caminhoArquivo = Paths.get(filePath,"Admin.json");

            try(Reader reader = new FileReader(caminhoArquivo.toFile())){
                Gson gson = new Gson();
                List<Admin> admins = gson.fromJson(reader, listaCliente);

                for(Admin a: admins){
                    if(a.getNome().equals(nome.getText()) && a.getSenha().equals(senha.getText())){
                        System.out.println("Login feito com sucesso");

                        AdminTela adminTela = new AdminTela();
                        Stage stage = new Stage();

                        // Chamar o método de inicialização manualmente
                        adminTela.start(stage);

                        return;
                    }
                }
            }catch(Exception e){
                System.out.println("Deu Erro "+e.getMessage());
            }

        }
    }


    public void onCriarCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("novo-usuario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Se você precisar acessar o controlador da nova tela
            NovoUsuarioController novoUsuarioController = loader.getController();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}