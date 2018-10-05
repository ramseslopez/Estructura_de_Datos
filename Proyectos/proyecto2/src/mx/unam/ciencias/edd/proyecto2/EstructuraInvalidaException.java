package mx.unam.ciencias.edd.proyecto2;

/**
 * Excepcion para estructura que no son
 * las correctas al leer el archivo
 * @author Ramses Lopez Soto
 * @version mayo 2018
 */
 public class EstructuraInvalidaException extends IllegalArgumentException{

   /**
    * Constructor por omision
    */
    public EstructuraInvalidaException(){
      super();
    }

    /**
     * Constructor para mandar un mensaje de error
     * @param msj mensaje de error
     */
     public EstructuraInvalidaException(String msj){
       super(msj);
     }
 }
