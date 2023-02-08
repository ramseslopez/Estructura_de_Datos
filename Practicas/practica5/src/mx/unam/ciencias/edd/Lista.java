package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) throws IllegalArgumentException{
            if(elemento == null)
                throw new IllegalArgumentException("Elemento nulo");
            this.elemento = elemento;
        }

    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() throws NoSuchElementException {
            if(siguiente == null)
                throw new NoSuchElementException("Elemento nulo");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() throws NoSuchElementException {
            if(anterior == null)
                throw new NoSuchElementException("Elemento nulo");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            siguiente = cabeza;
            anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return getElementos();
    }

    /**
     * Regresa el número apelementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException{
        if (elemento == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }
        Nodo n = new Nodo(elemento);
        if (cabeza == null) {
            cabeza = rabo = n;
        } else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
        longitud++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) throws IllegalArgumentException{
        if (elemento == null) {
            throw new IllegalArgumentException("Elemento nulo");
        }
        Nodo n = new Nodo(elemento);
        if (cabeza == null) {
            cabeza = rabo = n;
        } else {
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
        }
        longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al final de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) throws IllegalArgumentException{
        if (elemento == null) { throw new IllegalArgumentException("Elemento nulo"); }
        if (i <= 0) {
            agregaInicio(elemento);
        } else if (i >= getElementos()) {
            agregaFinal(elemento);
        } else {
            Nodo n = new Nodo(elemento);
            Nodo s = iesimoNodo(i);
            n.anterior = s.anterior;
            s.anterior.siguiente = n;
            n.siguiente = s;
            s.anterior = n;
            longitud++;
        }
    }

    /**
     * Metodo que regresa el i-esimo nodo de la lista
     * @param i indice del nodo a buscar
     * @return el i-esimo nodo
     */
    private Nodo iesimoNodo(int i){
      if (cabeza == null) {
          return null;
      } else {
          Nodo n2 = cabeza;
          int a = 0;
          while (a < i && n2.siguiente != null) {
              n2 = n2.siguiente;
              a++;
          }
          if (a != i)
              return null;
          return n2;
      }
    }
    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Nodo nodo = buscarNodo(elemento, cabeza);
        if (nodo == null) {
            return;
        } else if (cabeza == rabo) {
            cabeza = rabo = null;
        } else if (cabeza == nodo) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } else if (rabo == nodo) {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        } else {
            nodo.siguiente.anterior = nodo.anterior;
            nodo.anterior.siguiente = nodo.siguiente;
        }
        longitud--;
    }


    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() throws NoSuchElementException{
        if (cabeza == null){ throw new NoSuchElementException("La lista no tiene elementos"); }
        T elem = cabeza.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        longitud--;
        return elem;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() throws NoSuchElementException{
        if (cabeza == null){ throw new NoSuchElementException("La lista no tiene elementos"); }
            T elem = rabo.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        }
        longitud--;
        return elem;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscarNodo(elemento, cabeza) != null;
    }

    /**
     * Busca un elemento dentro de la lista de manera recursiva
     * @param elemento elemento que se va a buscar
     */
    private Nodo buscarNodo(T elemento, Nodo nodo){
        if (nodo == null || nodo.elemento.equals(elemento)) {
            return nodo;
        }
        return buscarNodo(elemento, nodo.siguiente);
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<>();
        return reversa_0(lista, cabeza);
    }

    /**
     * Metodo recursivo para invertir los elementos de una lista
     * @param lista lista que se va a invertir
     * @param nodo nodo del cual se comenzara
     * @return nueva lista con los elementos invertidos
     */
    private Lista <T> reversa_0(Lista <T> lista, Nodo nodo){
        if (nodo == null)
            return lista;
        lista.agregaInicio(nodo.elemento);
        return reversa_0(lista, nodo.siguiente);
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copia de la lista.
     */ 
    public Lista<T> copia() {
        Lista<T> lista = new Lista<T>();
        return copia_0(lista, cabeza);
    }

    /**
     * Metodo recursivo para copiar los elementos de una lista
     * @param lista lista a la que se copiara el elemento
     * @param nodo nodo del cual se comenzará a copiara
     * @return nueva lista con los elemento copiados
     */
    private Lista<T> copia_0(Lista<T> lista, Nodo nodo){
        if (nodo == null) {
            return lista;
        }
        lista.agrega(nodo.elemento);
        return copia_0(lista, nodo.siguiente);
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() throws NoSuchElementException{
        if (cabeza == null){
            throw new NoSuchElementException("La lista no tiene elementos");
        } else {
            return cabeza.elemento;
        }
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() throws NoSuchElementException{
        if (cabeza == null){
            throw new NoSuchElementException("La lista no tiene elementos");
        } else {
            return rabo.elemento;
        }
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= getElementos()) { throw new ExcepcionIndiceInvalido("Indice invalido"); }
        Nodo s = iesimoNodo(i);
        return s.elemento;
    }


    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        Nodo nodo = cabeza;
        for (int cont = 0; cont < getLongitud(); cont++) {
            if (nodo.elemento.equals(elemento)) {
                return cont;
            }
            nodo = nodo.siguiente;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        Nodo nodo = cabeza;
        String r;
        if (nodo == null) {
          r = "[]";
        } else {
          r = "[" + nodo.elemento;
          for (int i = 1; i < getLongitud(); i++) {
            if (nodo.siguiente != null) {
              r += ", " + nodo.siguiente.elemento;
            }
            nodo = nodo.siguiente;
          }
          r += "]";
        }
        return r;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        if (getLongitud() != lista.getLongitud()) { return false; }
            Nodo nodo = cabeza;
            Nodo nodo2 = lista.cabeza;
            while (nodo != nodo2) {
                if (!nodo.elemento.equals(nodo2.elemento))
                    return false;
                nodo = nodo.siguiente;
                nodo2 = nodo2.siguiente;
            }
            return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        if (longitud < 2) {
            return copia();
        }
        Lista<T> li = new Lista<>(), ld = new Lista<>();
        int i = 0;
        for(T el : this){
            Lista<T> ll = (i++ < longitud/2) ? li : ld;
            ll.agrega(el);
        }
        return mezcla(li.mergeSort(comparador),ld.mergeSort(comparador),comparador);
    }

    private Lista<T> mezcla(Lista<T> listaizq, Lista<T> listader, Comparator<T> c) {
        Lista<T> lista = new Lista<>();
        Nodo nodo = listaizq.cabeza;
        Nodo nodo2 = listader.cabeza;
        while (nodo != null && nodo2 != null) {
            if (c.compare(nodo.elemento, nodo2.elemento) <= 0) {
                lista.agrega(nodo.elemento);
                nodo = nodo.siguiente;
            } else {
                lista.agrega(nodo2.elemento);
                nodo2 = nodo2.siguiente;
            }
        }
        Nodo n;
        if (nodo != null) {
            n = nodo;
        } else {
            n = nodo2;
        }
        while (n != null) {
            lista.agrega(n.elemento);
            n = n.siguiente;
        }
        return lista;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        Nodo nodo = cabeza;
        while (nodo != null) {
            if (comparador.compare(nodo.elemento, elemento) == 0) {
                return true;
            }
            nodo = nodo.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }

}
