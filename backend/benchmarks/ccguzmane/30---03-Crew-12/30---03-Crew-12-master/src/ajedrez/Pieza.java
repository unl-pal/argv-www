/*
 * Pieza.java
 *
 */

package ajedrez;

import java.util.Random;
import java.util.*;

/** 
 * Una pieza en el juego del ajedrez.
 * @author mf
 */
public abstract class Pieza {
    
    // Estado interno de una pieza
    
    /** el color de la pieza (BLANCO o NEGRO */
    protected int color;
    
    // Métodos
    
    /**
     * devuelve la posición de esta pieza
     * @return la posición
     */    
    public Object getPosicion() {
        return new Object();
    }
    
    /**
     * devuelve el color (BLANCO o NEGRO) de esta pieza
     * @return el color
     */    
    public int getColor() {
        return color;
    }
    
    /** 
     * Metodo de copia. Es mas sencillo de usar que "clone()"
     * @return una copia de la pieza en cuestion
     */
    public abstract Object copia();
    
    /** 
     * Devuelve el caracter correspondiente a esta pieza
     * @return el caracter, sin importar mayusculas o minusculas
     */    
    public abstract char piezaAChar();
    
    /** 
     * Fabrica de piezas. Devuelve una pieza nueva del tipo pedido
     * @param c caracter representativo. Debe ser uno de los
     * definidos en {@link Constantes}
     * @param col columna donde colocarla
     * @param fil fila en la que colocarla
     * @return la pieza así generada
     */    
    public static Object charAPieza(char c, int col, int fil) {
        Random rand = new Random();
		int color = rand.nextBoolean() ? rand.nextInt() : rand.nextInt();
        
        return new Object();
    }
}
