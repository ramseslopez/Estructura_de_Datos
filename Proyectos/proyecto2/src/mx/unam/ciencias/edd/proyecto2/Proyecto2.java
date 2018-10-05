package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.MonticuloMinimo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileInputStream;

/**
 * Programa para generar imágenes con SVG
 * @author Ramses Lopez Soto
 * @version mayo 2018
 */
 public class Proyecto2{
   /*contador statico para reconocer la estructura*/
   static int c;
   /*cadena estatica*/
   /*lista statica de enteros*/
   static Lista<Integer> lista;
   /*cola estatica de enteros*/
   static Cola<Integer> cola;
   /*pila estatica de enteros*/
   static Pila<Integer> pila;
   static String estD;
   /**
    * Metodo principal para ejecutar el programa
    * @param pps arreglo de cadenas
    */
   public static void main(String[] pps) {
     /*declaramos un objeto de la clase DibujaLPC*/
     DibujaLPC dibuja;
     /*inicializamos la lista de enteros*/
     lista = new Lista<>();
     /*cadena de lineas que representan las lineas del archivo*/
     String lineas;
     /*contador*/
     int contador = 0;
     /*inicializamos la cadena con la primera localidad del arreglo*/
     lineas = pps[0];
     /*inicializamos la cadena con una cadena vacia*/
     String estructuraFinal = "";
     /*inicializamos la cadena con una cadena vacia*/
     estD = "";
     /*realizamos la lectura del archivo*/
     try {
       FileInputStream archivo = new FileInputStream(lineas);
       InputStreamReader input_read = new InputStreamReader(archivo,"utf8");
       BufferedReader bfr = new BufferedReader(input_read);
       /*lineas que se agregaran a la estructura*/
       String linea;
       /**verificamos si el archivo no es vacio*/
       while((linea = bfr.readLine()) != null){
         /*inicializamos la cadena con el metoso para eliminar #*/
         linea = eliminaGato(linea);
         /*vefificamos que la cadena sea distinta de la cadena vacia*/
         if(!linea.equals("")){
           /*inicializamos la cadena con el metodo para reconocer la estructura*/
           estD = leeEstructura(linea);
           /*verificamos que el contador sea cero*/
           if(contador == 0){
             /*asignamos la la estructuraFinal con la estructura ya leída*/
             estructuraFinal = estD;
             /*aumentamos el contador*/
             contador++;
           }
           /*eliminmos la estructura del archivo*/
           linea = quitaEstructura(linea);
           try{
             /*agregamos los elementos de la estructura a una lista*/
             agregaElementos(linea);
           }catch (NumberFormatException e) {
               System.out.println("Estructura invalida");
             }
         }
       }
       /*cerramos el archivo*/
       archivo.close();
       /*mandamos un error si lo anterior no se cumple*/
     } catch (IOException io) {
       System.out.println("Error: " + io);
     }

     /*inicializamos el objeto de la clase DibujaLPC*/
     dibuja = new DibujaLPC();
     /*verificamos si la estructura es una lista*/
      if (estD.equals("Lista")){
        /*imprimimos el codigo de la imagen de lista*/
       System.out.println(dibuja.drawList(lista));
       /*verificamos si la estructura es una Cola*/
     } else if (estD.equals("Cola")) {
       /*inicializamos la cola*/
       cola = new Cola<>();
       /*iteramos la lista estatica*/
       for (int i : lista) {
         /*metemos los elementos a la cola*/
         cola.mete(i);
       }
       /*imprimimos el codigo de la imagen de cola*/
       System.out.println(dibuja.drawQueues(cola, lista.getLongitud()));
       /*verificamos si la estructura es uan pila*/
     }else if(estD.equals("Pila")){
       /*inicializamos la pila*/
       pila = new Pila<>();
       /*iteramos la lista estatica*/
       for (int i : lista) {
         /*metemos los elementos a la pila*/
         pila.mete(i);
       }
       /*imprimimos el codigo de la imagen de pila*/
       System.out.println(dibuja.drawStack(pila, lista.getLongitud()));
     }

   }

   /**
    * Metodo para verificar si el archivo contiene una estructura
    * @param edd estructura a reconocer
    */
   private static String leeEstructura(String edd) throws EstructuraInvalidaException{
     /*inicializamos la cadena con la cadena vacia*/
     String a = "";
     /*verificamos si el archivo contiene la palabara Lista*/
     if (edd.contains("Lista")) {
       /*aumenrtamos el contador estatico*/
       c++;
       /*asignamos a la cadena a la palabra Lista*/
       a = "Lista";
     }
     /*verificamos si el archivo contiene la palabra Pila*/
     if (edd.contains("Pila")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadeana a la palabra Pila*/
       a = "Pila";
     }
     /*verificamos si el archivo contiene la palabra Cola*/
     if (edd.contains("Cola")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadena a la palabra Cola*/
       a = "Cola";
     }
     /*verificamos si el archivo contiene la palabra ArbolBinarioCompleto*/
     if (edd.contains("ArbolBinarioCompleto")) {
       /*aumentamos el contador estatica*/
       c++;
       /*asignamos a la cadena a la palabra ArbolBinarioCompleto*/
       a = "ArbolBinarioCompleto";
     }
     /*verificamos si el archivo contiene la palabara ArbolBinarioOrdenado*/
     if (edd.contains("ArbolBinarioOrdenado")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadean a la palabra ArbolBinarioOrdenado*/
       a = "ArbolBinarioOrdenado";
     }
     /*verificamos el archivo contiene la palabra ArbolRojinegro*/
     if (edd.contains("ArbolRojinegro")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadena a la palabra ArbolRojinegro*/
       a = "ArbolRojinegro";
     }
     /*verificamos si el archivo contiene la palabra ArbolAVL*/
     if (edd.contains("ArbolAVL")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadena a la palabra ArbolAVL*/
       a = "ArbolAVL";
     }
     /*verificamos si el archivo contiene la palabra Grafica*/
     if (edd.contains("Grafica")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadena a la palabra Grafica*/
       a = "Grafica";
     }
     /*verificamos si el archivo contiene la palabra MonticuloMinimo*/
     if (edd.contains("MonticuloMinimo")) {
       /*aumentamos el contador estatico*/
       c++;
       /*asignamos a la cadena a la palabra MonticuloMinimo*/
       a = "MonticuloMinimo";
     }
     /*verificamos si el contador es 1*/
     if (c > 1) {
       /*mandamos un error si el contador es distinto a 1*/
         throw new EstructuraInvalidaException("Estructura invalida :'v");
     }
     /*regresamos la cadena a*/
     return a;
   }

   /**
    * Metodo para ignorar la estructura y reconocer sólo los elementos
    * @param lin archivo con el cual solo reconocera los numeros
    * @return String - la cadena
    */
    private static String quitaEstructura(String lin) throws EstructuraInvalidaException{
      /*declaramos e inicializamos la cadeana con la cadean que nos pasa el parametro*/
      String inf = lin;
      /*declaramos e inicializamos una variable entera en cero*/
      int i = 0;
      /*verificamos si el archivo contiene la palabra Lista*/
      if (lin.contains("Lista")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena Lista*/
        i = lin.indexOf("Lista");
        /*reasignamos a la cadena la subcadena sin la palabra Lista*/
        inf = (i == 0) ? lin.substring(5).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 5).trim();
        /*verificamos si el archivo contiene la palabra Pila*/
      } else if (lin.contains("Pila")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena Pila*/
        i = lin.indexOf("Pila");
        /*reasignamos a la cadena la subcadena sin la palabra Pila*/
        inf = (i == 0) ? lin.substring(4).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 4).trim();
        /*verificamos si el archivo contiene a la palabra Cola*/
      } else if (lin.contains("Cola")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena Cola*/
        i = lin.indexOf("Cola");
        /*reasignamos a la cadena la subcadena sin la palabra Cola*/
        inf = (i == 0) ? lin.substring(4).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 4).trim();
        /*verificamos si el archivo contiene la palabra ArbolBinarioOrdenado*/
      } else if (lin.contains("ArbolBinarioOrdenado")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena ArbolBinarioOrdenado*/
        i = lin.indexOf("ArbolBinarioOrdenado");
        /*reasignamos a la cadena la subcadena sin la palabra ArbolBinarioOrdenado*/
        inf = (i == 0) ? lin.substring(20).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 20).trim();
        /*verificamos si el archivo contiene la palabra ArbolBinarioCompleto*/
      } else if (lin.contains("ArbolBinarioCompleto")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena ArbolBinarioCompleto*/
        i = lin.indexOf("ArbolBinarioCompleto");
        /*reasignamos a la cadena la subcadena sin la palabra ArbolBinarioCompleto*/
        inf = (i == 0) ? lin.substring(20).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 20).trim();
        /*verificamos si el archivo contiene la palabra ArbolRojinegro*/
      } else if (lin.contains("ArbolRojinegro")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena ArbolRojinegro*/
        i = lin.indexOf("ArbolRojinegro");
        /*reasignamos a la cadena la subcadena sin la palabra ArbolRojinegro*/
        inf = (i == 0) ? lin.substring(14).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 14).trim();
        /*verificamos si el archivo contiene la palabra ArbolAVL*/
      } else if (lin.contains("ArbolAVL")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena ArbolAVL*/
        i = lin.indexOf("ArbolAVL");
        /*reasignamos a la cadena la subcadena sin la palabra ArbolAVL*/
        inf = (i == 0) ? lin.substring(8).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 8).trim();
        /*verificamos si el archivo contiene la palabra Grafica*/
      } else if (lin.contains("Grafica")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena Grafica*/
        i = lin.indexOf("Grafica");
        /*reasignamos a la cadena la subcadena sin la palabra Grafica*/
        inf = (i == 0) ? lin.substring(7).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 7).trim();
        /*verificamos si el archivo contiene la palabra MonticuloMinimo*/
      } else if (lin.contains("MonticuloMinimo")) {
        /*asignamos a la variable entera la posicion donde se encuentra la cadena MonticuloMinimo*/
        i = lin.indexOf("MonticuloMinimo");
        /*reasignamos a la cadena la subcadena sin la palabra MonticuloMinimo*/
        inf = (i == 0) ? lin.substring(15).trim() : lin.substring(0, i - 1).trim() + lin.substring(i + 15).trim();
      }
      return inf;
    }

    /**
     * Metodo para verificar que los elementos sean enteros
     * @param ms cadena de texto que se encuentran en el archivo;
     * @return Lista<Integer> - lista de enteros
     */
    private static Lista<Integer> agregaElementos(String ms){
      /*declaramos e inicializamos un arreglo de cadenas con la cadena recibida*/
      String [] str = ms.replaceAll("\\s"," ").split(" ");
      /*iteramos es arreglo y agregamos los elementos*/
      for (String s : str) {
        /*verificamos que la cadena sea distinta de null y no sea vacia*/
        if(!s.equals("") && s != null){
          /*agregamos los elementos a la lista estatica*/
          lista.agrega(Integer.parseInt(s.trim()));
        }
      }
      /*regresamos la lista con los elementos*/
      return lista;
    }

    /**
     * Metodo para ignorar las lineas de texto que contienen #
     * @param edd cadena de texto que se encuentra en el archivo
     */
    private static String eliminaGato(String edd){
      /*declaramos una variable de cadenas y la inicializamos con la cadena recibida*/
      String a = edd;
      /*declaramos una variable entera para los indices*/
      int i = 0;
      /*verificamos si la cadena de texto contiene un #*/
      if (edd.contains("#")) {
        /*asignamos a la variable entera el indice donde se encuentra #*/
        i = edd.indexOf("#");
        /*reasignamos a la variable la subcadena donde se encuentre #*/
        a = (i == 0) ? "" : edd.substring(0, i-1);
      }
      /*regresamos la cadena sin #*/
      return a;
    }

 }
