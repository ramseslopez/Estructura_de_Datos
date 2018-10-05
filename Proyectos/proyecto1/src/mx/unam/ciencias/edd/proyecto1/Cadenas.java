package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;
/**
 * Clase que sirve para ordenar cadenas de texto
 * @author Ramses Lopez Soto
 * @version marzo 2018
 */
public class Cadenas implements Comparable<Cadenas> {

    /*varable tipo String con la qu se va a comparar*/
    private String cadena;

    /**
     * Contructor que incializa nuestra varable
     * @param cadena variable con la que se va a inicializar el atributo
     */
    public Cadenas(String cadena){
        this.cadena = cadena;
    }

    /**
     * Metodo que compara las cadenas de texto por lineas
     * @param m cadena con la que vamos a comparar
     * @return int - posicion de la cadena
     */
    @Override public int compareTo(Cadenas m){
        Collator comparador = Collator.getInstance();
        comparador.setStrength(Collator.PRIMARY);
        int x = comparador.compare(cadena.replaceAll("\\P{L}+", " "),m.toString().replaceAll("\\P{L}+", " "));
        return x;
    }

    /**
     * Metodo que regresa la representacion en cadena de texto
     * @return String - cadena de texto
     */
    public String toString(){
        return cadena;
    }

}
