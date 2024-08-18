package com.mycompany.grupo_07;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
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
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("juego");
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
    public void mostrarDialogoCantidadPreguntas() {
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
        alert.setHeaderText("¿Cuántas máximas preguntas quieres?");
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int cantidadPreguntas = comboBox.getValue();
                //Aqui esta el numeor de preguntas que selecciona
                System.out.println("Número de preguntas seleccionadas: " + cantidadPreguntas);
                try {
                    switchToSecondary();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
