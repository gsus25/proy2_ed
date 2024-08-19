/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo_07;
import java.util.*;

public class ArbolBinario<E> {
    Nodo raiz;

    public Nodo getRaiz(){
        return raiz;
    }

    private class Nodo {
        E contenido;
        ArbolBinario<E> izq, der;

        public Nodo(E e) {
            contenido = e;
            izq = der = null;
        }  
        
    }
    
    public E getContenido() {
        return raiz.contenido;
    }

    public ArbolBinario<E> getIzq() {
        return raiz.izq;
    }

    public ArbolBinario<E> getDer() {
        return raiz.der;
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

    public ArrayList<E> obtenerHojas() {
        if (isEmpty()) return null;
        ArrayList<E> recorrido = new ArrayList<E>();
        
        if (esHoja()) {
            recorrido.add(raiz.contenido);
        } else {
            if (raiz.izq != null) recorrido.addAll(raiz.izq.obtenerHojas());
            if (raiz.der != null) recorrido.addAll(raiz.der.obtenerHojas());
        }
        return recorrido;
    }
    
}