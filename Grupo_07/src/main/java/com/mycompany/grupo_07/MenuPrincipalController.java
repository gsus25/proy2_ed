package com.mycompany.grupo_07;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Joshz
 */
public class MenuPrincipalController {
    
    private void switchToSecondary(ActionEvent e,int n) throws IOException {
        FXMLLoader loader;
        loader=new FXMLLoader(getClass().getResource("juego.fxml"));
        Parent root = (Parent) loader.load();
        JuegoController juegoControlador=loader.getController();

        juegoControlador.setPreguntas(n);

        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene escena=new Scene(root,750,540);
        stage.setScene(escena);
        stage.show();
    }
    
    @FXML
    private void exit() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }
    public void mostrarDialogoCantidadPreguntas(ActionEvent e) {
        // Crear un ComboBox con valores del 1 al 20
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 1; i <= 20; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.setValue(20);
        VBox vBox = new VBox(comboBox);
        vBox.setSpacing(10);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cantidad de Preguntas");
        alert.setHeaderText("¿Cuántas preguntas quieres que te haga?");
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int cantidadPreguntas = comboBox.getValue();
                //Aqui esta el numero de preguntas que selecciona
                System.out.println("Número de preguntas seleccionadas: " + cantidadPreguntas);
                try {
                    
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Confirmación de juego");
                    alert2.setHeaderText("¿Ya has pensado en tu animal?");
                    alert2.setContentText("¿Quieres empezar el juego?");
                    ButtonType result = alert2.showAndWait().orElse(ButtonType.CANCEL);
                    if (result == ButtonType.OK) {
                        switchToSecondary(e, cantidadPreguntas);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
