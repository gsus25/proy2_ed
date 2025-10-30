package com.mycompany.grupo_07;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface Readable {

    static List<String> leerArchivo(String nombreArchivo) {

        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineas;
    }

    static void escribirAnimal(String p, String ruta) {

        try (FileWriter writer = new FileWriter(ruta, true)) {
            writer.write(p + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static List<String> preguntar(int numPreguntas) {
        List<String> preguntas = leerArchivo("preguntas.txt");
        Scanner scanner = new Scanner(System.in);
        List<String> respuestas = new ArrayList<>();

        for (int i = numPreguntas; i < preguntas.size(); i++) {
            System.out.println(preguntas.get(i));
            respuestas.add(scanner.nextLine());
        }

        return respuestas;
    }
}
