/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Codigo;
import java.util.*;
/**
 *
 * @author mateo
 */
public class Alumno {
    int i = 0;
    String nombre;
    String apellido;
    int año;
    String curso;
    int edad;
    String comportamiento;
    public Alumno() {
    }
    
    public Object getObservaciones() {
        return new Object();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getCurso() {
        return curso;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getComportamiento() {
        return comportamiento;
    }

    public String toString() {
        String aux = "";
        aux = "Alumno{" + "nombre=" + nombre + ", apellido=" + apellido + ", año=" + año + ", curso=" + curso + ", edad=" + edad + ", comportamiento=" + comportamiento + '}';
        return aux;
    }
    
}
