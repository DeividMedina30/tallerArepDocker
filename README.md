# AREP- ARQUITECTURAS EMPRESARIAL.

## AREP- TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER Y A AWS

### INTRODUCCIÓN.

El taller consiste en crear una aplicación web pequeña usando el micro-framework de Spark java
(http://sparkjava.com/). Una vez tengamos esta aplicación procederemos a construir un container 
para docker para la aplicación y los desplegaremos y configuraremos en nuestra máquina local. 
Luego, cerremos un repositorio en DockerHub y subiremos la imagen al repositorio. Finalmente, 
crearemos una máquina virtual de en AWS, instalaremos Docker , y desplegaremos el contenedor que 
acabamos de crear.

**El siguiente taller constará de 4 partes.**

1. Primera parte crear la aplicación web
2. Segunda Parte: crear imagen para docker y subirla
3. Tercera para subir la imagen a Docker Hub
4. Cuarta parte: AWS

### Primera parte crear la aplicación web.

1. Cree un proyecto java usando maven.
2. Cree la clase principal.
3. Importe las dependencias de spark Java en el archivo pom
4. Asegúrese que el proyecto esté compilando hacia la versión 8 de Java, en el archivo pom
5. Asegúrese que el proyecto este copiando las dependencias en el directorio target al compilar el proyecto. Esto es necesario para poder construir una imagen de contenedor de docker usando los archivos ya compilados de java. Para hacer esto use el purgan de dependencias de Maven. En archivo pom.
6. Compilar Proyecto.
7. Verificar que se crearon las dependencias en la carpeta target.
8. probar programa.


**Cree un proyecto java usando maven**

Para crear el proyecto hacemos uso del siguiente comando.
Abrimos primero una consola cmd y ejecutamos el siguiente codigo.

```Crear proyecto
mvn archetype:generate -DgroupId=edu.escuelaing.arep.TallerVirtualizacion -DartifactId = miprimer-virtualizacionDocker -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

**Cree un proyecto java usando maven**

Creamos la siguiente clase, donde puedes cambiar los comentarios o eliminar si así se prefiere.

```CrearClasePrincipal
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
```

**Importe las dependencias de spark Java en el archivo pom**

En el archivo pom.xml agrege las siguientes dependencias.

```Dependencias
    <dependencies>
        <!-- https://mvnrepository.com/artifact/com.sparkjava/spark-core -->
        <dependency>
            <groupId>com.sparkjava</groupId>
            <artifactId>spark-core</artifactId>
            <version>2.9.2</version>
        </dependency>
    </dependencies>
```

**Asegúrese que el proyecto esté compilando hacia la versión 8 de Java, en el archivo pom**

```Propiedades
    <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>8</maven.compiler.source>
            <maven.compiler.target>8</maven.compiler.target>
    </properties>
```

**Asegúrese que el proyecto este copiando las dependencias en el directorio target. En archivo pom.**

```Plugin
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.0.1</version>
                <executions>
                    <execution>
                        <id>copy-dependencies</id>
                        <phase>package</phase>
                        <goals><goal>copy-dependencies</goal></goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

**Compilar Proyecto.**

Para compilar el proyecto debemos dirigirnos a la ruta de la carpeta del proyecto y abrir un cmd.

```Compilar
    mvn clean install
```

Si todo salio bien debe obtener algo similar.

```Compilar2
   [INFO] Scanning for projects...
[INFO]
[INFO] --< edu.escuelaing.arep.TallerVirtualizacion:miprimer-virtualizacionDocker >--
[INFO] Building miprimer-virtualizacionDocker 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ miprimer-virtualizacionDocker ---
[INFO] Deleting D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ miprimer-virtualizacionDocker ---
[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\src\main\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ miprimer-virtualizacionDocker ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding Cp1252, i.e. build is platform dependent!
[INFO] Compiling 1 source file to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\classes
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ miprimer-virtualizacionDocker ---
[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\src\test\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ miprimer-virtualizacionDocker ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding Cp1252, i.e. build is platform dependent!
[INFO] Compiling 1 source file to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ miprimer-virtualizacionDocker ---
[INFO] Surefire report directory: D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running edu.escuelaing.arep.TallerVirtualizacion.AppTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ miprimer-virtualizacionDocker ---
[INFO] Building jar: D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\miprimer-virtualizacionDocker-1.0-SNAPSHOT.jar
[INFO]
[INFO] --- maven-dependency-plugin:3.0.1:copy-dependencies (copy-dependencies) @ miprimer-virtualizacionDocker ---
[INFO] Copying junit-3.8.1.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\junit-3.8.1.jar
[INFO] Copying spark-core-2.9.3.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\spark-core-2.9.3.jar
[INFO] Copying jetty-server-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-server-9.4.31.v20200723.jar
[INFO] Copying javax.servlet-api-3.1.0.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\javax.servlet-api-3.1.0.jar
[INFO] Copying jetty-http-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-http-9.4.31.v20200723.jar
[INFO] Copying jetty-util-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-util-9.4.31.v20200723.jar
[INFO] Copying jetty-io-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-io-9.4.31.v20200723.jar
[INFO] Copying jetty-webapp-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-webapp-9.4.31.v20200723.jar
[INFO] Copying jetty-xml-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-xml-9.4.31.v20200723.jar
[INFO] Copying jetty-servlet-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-servlet-9.4.31.v20200723.jar
[INFO] Copying jetty-security-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-security-9.4.31.v20200723.jar
[INFO] Copying websocket-server-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\websocket-server-9.4.31.v20200723.jar
[INFO] Copying websocket-common-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\websocket-common-9.4.31.v20200723.jar
[INFO] Copying websocket-client-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\websocket-client-9.4.31.v20200723.jar
[INFO] Copying jetty-client-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\jetty-client-9.4.31.v20200723.jar
[INFO] Copying websocket-servlet-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\websocket-servlet-9.4.31.v20200723.jar
[INFO] Copying websocket-api-9.4.31.v20200723.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\websocket-api-9.4.31.v20200723.jar
[INFO] Copying slf4j-simple-1.7.35.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\slf4j-simple-1.7.35.jar
[INFO] Copying slf4j-api-1.7.35.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\slf4j-api-1.7.35.jar
[INFO] Copying gson-2.8.9.jar to D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\dependency\gson-2.8.9.jar
[INFO]
[INFO] --- maven-install-plugin:2.4:install (default-install) @ miprimer-virtualizacionDocker ---
[INFO] Installing D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\target\miprimer-virtualizacionDocker-1.0-SNAPSHOT.jar to C:\Users\Deivid\.m2\repository\edu\escuelaing\arep\TallerVirtualizacion\miprimer-virtualizacionDocker\1.0-SNAPSHOT\miprimer-virtualizacionDocker-1.0-SNAPSHOT.jar
[INFO] Installing D:\Escritorio\Universidad\AREP\LABORATORIO\CORTE DOS\miprimer-virtualizacionDocker\pom.xml to C:\Users\Deivid\.m2\repository\edu\escuelaing\arep\TallerVirtualizacion\miprimer-virtualizacionDocker\1.0-SNAPSHOT\miprimer-virtualizacionDocker-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.465 s
[INFO] Finished at: 2022-03-15T17:44:24-05:00
[INFO] ------------------------------------------------------------------------
 
```

**Verificar que se crearon las dependencias en la carpeta target.**

![primeraparte1.png](https://i.postimg.cc/nzPstB1L/primeraparte1.png)

**probar programa.**

Para probar el programa debemos de ejecutar desde la carpeta del proyecto en un cmd.

```EjecutarPrograma
   java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.TallerVirtualizacion.SparkWebServer
```

Si funciono todo correctamente. Puedes abrir la aplicación desde un navegador.

![primeraparte2.png](https://i.postimg.cc/4xxmnWF3/primeraparte2.png)

### PASOS PARA CLONAR.

-  Nos dirigimos a la parte superior de nuestra ubicación, donde daremos click y escribimos la palabra cmd, luego damos enter, con el fin de desplegar
   el Command Prompt, el cual es necesario.

![img1.png](https://i.postimg.cc/GmSNVZZL/img1.png)

![Imagen2.png](https://i.postimg.cc/vB5N1DDT/Imagen2.png)

![Imagen3.png](https://i.postimg.cc/T3hNVthZ/Imagen3.png)

- Una vez desplegado el Command Prompt, pasamos a verificar que tengamos instalado git, ya que sin él no podremos realizar la descarga.
  Para esto ejecutamos el siguiente comando.

`git --version`

![Imagen4.png](https://i.postimg.cc/nh5R0qDM/Imagen4.png)

- Si contamos con git instalado, tendra que salir algo similar. La version puede variar de cuando se este realizando este tutorial.
  Si no cuenta con git, puede ver este tutorial.

[Instalación de Git][id/name]

[id/name]: https://www.youtube.com/watch?v=cYLapo1FFmA

![Imagen5.png](https://i.postimg.cc/fR6CxZG9/Imagen5.png)

-  Una vez comprobado de que contamos con git. pasamos a escribir el siguiente comando. git clone,
   que significa que clonamos el repositorio, y damos la url del repositorio.

`$ git clone https://github.com/DeividMedina30/tallerArepDocker.git`

![Imagen6.png](https://i.postimg.cc/gjkHY0Zf/Imagen6.png)

- Luego podemos acceder al proyecto escribiendo.

`$ cd tallerArepDocker`

### TECNOLOGÍAS USADAS PARA EL DESARROLLO DEL LABORATORIO.

* [Maven](https://maven.apache.org/) - Administrador de dependencias.

* [GitHub](https://github.com/) - Forja para alojar proyectos utilizando el sistema de control de versiones Git.

* [Spark](http://sparkjava.com/) - Spark Framework es un DSL de marco web Java/Kotlin.

* [Docker](https://www.docker.com/) - Automatiza el despliegue de aplicaciones dentro de contenedores de software.

* [DockerHub](https://hub.docker.com/) - Administrar y entregar las aplicaciones de contenedores de sus equipos.

* [AWS](https://aws.amazon.com/es/free/?trk=eb709b95-5dcd-4cf8-8929-6f13b8f2781f&sc_channel=ps&sc_campaign=acquisition&sc_medium=ACQ-P|PS-GO|Brand|Desktop|SU|Core-Main|Core|LATAMO|ES|Text&ef_id=EAIaIQobChMIoueptLLJ9gIVw52GCh2YxwNgEAAYASAAEgIqMPD_BwE:G:s&s_kwcid=AL!4422!3!561348326837!e!!g!!aws&ef_id=EAIaIQobChMIoueptLLJ9gIVw52GCh2YxwNgEAAYASAAEgIqMPD_BwE:G:s&s_kwcid=AL!4422!3!561348326837!e!!g!!aws&all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all) - Colección de servicios de computación en la nube pública.
 

### LIMITACIONES.

Para este laboratorio se encontró con la limitación de que docker, no corre exactamente que digamos
en Windows. Por lo cual se tuvo que proceder a hacer uso de un tutorial donde nos mostraban 
como resolver dicho problema. El video del tutorial se encuentra en el siguiente link.

* [SolucionDockerWindows](https://www.youtube.com/watch?v=_et7H0EQ8fY&t=346s) -Video tutorial solcuion Docker Windows.

Además de esto se encuentra la limitación que tiene AWS con respecto a sus créditos, ya que
si no somos cuidadosos podremos llegar perderlos todos dejando alguna máquina prendida. Por eso
al finalizar este taller se pasó a eliminar de forma inmediata las maquina con el fin de evitar futuros percances.

### EXTENDER.

Se puede llegar a crear futuras aplicaciones más compleja con ayuda de docker y aws, teniendo
en cuenta siempre sus respectivas limitaciones. Pero para el primer acercamiento por parte de
nosotros como estudiantes. Es un buen ejemplo de como funcionan Docker.

### Documentación

Para generar la documentación se debe ejecutar:

`$ mvn javadoc:javadoc`

Esta quedará en la carpeta target/site/apidocs

### AUTOR.

> Deivid Sebastián Medina Rativa.

### Licencia.

Este laboratorio esta bajo la licencia de GNU GENERAL PUBLIC LICENSE.



