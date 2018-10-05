package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.MeteSaca;

/**
 * Clase que sirve para general los svg de Lista, Pila y cola
 * @see Lista
 * @see Pila
 * @see Cola
 * @author Ramses Lopez Soto
 * @version mayo 2018
 */
public class DibujaLPC{

    /*declaramos una variable estatica y privada que va a representar la estructura a graficar*/
    private static String estructura;

    /*variable estatica constante que representa el ecabezado de la imagen svg*/
    private static final String inicio_svg = "<?xml version=\'1.0\' encoding=\'"+
  	                                         "UTF-8\' ?> \n\t<svg "+
                                             "\n\twidth='%d' height='%d'>\n";

    /*variable estatica constante que representa el cierre del al etiqueta del svg*/
    private static final String fin_svg = "</svg>\n";

    /*variable estatica constante que representa el inicio de la etiqueta g del svg*/
    private static final String inicio_etiqueta_g = "\t<g>\n";

    /*variable estatica constante que representa el cierre de la etiqueta g del svg*/
    private static final String fin_etiqueta_g = "\t</g>\n";

    /*variable estatica constante que representa el rectangulo en el svg*/
    private static final String etiqueta_rectangulo = "\t<rect x='%d' y='%d' width='%d' height='%d' "+
                                                     "stroke='%s' fill='%s'/>\n";

    /*variable estatica constante que representa el texto en el svg*/
    private static final String etiqueta_texto = "\t<text font-family='sans-serif' "+
                                                "font-size='16' x='%d' y='%f' "+
                                                "text-anchor='middle' fill='%s'>%s</text>\n";

    /*variable estatica constante que representa a la figura en el svg*/
    private static final String conecta_lista = "\t<text fill='black' font-family='sans-serif' "+
                                                  "font-size='%d' x='%d' y='%d' "+
                                                  "text-anchor='middle'>%s</text>\n";


   /**
    * Metodo para dibujar una lista
    * @param edd lista de enteros a graficar
    * @return String - codigo para representar la lista
    */
   public static String drawList(Lista<Integer> edd){
     /*inicializamos a la variable de cadenas*/
     estructura ="";
     /*asignamos a una variable la medida de la lista*/
     int medida = edd.getLongitud()*100 - 50;
     /*reasignamos a la variable con el encabezado del svg*/
     estructura += String.format(inicio_svg, medida,25) + inicio_etiqueta_g;
     /*contador que inicializamos en cero*/
      int j = 0;
      /*iteramos la lista de enteros*/
     for (int i : edd) {
       /*reasignamos a la variable la figura y sus caracteristicas*/
       estructura += String.format(etiqueta_rectangulo,100*j,0,50,25,"black","white");
       /*verificamos si el contador es menor o igual a la longitud de la lista*/
       if (j + 1 <= edd.getLongitud()) {
         /*reasignamos a la variable con la cadena de texto*/
         estructura += String.format(etiqueta_texto,j*100+75,20.0,"black", "⇌");
         /*reasignamos a al variable con los elementos que iran en el svg*/
         estructura += String.format(etiqueta_texto, j*100+25,20.0,"black", i);
         /*aumentamos el contador*/
         j++;
       }
     }
     /*reasignamos a la variable el cierre del la etiqueta g*/
      estructura += fin_etiqueta_g;
      /*reasignamos a al variable el cierre del svg*/
      estructura += fin_svg;
      /*regresamos la estructura*/
      return estructura;
   }

   /**
    * Metodo para dibujar una pila
    * @param edd pila de enteros
    * @param num cantidad de elementos de la pila
    * @return String - codigo para representar la pila
    */
   public static String drawStack(Pila<Integer> edd, int num){
     /*inicializamos la variable*/
     estructura = "";
     /*asignamos a una variable la medida de la pila*/
     int medida = 25*num;
     /*reasignamos a la variable el encabezado del svg*/
     estructura += String.format(inicio_svg,50,medida) + inicio_etiqueta_g;
     /*inicializamos el contador*/
      int j = 0;
      /*verificamos que la pila no sea vacia*/
      while(!edd.esVacia()){
        /*sacamos los elementos de la pila y lo guardamos a una variable*/
        int e = edd.saca();
        /*reasignamos a la variable la figura y sus caracteristicas*/
        estructura += String.format(etiqueta_rectangulo,0,25*j,50,25,"black","white");
        /*verificamos que el contador sea menor de la cantidad de elementos*/
        if (j + 1 < num) {
          /*reasignamos a la variable con la cadena de texto*/
          estructura += String.format(etiqueta_texto,j*100+75,20.0,"black", "⇣");
      }
      /*reasignamos a la variable los elementos de la pila*/
        estructura += String.format(etiqueta_texto, 20,j*25.0+20,"black", e);
        /*aumentamos el contador*/
        j++;
   }
    /*reasignamos a la variable el cierre de la etiqueta g*/
     estructura += fin_etiqueta_g;
     /*reasignamos a la variable el cierre del svg*/
     estructura += fin_svg;
     /*regresamos el codigo de la estructura*/
     return estructura;
   }

   /**
    * Metodo para dibujar una Cola
    * @param edd cola de enteros
    * @param num cantidad de elementos de la Cola
    * @return String - codigo para representar la cola
    */
   public static String drawQueues(Cola<Integer> edd, int num){
     /*inicializamos la variable*/
     estructura = "";
     /*asignamos a una variable la medida de la cola*/
     int medida = 100*num-50;
     /*reasignamos a al variable el encabezado del svg*/
     estructura += String.format(inicio_svg, medida,25) + inicio_etiqueta_g;
     /*inicializamos el contador*/
      int j = 0;
      /*verificamos si la cola no es vacia*/
      while(!edd.esVacia()){
        /*sacamos los elementos de la cola y lo guardamos a una variable*/
        int e = edd.saca();
        /*reasignamos a la variable la figura y sus caracteristicas*/
        estructura += String.format(etiqueta_rectangulo,100*j,0,50,25,"black","white");
        /*verificamos que el contador sea menor de la cantidad de elementos*/
        if (j + 1 < num) {
          /*reasignamos a la variable con la cadena de texto*/
          estructura += String.format(etiqueta_texto,j*100+75,20.0,"black", "➜");
      }
      /*reasignamos a la variable los elementos de la pila*/
      estructura += String.format(etiqueta_texto, j*100+25,20.0,"black", e);
      /*aumentamos el contador*/
      j++;
   }
    /*reasignamos a la variable el cierre de la etiqueta g*/
     estructura += fin_etiqueta_g;
     /*reasignamos a la variable el cierre del svg*/
     estructura += fin_svg;
     /*regresamos el codigo de la estructura*/
     return estructura;
  }
}
