package edu.escuelaing.arep.TallerVirtualizacion;
import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class SparkWebServer
{
    public static void main( String[] args ){
        port(getPor());
        get("/hello", ((request, response) -> "Hello World Docker"));
    }

    private static int getPor() {
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
