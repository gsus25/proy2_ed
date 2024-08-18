/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Usuario
 */
public class JuegoController {
    @FXML
    private void switchToPrincipal() throws IOException {
        App.setRoot("menuprincipal");
    }
    @FXML
    private void switchToMenu() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Regreso");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            App.setRoot("menuprincipal");
        }
    }
}
