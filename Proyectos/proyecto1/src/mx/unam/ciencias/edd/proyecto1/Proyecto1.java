package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileInputStream;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase principal en la que se compara y ordena un archivo de texto
 * @see Lista
 * @see Cadenas
 * @author Ramses Lopez Soto
 * @version marzo 2018
 */
public class Proyecto1 {

  /*varable booleana que representa la bandera -r*/
  static boolean reversa = false;
  /*varable booleana que representa la bandera -b*/
  static boolean espacios = false;
  /*varable booleana que representa la bandera -o*/
  static boolean guardar = false;
  /*Lista de cadenas de texto*/
	static Lista<String> archivos = new Lista<>();
  /*lista de objetos de tipo Cadenas*/
	static Lista<Cadenas> lista = new Lista<>();

/**
 * Metodo en el que se ejecutará el programa
 * @param pps arreglo de cadenas (argumentos)
 */
	public static void main(String[] pps) {
    /*lineas que se van a comparar y ordenar*/
    String lineas;

    /*inicializamos la variable lineas*/
    lineas = pps[0];

    /*relizamos la lectura del archivo*/
    try {
      FileInputStream archivo = new FileInputStream(lineas);
      InputStreamReader input_read = new InputStreamReader(archivo,"utf8");
      BufferedReader bfr = new BufferedReader(input_read);
      /*lineas que se van a agregar*/
      String linea;
      /*verificamos si la lectura no es vacia*/
      while((linea = bfr.readLine()) != null)
        /*agregamos las lineas a la lista*/
        lista.agrega(new Cadenas(linea));
      /*cerramos el archivo*/
      archivo.close();
      /*mandamos eun mensaje de error si lo anterior no se cumple*/
      } catch (IOException io) {
        System.out.println("Error: " + io);
      }
      /*finalizamos la lectura del archivo*/

      /*recorremos la lista y la ordenamos*/
      for (Cadenas cad : Lista.mergeSort(lista)){
        /*imprimimos la lista*/
        System.out.println(cad);
      }
      /*terminamos de recorrer la lista*/

      /*verificamos si las banderas no son el último argumento*/
      if(pps[pps.length - 1].equals("-r") || pps[pps.length - 1].equals("-o")|| pps[pps.length - 1].equals("-b")){
        /*mandamos un mensaje de error*/
        System.out.println("No se encontró fuente");
        /*terminamos el programa*/
        System.exit(0);
      }else {
        /*recorremos el arreglo y verificamos si las banderas se encuentran en los argumentos*/
        for (int i=0; i < pps.length-1; i++) {
          /*verificamos si la bandera -r se encuentra en los argumentos*/
          if (pps[i].equals("-r")){
            /*verificamos si la bandera -r no se repite*/
            if(reversa){
              /*manda un mensaje de error*/
              System.out.println("Encontré -r repetida");
              /*terminamos el programa*/
              System.exit(0);
            }
            /*volvemos la variable verdadera*/
            reversa = true;
          }
          /*final de verificar la bandera -r*/

          /*verificamos si la bandera -b se encuentra en los argumentos*/
          if (pps[i].equals("-b")){
            /*verificamos si la bandera -b no se repite*/
            if(espacios){
                /*manda un mensaje de error*/
                System.out.println("Encontré -b repetida");
                /*terminamos el programa*/
                System.exit(0);
            }
            /*volvemos la varable verdadera*/
            espacios = true;
          }
          /*final de verificar la bandera -b*/

          /*verificamos si la bandera -o se encuentra en los argumentos*/
          if (pps[i].equals("-o")){
            /*verificamos si la bandera -o no se repite*/
            if(guardar){
              /*manda un mensaje de error*/
              System.out.println("Encontré -o repetida");
              /*terminamos el programa*/
              System.exit(0);
            }
            /*volvemos la varable verdadera*/
            guardar = true;
            }
            /*final de verificar la bandera -o*/
          }
          /*terminamos de recorrer el arreglo y verificar banderas*/

          /*verificamos si la bandera -r está en los argumentos*/
          if(reversa == true){
              /*colocamos el archivo a leer en la siguiente localidad*/
              lineas = pps[1];
              /*realizamos la lectura del archivo*/
              try {
          			FileInputStream fis = new FileInputStream(lineas);
                InputStreamReader isr = new InputStreamReader(fis,"utf8");
                BufferedReader br = new BufferedReader(isr);

                String linea;
                /*verificamos si la lectura no es vacia*/
                while((linea = br.readLine()) != null)
                  /*agregamos las cadenas a una lista*/
                  lista.agrega(new Cadenas(linea));
                /*cerramos el archivo*/
                fis.close();
              /*mandamos un mensaje de erro si lo anterior no se cumple*/
          		} catch (IOException io) {
          				System.out.println("Error: " + io);
          		}
              /*finalizamos la lectura del archivo*/

              /*recorremos la lista, la ordemos y obtenemos la reversa*/
          		for (Cadenas cad : Lista.mergeSort(lista).reversa()){
                /*imprimimos la lista*/
          			System.out.println(cad);
          		}
          /*verificamos si la bandera -b está en los argumentos*/
          } else if (espacios == true) {
            lineas = pps[1];
            /*realizamos la lectura del archivo*/
            try {
              FileInputStream fis = new FileInputStream(lineas);
              InputStreamReader isr = new InputStreamReader(fis,"utf8");
              BufferedReader br = new BufferedReader(isr);

              String linea;
              /*verificamos si la lectura no es vacia*/
              while((linea = br.readLine()) != null)
                /*agregamos las cadenas a una lista*/
                lista.agrega(new Cadenas(linea));
              /*cerramos el archivo*/
              fis.close();
            /*mandamos un mensaje de erro si lo anterior no se cumple*/
            } catch (IOException io) {
                System.out.println("Error: " + io);
            }
            /*terminamos la lectura del archivo*/

            /*recorremos la lista, la ordemos y obtenemos la reversa*/
            for (Cadenas cad : Lista.mergeSort(lista)){
              /*imprimimos la lista*/
              System.out.println(cad);
            }
          }
          /*final de verificar la bandera -b*/
        }
        /*final de verificar argumentos*/

      }
}
