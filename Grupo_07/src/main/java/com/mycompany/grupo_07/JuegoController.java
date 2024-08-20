/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;

import static com.mycompany.grupo_07.Aquinator.cargarPreguntas;
import static com.mycompany.grupo_07.Aquinator.cargarRespuestas;
import static com.mycompany.grupo_07.Aquinator.construirArbol;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class JuegoController {

    private int maxPreguntas;
    private ArrayList<String> preguntas;
    private int numPreguntas;
    private Random random;
    private ArrayList<String> imagePaths;
    private Map<String, String[]> respuestas;
    private ArbolBinario<String> arbol;
    private ArrayList<String> animales;
    private ArbolBinario<String> nodoActual;
    private ArrayList<String> contesta;
    private ArrayList<String> opciones;
    private int contadorNose;
    
    @FXML
    private Button botonSi;
    @FXML
    private Button botonNo;
    @FXML
    private Label preguntaLabel;
    @FXML
    private ImageView imgView;
    @FXML
    private Button botonNoSe;
    
    public void setPreguntas(int n){
        this.maxPreguntas = n;
        inicializar();

        // Configurar el Label para que se ajuste automáticamente al contenido
        preguntaLabel.setWrapText(true);  // Si quieres que el texto se ajuste en múltiples líneas
        preguntaLabel.setMaxWidth(Double.MAX_VALUE);  // Para que ocupe el máximo ancho posible
        preguntaLabel.setText(numPreguntas+". "+preguntas.get(0));
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
    
    private void cambiarPantallaAprender(ActionEvent e) throws IOException{
        FXMLLoader loader;
        loader=new FXMLLoader(getClass().getResource("aprender.fxml"));
        Parent root = (Parent) loader.load();
        AprenderController aprenderControlador=loader.getController();

        aprenderControlador.setRespuestas(contesta, animales);

        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene escena=new Scene(root,750,540);
        stage.setScene(escena);
        stage.show();
    }
    
    private void cambiarPantallaFinal(ActionEvent e, ArrayList<String> animalesPosibles) throws IOException{
        FXMLLoader loader;
        loader=new FXMLLoader(getClass().getResource("finaljuego.fxml"));
        Parent root = (Parent) loader.load();
        FinalJuegoController finalControlador=loader.getController();

        finalControlador.llenarAnimales(animalesPosibles);

        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene escena=new Scene(root,750,540);
        stage.setScene(escena);
        stage.show();
    }
    
    private void inicializar(){
        imagePaths = new ArrayList<>();
        imagePaths.add("/images/Pdefault.png");
        imagePaths.add("/images/Penojado.png");
        imagePaths.add("/images/Pfeliz.png");
        imagePaths.add("/images/Pmuyfeliz.png");
        imagePaths.add("/images/Ptriste.png");
        
        opciones=new ArrayList<>();
        opciones.add("Sí");
        opciones.add("No");  
        
        random = new Random();
        
        // Cargar las preguntas y respuestas
        preguntas = cargarPreguntas("preguntas.txt");
        respuestas = cargarRespuestas("respuestas.txt");

        // Construir el árbol de decisiones
        arbol= construirArbol(preguntas, respuestas);
        animales= arbol.obtenerHojas();
        
        nodoActual = arbol;
        contadorNose=0;
        numPreguntas = 1;
        contesta=new ArrayList<>();
    }
    
    private void cambiarImagen(){
        String selectedImagePath = imagePaths.get(random.nextInt(imagePaths.size()));

        // Cargar la imagen y establecerla en el ImageView
        Image image = new Image(getClass().getResourceAsStream(selectedImagePath));
        imgView.setImage(image);
    }
    
    private void aprender(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Animal no encontrado");
        alert.setHeaderText("No es posible encontrar un animal con estas características");
        alert.setContentText("¿Quieres añadir tu animal al juego?");

        // Verifica la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cambiarPantallaAprender(e);
        } else {
            // Si el usuario selecciona "Cancel", retornamos sin hacer nada más.            
            cambiarPantallaMenu(e);
            return;
        }
    }

    
    @FXML
    private void switchPregunta(ActionEvent e) throws IOException {        
        
        if (nodoActual == null || nodoActual.raiz == null) {
            if(contesta.size()==contadorNose){
                resultado(e);
                return;
            }
            aprender(e);
            return;
        }

        if(numPreguntas<=maxPreguntas && !nodoActual.esHoja()){        

            System.out.println(nodoActual.getContenido());
            String respuesta = ((Button) e.getSource()).getText();
            System.out.println(respuesta);
            
            if(respuesta.equals("No lo sé")){
                respuesta=opciones.get(random.nextInt(opciones.size()));
                contadorNose++;
            }
            
            contesta.add(respuesta.toLowerCase());
            if (respuesta.equals("Sí"))
                nodoActual = nodoActual.getIzq();
            else if(respuesta.equals("No"))
                nodoActual = nodoActual.getDer();

            if(numPreguntas!=maxPreguntas && nodoActual!=null){ 
                preguntaLabel.setText(numPreguntas+1+". "+nodoActual.getContenido());
                cambiarImagen();
            }
            if(numPreguntas==maxPreguntas || nodoActual==null) resultado(e);
            
            numPreguntas++;
            System.out.println(contesta);
          
        }
        else{
            resultado(e);
        }
        
    }
        
    private void resultado(ActionEvent e) throws IOException{
        ArrayList<String> animalesPosibles=new ArrayList<>();
        
        if(contesta.size()!=contadorNose){
            if (nodoActual != null && nodoActual.esHoja()) {
                animalesPosibles.add(nodoActual.getContenido());
            } else if(nodoActual!=null){
                animalesPosibles.addAll(nodoActual.obtenerHojas());
            }else{
                if(contesta.size()==contadorNose){
                    resultado(e);
                    return;
                }
                aprender(e);
                return;
            }
        }
        cambiarPantallaFinal(e,animalesPosibles);
    }
    
}
