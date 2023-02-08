package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
      Nodo nodo = cabeza;
          String r = "";
          while (nodo != null) {
            r += nodo.elemento + ",";
            nodo = nodo.siguiente;
          }
          return r;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) throws IllegalArgumentException{
        if (elemento == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }
        Nodo nodo = new Nodo(elemento);
        if(rabo == null){
            cabeza = rabo = nodo;
        } else {
            rabo.siguiente = nodo;
            rabo = nodo;
        }
    }
}
