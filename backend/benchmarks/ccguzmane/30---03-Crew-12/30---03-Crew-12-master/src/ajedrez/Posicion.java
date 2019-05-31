/*
 * Posicion.java
 */

package ajedrez;

/**
 * Una posicion en ajedrez, dada por una fila y una columna
 * Para facilitar su uso, los contenidos de una posicion no se pueden 
 * cambiar una vez creada. Por tanto, no hay inconvenientes en permitir
 * acceso publico a sus miembros.
 *
 * @author  mf
 */
public class Posicion {
        
    // Informacion de estado de una posicion
    
    /** columna */
    public final byte col;

    /** fila */
    public final byte fil;
    
    // Metodos
    
    /**
     * constructor por defecto
     */
    public Posicion() {
        this(0, 0);
    }
    
    /**
     * constructor a partir de cadena
     * @param s de la forma "E2" o "H7" (columna-fila)
     */
    public Posicion(String s) {
        this(s.charAt(0)-'A', s.charAt(1)-'1');
    }
    
    /**
     * constructor a partir de componentes
     * @param columna la columna
     * @param fila la fila
     */
    public Posicion(int columna, int fila) {
        this.col = (byte)columna;
        this.fil = (byte)fila;
    }   
    
    /** 
     * devuelve una representacion como cadena
     * @return una cadena de la forma "F7" o "A3" (columna-fila)
     */
    public String toString() {
        return "" + (char)('A'+col) + (char)('1'+fil);
    }
}
