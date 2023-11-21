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

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelloController {
    @FXML
    private Label errorMessager;
    @FXML
    private TextField nome;
    @FXML
    private PasswordField senha;
    @FXML
    private RadioButton cliente;
    @FXML
    private RadioButton admin;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
        if(cliente.isSelected() && admin.isSelected()){
            errorMessager.setText("Selecione so um campo");
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


                        return;
                    }
                }
            }catch(Exception e){
                System.out.println("Deu Erro"+e);
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
                        FXMLLoader fx = new FXMLLoader(HelloController.class.getResource("home-view.fxml"));
                        Scene home = new Scene(fx.load());
                        Stage st = new Stage();
                        st.setTitle("Home");
                        st.setScene(home);
                        st.show();
                        return;
                    }
                }
            }catch(Exception e){
                System.out.println("Deu Erro"+e.getMessage());
            }

        }
    }
}