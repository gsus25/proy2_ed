package com.mycompany.grupo_07;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FinalJuegoController {

    private static final Logger LOGGER = Logger.getLogger(FinalJuegoController.class.getName());

    @FXML
    private VBox vboxAnimales;
    @FXML
    private VBox rootVBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label respuestaLabel;

    @FXML
    private void switchToMenu(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Regreso");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas volver al menú?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            cambiarPantallaMenu(e);
        }
    }

    private void cambiarPantallaMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuprincipal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene escena = new Scene(root, 640, 480);
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

    @FXML
    private void jugarOtra(ActionEvent e) {
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 1; i <= 20; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.setValue(20);
        VBox vBox = new VBox(comboBox);
        vBox.setSpacing(10);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cantidad de Preguntas");
        alert.setHeaderText("¿Cuántas máximas preguntas quieres esta vez?");
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int cantidadPreguntas = comboBox.getValue();
                LOGGER.info("Número de preguntas seleccionadas: " + cantidadPreguntas);
                try {
                    switchToSecondary(e, cantidadPreguntas);
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
        });
    }

    private void switchToSecondary(ActionEvent e, int n) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("juego.fxml"));
        Parent root = loader.load();
        JuegoController juegoControlador = loader.getController();

        juegoControlador.setPreguntas(n);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene escena = new Scene(root, 750, 540);
        stage.setScene(escena);
        stage.show();
    }

    public void llenarAnimales(List<String> animales) {

        if (animales.isEmpty()) {
            respuestaLabel.setText("No tengo suficiente información para adivinar tu animal");
            respuestaLabel.setWrapText(true);
            respuestaLabel.setMaxWidth(Double.MAX_VALUE);
            respuestaLabel.setAlignment(Pos.CENTER);
            return;
        }

        for (String animal : animales) {
            Label label = new Label(animal);
            vboxAnimales.getChildren().add(label);
        }
        vboxAnimales.setSpacing(10);
        vboxAnimales.setPadding(new Insets(10, 10, 10, 10));
    }
}
