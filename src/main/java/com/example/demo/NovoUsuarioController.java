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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class NovoUsuarioController {
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private TextField idade;
    @FXML
    private PasswordField senha;





    @FXML
    protected void onCadastrarCliente(ActionEvent event){

        Cliente novoCliente = new Cliente(nome.getText(),cpf.getText(),Integer.parseInt(idade.getText()),senha.getText());

        String filePath = System.getProperty("user.dir");
        Gson gson = new Gson();
        Type clientList = new TypeToken<List<Cliente>>(){}.getType();
        Path caminhoArquivo = Paths.get(filePath,"Cliente.json");
        List<Cliente> listaCliente = new ArrayList<>();

        try(Reader reader = new FileReader(caminhoArquivo.toFile())){
            listaCliente = gson.fromJson(reader, clientList);
            listaCliente.add(novoCliente);

        }catch (IOException e){
            System.out.println("Deu erro: " + e.getMessage());

        }

        gson = new Gson();
        String json = gson.toJson(listaCliente);

        try(FileWriter writer = new FileWriter(caminhoArquivo.toFile())){
            writer.write(json);
        }catch (IOException e){
            System.out.println("Deu erro: " + e.getMessage());
        }

    }
}
