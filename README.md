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



