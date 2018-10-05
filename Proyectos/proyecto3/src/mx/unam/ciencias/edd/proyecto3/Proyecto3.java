package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileInputStream;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
import java.text.Normalizer;

public class Proyecto3 {

  static boolean banderaO = false;

  static Lista<String> p = new Lista<>();

  static Diccionario<String, Integer> diccionario = new Diccionario<>();

  public static void main(String[] pps) {
    String archivo = pps[1];
    String directorio = pps[2];
    File dir = new File(directorio);

    if(pps[pps.length - 1].equals("-o")){
        System.out.println("No se encontró fuente");
        System.exit(0);
      }else {
        for (int i = 0; i < pps.length-1; i++) {
          if (pps[i].equals("-o")){
            if(banderaO){
              System.out.println("Encontré -o repetida");
              System.exit(0);
            }
            banderaO = true;
            File direct = new File(directorio);
            dir.mkdir();
            }
          }
      }

      String lineas;

    lineas = pps[0];

    try {
      FileInputStream arch = new FileInputStream(lineas);
      InputStreamReader input_read = new InputStreamReader(arch,"utf8");
      BufferedReader bfr = new BufferedReader(input_read);
      String linea;
      String [] arreglo;
      while((linea = bfr.readLine()) != null){
        arreglo = linea.replaceAll(" +", " ").split(" ");
        for (int cont = 0; cont < arreglo.length; cont++) {
          arreglo[cont] = Normalizer.normalize(arreglo[cont], Normalizer.Form.NFD);
          arreglo[cont] = arreglo[cont].replaceAll("[^\\p{ASCII}]", "").replaceAll("[.:;¡!¿?/#$&()=\\-,'\"]","");
          if (!arreglo[cont].equals("") && !arreglo[cont].equals(" ")) {
            p.agrega(arreglo[cont]);
          }
        }
      }
      arch.close();
      } catch (IOException io) {
        System.out.println("Error: " + io);
      }
      for (String cadena : p)
          try {
            int n = 0;
            n = diccionario.get(cadena) + 1;
            diccionario.agrega(cadena,n);
          } catch (Exception e) {
          diccionario.agrega(cadena,1);
          }
      System.out.println(Lista.mergeSort(p));
      System.out.println("El archivo tiene " + p.getLongitud() + " palabras");
      }
}
