/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.grupo_07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author davidsuarez
 */
public interface Readable {
    

    
    static ArrayList<String> leerArchivo(String nombreArchivo){
        
        ArrayList<String> lineas = new ArrayList<>();
        
        //Lectura del archivo y obtencion de informacion (se agrega en el Array)
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(nombreArchivo);
            fr = new FileReader(archivo,StandardCharsets.UTF_8);
            br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null){
                lineas.add(linea);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null != fr){
                    fr.close();
                }
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return lineas;
    }
    

    
    static void escribirAnimal(String p, String ruta){
        FileWriter writer=null;
        try{
            writer= new FileWriter(ruta,true);
            writer.write(p+"\n");

        }catch(IOException ex){
            ex.printStackTrace();
        } finally{
            try{
                writer.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    static ArrayList<String> preguntar(int numPreguntas){
        ArrayList<String> preguntas = leerArchivo("preguntas.txt");
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> respuestas= new ArrayList<>();
        for (int i = numPreguntas; i < preguntas.size(); i++) {
            System.out.println(preguntas.get(i));
            String respuesta = scanner.nextLine();
            respuestas.add(respuesta);
        }
        return respuestas;
    }
}
