/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;

import static com.mycompany.grupo_07.Readable.escribirAnimal;
import static com.mycompany.grupo_07.Readable.preguntar;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author luisa
 */
public class Aquinator {
    public static void main(String[] args) {       
        Scanner scanner = new Scanner(System.in);

        // Cargar las preguntas y respuestas
        ArrayList<String> preguntas = cargarPreguntas("preguntas.txt");
        Map<String, String[]> respuestas = cargarRespuestas("respuestas.txt");

        // Construir el árbol de decisiones
        ArbolBinario<String> arbol = construirArbol(preguntas, respuestas);
        ArrayList<String> animales = arbol.obtenerHojas();
        // Obtener el número máximo de preguntas
        System.out.print("Ingresa el número máximo de preguntas (N): ");
        int maxPreguntas = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        // Jugar
        ArbolBinario<String> nodoActual = arbol;
        int numPreguntas = 0;
        ArrayList<String> contesta =new ArrayList<>();
        OUTER:
        while (numPreguntas <= maxPreguntas) {
            if (nodoActual == null || nodoActual.raiz == null) {
                System.out.println("No es posible encontrar un animal con estas características.");
                System.out.println("Quieres añadir tu animal al juego? (sí/no)");
                String respuesta2 = scanner.nextLine().trim().toLowerCase();
                switch (respuesta2) {
                    case "sí":
                    case "si":
                        System.out.println("oki");
                        aprender(animales,contesta);
                        break;
                    case "no":
                        break OUTER;
                    default:
                        System.out.println("Respuesta no válida. Responde con 'sí' o 'no'.");
                        continue;
                }                       
                return;
            }
            if (nodoActual.esHoja()) {
                break;
            }
            System.out.println(nodoActual.getContenido());
            String respuesta = scanner.nextLine().trim().toLowerCase();
            contesta.add(respuesta);
            if (respuesta.equals("si")||respuesta.equals("sí")) {
                nodoActual = nodoActual.getIzq();
            } else if (respuesta.equals("no")) {
                nodoActual = nodoActual.getDer();
            } else {
                System.out.println("Respuesta no válida. Responde con 'sí' o 'no'.");
                continue;
            }
            numPreguntas++;
        }

        // Resultado
        if (nodoActual != null && nodoActual.esHoja()) {
            System.out.println("El animal que pensaste es un " + nodoActual.getContenido() + "?");
        } else if (nodoActual != null) {
            ArrayList<String> posiblesAnimales = nodoActual.obtenerHojas();
            if (posiblesAnimales.isEmpty()) {
                System.out.println("No es posible encontrar un animal con estas características.");
            } else if (posiblesAnimales.size() == 1) {
                System.out.println("No pude adivinar tu animal, pero podría ser un " + posiblesAnimales.get(0) + ".");
            } else {
                System.out.println("No pude adivinar tu animal. Los posibles animales que podrían coincidir son: " + posiblesAnimales);
            }
        } else {
            System.out.println("Gracias por jugar <3");
        }
    }

    public static Map<String, String[]> cargarRespuestas(String archivo) {
        Map<String, String[]> respuestas = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 2);
                String nombre = partes[0];
                String[] respuestasArray = partes[1].split(",");
                respuestas.put(nombre, respuestasArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuestas;
    }

    public static ArrayList<String> cargarPreguntas(String archivo) {
        ArrayList<String> preguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                preguntas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return preguntas;
    }
    
    public static void aprender(ArrayList<String> animales, ArrayList<String> respuestas){      
        int n= respuestas.size();
        respuestas.addAll(preguntar(n));
        
        //cambiar respuestas de "si" por "sí"
        respuestas.replaceAll(s -> "si".equals(s) ? "sí" : s);
        
        Scanner scanner = new Scanner(System.in);
        if (respuestas.size()==20){
            System.out.println("Como se llama el animal que quieres agregar:");
            String animalito = scanner.nextLine();
            if (!animales.contains(animalito)){
                StringBuilder animal = new StringBuilder(animalito);

                for (String elemento : respuestas) {
                    animal.append(",").append(elemento);
                }

                String animalRespuestas = animal.toString();

                escribirAnimal(animalRespuestas,"respuestas.txt");
            }
            else{
                System.out.println("Este animal si se encuentra en el juego");
            }
        }
    }
    

    public static ArbolBinario<String> construirArbol(ArrayList<String> preguntas, Map<String, String[]> respuestas) {
        if (preguntas.isEmpty() || respuestas.isEmpty()) return null;

        // Crear raíz del árbol
        ArbolBinario<String> arbol = new ArbolBinario<>(preguntas.get(0));

        for (Map.Entry<String, String[]> entrada : respuestas.entrySet()) {
            String[] ruta = entrada.getValue();
            ArbolBinario<String> nodoActual = arbol;

            for (int i = 0; i < ruta.length; i++) {
                if (ruta[i].equals("sí")) {
                    if (nodoActual.getIzq() == null) {
                        if (i == ruta.length - 1) {
                            nodoActual.addLeft(new ArbolBinario<>(entrada.getKey()));
                        } else {
                            nodoActual.addLeft(new ArbolBinario<>(preguntas.get(i + 1)));
                        }
                    }
                    nodoActual = nodoActual.getIzq();
                } else {
                    if (nodoActual.getDer() == null) {
                        if (i == ruta.length - 1) {
                            nodoActual.addRight(new ArbolBinario<>(entrada.getKey()));
                        } else {
                            nodoActual.addRight(new ArbolBinario<>(preguntas.get(i + 1)));
                        }
                    }
                    nodoActual = nodoActual.getDer();
                }
            }
        }
        return arbol;
    }
}
