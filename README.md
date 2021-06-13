# Employee Manager

Employee Manager es un servicio web que permite crear, actualizar, eliminar y listar empleados. Para su implementación, se utilizaron las siguientes herramientas:

* JDK 11.0.11 (Java 11)
* Apache Maven 3.6.3
* Spring Boot 2.5.1
* Mockito 3.9.0
* Eclipse IDE 2021-03 (4.19.0)
* Postman v8.6.1

Para ejecutar la aplicación localmente, se recomienda al menos descargar la misma versión de JDK, así como una versión igual o superior de Maven. También se recomienda descargar Postman para poder probar la API que expone el servicio.

## 1 Contenido

* [2 Probar la aplicación](#2-probar-la-aplicación)
* [2.1 Usar la aplicación de Heroku](#21-usar-la-aplicación-de-heroku)
* [2.2 Usar la aplicación desde la máquina local](#22-usar-la-aplicación-desde-la-máquina-local)
* [2.3 Configurar la colección de Postman](#23-configurar-la-colección-de-postman)
* [3 Diseño de la aplicación](#3-diseño-de-la-aplicación)

## 2 Probar la aplicación

Para revisar el funcionamiento de la aplicación, se tienen dos opciones:

1. Usar la aplicación que previamente ha sido desplegada en Heroku. En esta opción basta con configurar Postman para que haga las solicitudes HTTP a la URL de la aplicación.
2. Descargar el código fuente del proyecto, abrirlo en el IDE de preferencia y arrancar la aplicación mediante el servidor que provee el entorno de desarrollo (ej. Eclipse).

Independientemente del camino que se escoja, se debe descargar la colección de Postman que está en el código fuente del proyecto, con nombre *employee_manager.postman_collection.json*, para importarla en Postman y utilizarla a conveniencia. A continuación, se describe cada uno de los pasos.

### 2.1 Usar la aplicación de Heroku

Con el propósito de facilitar las pruebas, se desplego la aplicación desarrollada en Heroku. En este sentido, para consultar la aplicación desde Postman basta con utilizar la siguiente URL: [https://shrouded-stream-16016.herokuapp.com/employee](https://shrouded-stream-16016.herokuapp.com/employee)

### 2.2 Usar la aplicación desde la máquina local

Otra forma de probar la aplicación es descargando su código fuente e importándolo en algún IDE, para luego arrancarla con servidores embebidos como Tomcat. En ese sentido los pasos serían los siguientes:

1. Descargar el proyecto mediante el siguiente enlace [https://github.com/contrerasmiguel/employee-manager/archive/refs/heads/master.zip](https://github.com/contrerasmiguel/employee-manager/archive/refs/heads/master.zip)

2. Descomprimirlo e importarlo con el IDE de preferencia

3. Ejecutar el proyecto como una aplicación de Spring Boot

4. Tomar nota del host en que se ejecuta la aplicación, para utilizarlo en la colección de Postman con la que se realizarán las pruebas. Por lo general, esta dirección es: [http://localhost:8080/employee](http://localhost:8080/employee)

### 2.3 Configurar la colección de Postman

En la raíz del proyecto se puede encontrar un archivo llamado *employee_manager.postman_collection.json*, el cual debe ser importado a Postman para realizar las pruebas. Una vez importado, se tendrán disponibles algunas variables dentro de la colección para facilitar su uso. Estas variables son:

* **api_url:** es la URL a la cual se enviarán las solicitudes disponibles en la colección. Por defecto tiene como valor [http://localhost:8080/employee](http://localhost:8080/employee) y se puede sustituir con la URL de la aplicación en Heroku u otra ubicación.
* **update_employee_id:** en la API de la aplicación, existe un endpoint que permite actualizar los empleados creados, y este endpoint recibe, entre varios parámetros, el ID del empleado a actualizar. Es por ello que existe esta variable, a la cual se le debe asignar el ID del empleado a actualizar u, opcionalmente, modificar la consulta de Postman para incrustar el ID del empleado en la URL. Por ejemplo, *{{api_url}}/update/**5***, en donde ***5*** es el ID del empleado a actualizar.
* **delete_employee_id:** similar a *update_employee_id*, permite especificar el ID del empleado a eliminar, en el endpoint para eliminar empleados. Igualmente, se puede sustituir la URL de tal consulta con algo como *{{api_url}}/delete/**6***, en donde ***6*** es el ID del empleado a eliminar.
* **name_filter:** representa el filtro de nombre del servicio que devuelve el listado de empleados. Se puede dejar vacío para inhabilitar el filtro.
* **position_filter:** representa el filtro de cargo del servicio que devuelve el listado de empleados. Se puede dejar vacío para inhabilitar el filtro.

Por otra parte, las consultas disponibles en Postman son las siguientes:

* **POST /employee/create (Create a new employee):** permite crear un empleado. Para utilizarla, es necesario especificar el cuerpo de la solicitud en formato JSON, para lo cual la colección ya trae la plantilla que se puede rellenar.
* **PUT /employee/update (Update an employee):** permite actualizar un empleado. Para utilizarla, es necesario especificar el cuerpo de la solicitud en formato JSON, para lo cual la colección ya trae la plantilla que se puede rellenar. También se le debe dar valor a la variable de colección *upadte_employee_id*.
* **DELETE /employee/delete (Delete an employee):** permite eliminar un empleado. Para utilizarla, se le debe dar valor a la variable de colección *delete_employee_id*.
* **GET /employee/filter (Filter employees):** permite consultar el listado de empleados. Es posible especificar filtros de nombre y cargo de empleado mediante las variables de colección *name_filter* y *position_filter*.
* **GET /employee/positions (Employees by position):** permite obtener el listado de empleados agrupados por cargos, en donde los elementos de cada cargo se encuentran ordenador por los sueldos, de los más altos hasta los más bajos.

## 3 Diseño de la aplicación

La aplicación se encuentra divida en tres capas o paquetes; estas son:

* **Paquete entities:** contiene las entidades y los repositorios de JPA.
* **Paquete services:** contiene los servicios de Spring y la mayor parte de la lógica de negocio, así como algunos DTOs para la comunicación con la capa superior de controladores. Esta capa consume al paquete *entities*.
* **Paquete controllers:** contiene el controlador de la aplicación. Esta capa consume al paquete *services*.

Por otro lado, se creó la clase *employeemanager.services.EmployeeServiceImplTest* con el propósito de dejar algunas pruebas unitarias del servicio principal de la aplicación, *EmployeeService*.

### 2.1 Diagrama de clases de la aplicación

Inicialmente se planteó el siguiente diagrama como esqueleto de la estructura de la aplicación. No obstante, durante la implementación se eliminó la clase FiltersDto, asó como se restructuraron los DTO en general, para lograr un modelo más ajustado a las salidas JSON requeridas originalmente. Aún así, el diagrama muestra las tres principales capas de la aplicación, además de la conexión entre ellas.

![Diagrama de clases](https://i.imgur.com/Y1apNqy.jpg)
