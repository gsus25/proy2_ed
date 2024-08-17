/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 * @author Usuario
 */
public class JuegoController {
    @FXML
    private void switchToPrincipal() throws IOException {
        App.setRoot("menuprincipal");
    }
    
}
