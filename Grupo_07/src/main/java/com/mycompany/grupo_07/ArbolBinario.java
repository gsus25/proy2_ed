/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author davidsuarez
 */
public class ArbolBinario<E> {
    Nodo raiz;
    
    private class Nodo {
        E contenido;
        ArbolBinario<E> izq, der;

        public Nodo(E e) {
            contenido = e;
            izq = der = null;
        }
    }

    public ArbolBinario() {
        clear();
    }

    public ArbolBinario(E contenido) {
        raiz = new Nodo(contenido);
    }

    public void clear() {
        raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public boolean esHoja() {
        if (isEmpty()) return false;
        return raiz.der == null && raiz.izq == null;
    }

    public boolean addLeft(ArbolBinario<E> ab) {
        if (raiz.izq != null) return false;
        raiz.izq = ab;
        return true;
    }

    public boolean addRight(ArbolBinario<E> ab) {
        if (raiz.der != null) return false;
        raiz.der = ab;
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Cargar las preguntas y respuestas
        ArrayList<String> preguntas = new ArrayList<>();
        preguntas.add("¿Es este animal un mamífero?");
        preguntas.add("¿Es este animal un carnívoro?");
        preguntas.add("¿Se para este animal sobre cuatro patas?");

        Map<String, String[]> respuestas = new HashMap<>();
        respuestas.put("Oso", new String[]{"sí", "sí", "sí"});
        respuestas.put("Lechuza", new String[]{"no", "sí", "no"});
        respuestas.put("Venado", new String[]{"sí", "no", "sí"});
        respuestas.put("Paloma", new String[]{"no", "no", "no"});

        // Construir el árbol de decisiones
        ArbolBinario<String> arbol = construirArbol(preguntas, respuestas);

        // Jugar
        ArbolBinario<String> nodoActual = arbol;
        while (!nodoActual.esHoja()) {
            System.out.println(nodoActual.raiz.contenido);
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("sí")) {
                nodoActual = nodoActual.raiz.izq;
            } else if (respuesta.equals("no")) {
                nodoActual = nodoActual.raiz.der;
            } else {
                System.out.println("Respuesta no válida. Responde con 'sí' o 'no'.");
            }
        }

        // Resultado
        System.out.println("El animal que pensaste es un " + nodoActual.raiz.contenido + "?");
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
                    if (nodoActual.raiz.izq == null) {
                        if (i == ruta.length - 1) {
                            nodoActual.addLeft(new ArbolBinario<>(entrada.getKey()));
                        } else {
                            nodoActual.addLeft(new ArbolBinario<>(preguntas.get(i + 1)));
                        }
                    }
                    nodoActual = nodoActual.raiz.izq;
                } else {
                    if (nodoActual.raiz.der == null) {
                        if (i == ruta.length - 1) {
                            nodoActual.addRight(new ArbolBinario<>(entrada.getKey()));
                        } else {
                            nodoActual.addRight(new ArbolBinario<>(preguntas.get(i + 1)));
                        }
                    }
                    nodoActual = nodoActual.raiz.der;
                }
            }
        }
        return arbol;
    }
}
