/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;

import static com.mycompany.grupo_07.Aquinator.cargarPreguntas;
import static com.mycompany.grupo_07.Readable.escribirAnimal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 *
 * @author luisa
 */
public class AprenderController {

    private ArrayList<String> preguntas;
    private int numRespuestas;
    private ArrayList<String> animales;
    private ArrayList<String> respuestas;
    private int maxRespuestas;
    
    @FXML
    private Button botonSi;
    @FXML
    private Button botonNo;
    @FXML
    private Label preguntaLabel;
    
    public void setRespuestas(ArrayList<String> respuestas, ArrayList<String> animales){
        this.respuestas=respuestas;
        preguntas = cargarPreguntas("preguntas.txt");
        numRespuestas = respuestas.size();
        maxRespuestas=preguntas.size();
        this.animales=animales;
                
        // Configurar el Label para que se ajuste automáticamente al contenido
        preguntaLabel.setWrapText(true);  // Si quieres que el texto se ajuste en múltiples líneas
        preguntaLabel.setMaxWidth(Double.MAX_VALUE);  // Para que ocupe el máximo ancho posible
        preguntaLabel.setText(preguntas.get(numRespuestas));
        preguntaLabel.setAlignment(Pos.CENTER);
    }
    
    @FXML
    private void switchToMenu(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Regreso");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas volver al menu?");
        if (alert.showAndWait().get()==ButtonType.OK) {
            cambiarPantallaMenu(e);
        }
    }
    
    private void cambiarPantallaMenu(ActionEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("menuprincipal.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene escena=new Scene(root,640,480);
        stage.setScene(escena);
        stage.show();
    }
    
    @FXML
    private void switchPregunta(ActionEvent e) throws IOException {
        
        if (respuestas.size()==20){
            agregarAnimal(e);
        }
                
        if(numRespuestas<=maxRespuestas){
            String respuesta = ((Button) e.getSource()).getText();
            System.out.println(respuesta);
            respuestas.add(respuesta.toLowerCase());

            if(numRespuestas!=maxRespuestas && numRespuestas+1!=preguntas.size()){ 
                preguntaLabel.setText(preguntas.get(++numRespuestas));
            }
            
            System.out.println(respuestas);
            
            if (respuestas.size()==20){
                agregarAnimal(e);
            }
        }
    }
    
    private void agregarAnimal(ActionEvent e){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar nuevo animal");
        dialog.setHeaderText(null);
        dialog.setContentText("¿Cómo se llama el animal que quieres agregar?");

        // Mostrar el diálogo y esperar a que el usuario ingrese un valor
        Optional<String> result = dialog.showAndWait();

        // Si el usuario proporciona un valor, lo almacenamos en la variable
        result.ifPresent(animalito -> {
            if (!animales.contains(animalito)){
                StringBuilder animal = new StringBuilder(animalito);

                for (String elemento : respuestas) {
                    animal.append(",").append(elemento);
                }

                String animalRespuestas = animal.toString();

                escribirAnimal(animalRespuestas,"respuestas.txt");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Animal agregado");
                alert.setHeaderText(null);
                try{
                    cambiarPantallaMenu(e);
                }catch(IOException ie){
                    System.out.println(ie.getStackTrace());
                }
                alert.setContentText("Su animal ha sido agregado exitosamente");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Animal ya agregado");
                alert.setHeaderText(null);
                alert.setContentText("El animal ya se encuentra agregado");
                alert.show();
            }
        });
    }
    
}
