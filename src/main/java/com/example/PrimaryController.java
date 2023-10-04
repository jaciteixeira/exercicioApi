package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {

    @FXML FlowPane flow;

    @FXML Pagination pagination;
    private int pagina = 1;
    

    public FlowPane carregarPersonagens(){
        try {
            var url = new URL("https://dog.ceo/api/breeds/image/random");
            var con = url.openConnection();
            con.connect();
            var is = con.getInputStream();
            var reader = new BufferedReader(new InputStreamReader(is));

            var json = reader.readLine();

            var lista = jsonParaList(json);

            return mostrarPersonagens(lista);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FlowPane mostrarPersonagens(List<Personagem> lista) {
        var flow = new FlowPane();

        lista.forEach(personagem -> {
            var image = new ImageView(new Image(personagem.getMessage()));
            image.setFitHeight(200);
            image.setFitWidth(200);

            var labelName = new Label(personagem.getMessage());
            var labelSpecie = new Label(personagem.getStatus());
            flow.getChildren().add(
                new VBox(labelName, labelSpecie)
            );
            
        });

        return flow;

    }

    private List<Personagem> jsonParaList(String json) throws JsonMappingException, JsonProcessingException {
        var mapper = new ObjectMapper();
        var status = mapper.readTree(json).get("status");
        List<Personagem> personagens = new ArrayList<>();

        status.forEach(personagem -> {
            try {
                var p = mapper.readValue(personagem.toString(), Personagem.class);
                personagens.add(p);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });

        return personagens;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pagination.setPageFactory(pag -> {
            pagina = pag + 1;
            return carregarPersonagens();
        });
    }



}
