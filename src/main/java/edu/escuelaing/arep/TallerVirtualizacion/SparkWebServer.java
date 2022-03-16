package edu.escuelaing.arep.TallerVirtualizacion;
import static spark.Spark.*;
/**
 * Esta clase define los subprocesos para empezar a ejecutrar.
 * @author Deivid Medina
 * @version 15/03/2022
 */

public class SparkWebServer
{
    /**
     * Clase principal main, la cual invoca los métodos correspondientes para ejecutar el main.
     * @param args - String []
     */
    public static void main( String[] args ){
        port(getPor());
        get("/hello", ((request, response) -> "Hello World Docker"));
    }

    /**
     * Método que me permite obtener el puerto donde se ejecutara la aplicación, en caso de no ser definido por defecto se dara el puerto 4567
     * @return int - Retorna el puerto obtenido o sino por defecto el puerto 4567.
     */
    private static int getPor() {
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
