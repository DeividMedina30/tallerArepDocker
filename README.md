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

```EjecutarProgramaLocalHost
   localhost:4567/hello
```

![primeraparte2.png](https://i.postimg.cc/4xxmnWF3/primeraparte2.png)

### Segunda Parte: crear imagen para docker y subirla.

1. En la raíz de su proyecto cree un archivo denominado Dockerfile con el siguiente contenido:
2. Usando la herramienta de línea de comandos de Docker construya la imagen:
3. Revise que la imagen fue construida
4. A partir de la imagen creada cree tres instancias de un contenedor docker independiente de la consola (opción “-d”) y con el puerto 6000 enlazado a un puerto físico de su máquina (opción -p):
5. Asegúrese que el contenedor está corriendo
6. Acceda por el browser a http://localhost:34002/hello, o a http://localhost:34000/hello, o a http://localhost:34001/hello para verificar que están corriendo.

**En la raíz de su proyecto cree un archivo denominado Dockerfile.**

Para crear el archivo Dockerfile, nos hubicamos en la carpeta raiz y abrimos un cmd.
Luego pasamos a ejecutar el siguiente comando.

```CreandoArchivoDockerFile
   echo "Hola" > Dockerfile
```

Abrimos el archivo con un editor de texto y eliminamos su contenido para poner el siguiente.

```ContenidoDocker
    FROM openjdk:8

    WORKDIR /usrapp/bin
    
    ENV PORT 6000
    
    COPY /target/classes /usrapp/bin/classes
    COPY /target/dependency /usrapp/bin/dependency
    
    CMD ["java","-cp","./classes:./dependency/*","edu.escuelaing.arep.TallerVirtualizacion.SparkWebServer"]
```

**Usando la herramienta de línea de comandos de Docker construya la imagen:**

```CreandoArchivoDockerFile
   docker build --tag primer_docker_spark .
```

![segundaparte1.png](https://i.postimg.cc/sgfxNTYr/segundaparte1.png)

![segundaparte2.png](https://i.postimg.cc/ZRPhZSmD/segundaparte2.png)

**Revise que la imagen fue construida**

```imagenDocker
   docker images
```

![segundaparte3.png](https://i.postimg.cc/NjyqFTYX/segundaparte3.png)

Debera ver algo similar.

```imagenDocker2
    REPOSITORY              TAG       IMAGE ID       CREATED       SIZE
    primer_docker_spark     latest    c9fc19b9fc38   4 hours ago   530MB
```

**A partir de la imagen creada cree tres instancias de un contenedor docker independiente.**

```CrenadoInstancias
    docker run -d -p 34000:6000 --name firstdockercontainer dockersparkprimer
    docker run -d -p 34001:6000 --name firstdockercontainer2 dockersparkprimer
    docker run -d -p 34002:6000 --name firstdockercontainer3 dockersparkprimer
```

![segundaparte4.png](https://i.postimg.cc/gknBHW5F/segundaparte4.png)

**Asegúrese que el contenedor está corriendo**

```imagenDocker
   docker ps
```

Mirando desde Docker

![segundaparte5.png](https://i.postimg.cc/sf0TXL2n/segundaparte5.png)


Debera ver algo similar.

```dockerPs
    CONTAINER ID   IMAGE                 COMMAND                  CREATED       STATUS       PORTS                     NAMES9abcb510d025   primer_docker_spark   "java -cp ./classes:…"   4 hours ago   Up 4 hours   0.0.0.0:34002->6000/tcp   firstdockercontainer3
    3041305b7712   primer_docker_spark   "java -cp ./classes:…"   4 hours ago   Up 4 hours   0.0.0.0:34001->6000/tcp   firstdockercontainer2
    ac22d2340f2b   primer_docker_spark   "java -cp ./classes:…"   4 hours ago   Up 4 hours   0.0.0.0:34000->6000/tcp   firstdockercontainer

```

![segundaparte6.png](https://i.postimg.cc/vZwLxwwv/segundaparte6.png)

**Acceda por el browser**

```formaUno
   http://localhost:34000/hello
```

```formaDos
   http://localhost:34001/hello
```

```formaTres
   http://localhost:34002/hello
```

Debera ver algo similar.

![segundaparte7.png](https://i.postimg.cc/KzjnhZtm/segundaparte7.png)

### Tercera para subir la imagen a Docker Hub.

1. Cree una cuenta en Dockerhub y verifique su correo.
2. Acceda al menu de repositorios y cree un repositorio.
3. En su motor de docker local cree una referencia a su imagen con el nombre del repositorio a donde desea subirla:
4. Verifique que la nueva referencia de imagen existe.
5. Autentíquese en su cuenta de dockerhub (ingrese su usuario y clave si es requerida):
6. Empuje la imagen al repositorio en DockerHub.

**Cree una cuenta en Dockerhub y verifique su correo.**

* [DockerHub](https://hub.docker.com/)

![terceraparte1.png](https://i.postimg.cc/rprvqkc2/terceraparte1.png)

**Acceda al menu de repositorios y cree un repositorio.**

![terceraparte2.png](https://i.postimg.cc/G3Q6Lz0t/terceraparte2.png)

![terceraparte3.png](https://i.postimg.cc/wjSGfGNf/terceraparte3.png)

**En su motor de docker local cree una referencia a su imagen con el nombre del repositorio**

```formaTres
   docker tag primer_docker_spark:latest deividmedina30/primer_spark_web_deivid:latest
```

![terceraparte4.png](https://i.postimg.cc/43VFrQbJ/terceraparte4.png)

**Verifique que la nueva referencia de imagen existe.**

```formaTres
   docker images
```

![terceraparte9.png](https://i.postimg.cc/HnRwYfdF/terceraparte9.png)

**Autentíquese en su cuenta de dockerhub (ingrese su usuario y clave si es requerida):**

```formaTres
   docker login
```

![terceraparte5.png](https://i.postimg.cc/d3tHZPM1/terceraparte5.png)

**Empuje la imagen al repositorio en DockerHub.**

```formaTres
   docker push deividmedina30/primer_spark_web_deivid:latest
```

![terceraparte6.png](https://i.postimg.cc/2ycH4cmx/terceraparte6.png)

![terceraparte7.png](https://i.postimg.cc/FK5TgJtw/terceraparte7.png)

![terceraparte8.png](https://i.postimg.cc/63BzFJp0/terceraparte8.png)

![terceraparte10.png](https://i.postimg.cc/XJvdwVvT/terceraparte10.png)

### Cuarta parte: AWS.

1. Acceda a la maquina virtual
2. Instalar java.
3. Instalar Docker.
4. Iniciando el servicio Docker.
5. Configure su usuario en el grupo de docker para no tener que ingresar “sudo” cada vez que invoca un comando
6. Desconectes de la máquina virtual e ingrese nuevamente para que la configuración de grupos de usuarios tenga efecto.
7. A partir de la imagen creada en Dockerhub cree una instancia de un contenedor docker independiente de la consola (opción “-d”) y con el puerto 6000 enlazado a un puerto físico de su máquina (opción -p):
8. Abra los puertos de entrada del security group de la máxima virtual para acceder al servicio.
9. Verifique que pueda acceder  en una url similar a esta (la url específica depende de los valores de su maquina virtual EC2)

**Acceda a la máquina virtual.**

![cuartaparte1.png](https://i.postimg.cc/sXqdp4z7/cuartaparte1.png)

![cuartaparte2.png](https://i.postimg.cc/ZnNkjvYg/cuartaparte2.png)

![cuartaparte3.png](https://i.postimg.cc/sgV8GNWb/cuartaparte3.png)

![cuartaparte4.png](https://i.postimg.cc/htpYL1bv/cuartaparte4.png)

![cuartaparte5.png](https://i.postimg.cc/25tXJjtf/cuartaparte5.png)

![cuartaparte6.png](https://i.postimg.cc/Y06nh6Cp/cuartaparte6.png)

![cuartaparte7.png](https://i.postimg.cc/wTz4nGw3/cuartaparte7.png)

![cuartaparte8.png](https://i.postimg.cc/HLr6QDkv/cuartaparte8.png)

![cuartaparte9.png](https://i.postimg.cc/Dy75xbzX/cuartaparte9.png)

**Instalar java.**

```instalanadoJava
   sudo yum install java-1.8.0
```

```instalanadoJava2
   sudo yum install java-1.8.0-openjdk-devel
```

![cuartaparte10.png](https://i.postimg.cc/7YX9dYCQ/cuartaparte10.png)

![cuartaparte11.png](https://i.postimg.cc/MH57CdGj/cuartaparte11.png)

![cuartaparte12.png](https://i.postimg.cc/v8ttxHvH/cuartaparte12.png)

**Instalar Docker.**

```instalanadoDocker
   sudo yum update -y
```

```instalanadoDocker2
   sudo yum install docker
```

![cuartaparte13.png](https://i.postimg.cc/fLhYk6t9/cuartaparte13.png)

**Iniciando el servicio Docker.**

```iniciandoDocker
   sudo service docker start
```

![cuartaparte14.png](https://i.postimg.cc/xCyMmtMF/cuartaparte14.png)

**Configure su usuario en el grupo de docker para no tener que ingresar “sudo”.**

```configurandoUsuarioDocker
   sudo usermod -a -G docker ec2-user
```

![cuartaparte15.png](https://i.postimg.cc/sD0S5BGy/cuartaparte15.png)

**Desconectes de la máquina virtual e ingrese nuevamente para que la configuración de grupos.**

```salir
   exit
```

![cuartaparte16.png](https://i.postimg.cc/YCGFRpQF/cuartaparte16.png)

![cuartaparte17.png](https://i.postimg.cc/HLbyGBZs/cuartaparte17.png)

![cuartaparte18.png](https://i.postimg.cc/j2Sw7KsL/cuartaparte18.png)

**A partir de la imagen creada en Dockerhub cree una instancia de un contenedor docker independiente.**

```creandoInstancia
    docker run -d -p 35000:6000 --name primer_docker_aws deividmedina30/primer_spark_web_deivid:latest
```

```dockerimages
    docker images
```

```dockerps
    docker ps
```

![cuartaparte19.png](https://i.postimg.cc/MKXfZWPL/cuartaparte19.png)

![cuartaparte20.png](https://i.postimg.cc/HsKcVfFk/cuartaparte20.png)

**Abra los puertos de entrada del security group de la máxima virtual para acceder al servicio.**

![cuartaparte21.png](https://i.postimg.cc/kgbV8TdF/cuartaparte21.png)

![cuartaparte22.png](https://i.postimg.cc/5tWXm84c/cuartaparte22.png)

![cuartaparte23.png](https://i.postimg.cc/TPjpNh1K/cuartaparte23.png)

**Verifique que pueda acceder en una url.**

```salir
   http://ec2-184-73-150-30.compute-1.amazonaws.com:35000/hello
```

![cuartaparte24.png](https://i.postimg.cc/L4Ggscc6/cuartaparte24.png)

### PASOS PARA CLONAR.

-  Nos dirigimos a la parte superior de nuestra ubicación, donde daremos clic y escribimos la palabra cmd, luego damos enter, con el fin de desplegar
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

* [SolucionDockerWindows](https://www.youtube.com/watch?v=_et7H0EQ8fY&t=346s) -Video tutorial solución Docker Windows.

Además de esto se encuentra la limitación que tiene AWS con respecto a sus créditos, ya que
si no somos cuidadosos podremos llegar perderlos todos dejando alguna máquina prendida. Por eso
al finalizar este taller se pasó a eliminar de forma inmediata las maquina con el fin de evitar futuros percances.

### EXTENDER.

Se puede llegar a crear futuras aplicaciones más complejas con ayuda de docker y aws, teniendo
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



