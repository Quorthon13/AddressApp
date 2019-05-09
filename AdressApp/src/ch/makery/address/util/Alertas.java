package ch.makery.address.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;


public class Alertas{
    
    /**
     *
     */
    public static void noPersonSelected(){
       Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Nenhuma seleção");
        alert.setHeaderText("Nenhuma pessoa selecionada");
        alert.setContentText("Por favor, selecione uma pessoa na tabela.");
        alert.showAndWait();
    }
    
    public static void campoInvalido(String errorMessage){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Campos inválidos");
        alert.setHeaderText("Por favor, corrija os campos inválidos");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
    
    public static void alertaAbout(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("Sobre");
        alert.setContentText("Autor: Marco Jakob\nWebsite: http://code.makery.ch\nFormatação em princípios SOLID: Diego Demétrio");
        alert.showAndWait();
    }
    
    public static void unknownData(File file){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText("Não foi possível carregar dados do arquivo:\n");
        alert.setContentText(file.getPath());
    }
}

