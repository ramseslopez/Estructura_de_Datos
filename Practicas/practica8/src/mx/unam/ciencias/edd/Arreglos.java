package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T [] arreglo, Comparator<T> comparador) {
      quickSort_0(arreglo, comparador, 0 , arreglo.length - 1);
    }

    /**
     * Metodo auxiliar para quickSort
     * @param <T> tipo del que puede ser el arreglos
     * @param arreglo el arreglo a ordenar
     * @param comparador el comparador para ordenar el arreglo
     * @param izq primer indice del arreglo
     * @param der ultimo indice del arreglo
     */
    private static <T> void quickSort_0(T [] arreglo, Comparator<T> comparador, int izq, int der){
      int pivote;
      if (izq >= der) {
        return;
      }
      int i = izq;
      int j = der;
      if (izq != der) {
        pivote = izq;
        while (izq != der) {
          while (comparador.compare(arreglo[der], arreglo[izq]) >= 0 && izq < der)
            der--;
            while(comparador.compare(arreglo[izq], arreglo[der]) < 0 && izq < der)
              izq++;

          if (der != izq) {
            swap(arreglo, der, izq);
          }
          if (izq == der) {
            quickSort_0(arreglo, comparador, i, izq - 1);
            quickSort_0(arreglo, comparador, izq + 1, j);
          }
        }
       }
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T [] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T [] arreglo, Comparator<T> comparador) {
        int menor;
        for (int i = 0; i < arreglo.length - 1; i++) {
            menor = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j],arreglo[menor]) < 0) {
                    swap(arreglo, menor, j);
                }
            }
            swap(arreglo, i , menor);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T [] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T [] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinaria_0(arreglo, elemento, comparador, 0, arreglo.length - 1);
    }

    /**
     * Metodo auxiliar para busquedaBinaria.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @param izq subarreglo izquierdo del arreglo original.
     * @param der subarreglo derecho del arreglo original.
     * @return el índice del elemento del arreglo, o -1 si no se encuentra.
     */
     private static <T> int busquedaBinaria_0(T [] arreglo, T elemento, Comparator<T> comparador, int izq, int der){
         int mitad = (izq + der)/2;
         if (izq > der) {
             return - 1;
         }
         if (comparador.compare(arreglo[mitad], elemento) < 0) {
             return busquedaBinaria_0(arreglo, elemento, comparador, mitad + 1, der);
         } else if (comparador.compare(arreglo[mitad], elemento) > 0){
             return busquedaBinaria_0(arreglo, elemento, comparador, izq, mitad - 1);
         } else {
             return mitad;
         }
     }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T [] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Metodo auxiliar para intercambiar valores
     * @param <T> tipo del que puede ser el arreglo
     * @param arreglo arreglo del que se intercambian valores
     * @param i valor a intercambiar a la posicion j
     * @param j valor a intercambiar a la posicion i
     */
    private static <T> void swap(T [] arreglo, int i, int j){
		T temp = arreglo[i];
		arreglo[i] = arreglo[j];
		arreglo[j] = temp;
    }

}
