# BeatBase API

API REST para la gestión de artistas musicales, sus biografías y proyectos discográficos.
Incluye integración con Open-Meteo para consulta de clima en tiempo real.

## Tecnologías

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- Maven
- MySQL (Laragon)
- WebClient (llamada a API externa)

## Requisitos previos

- Java 21 instalado
- Laragon corriendo con MySQL activo

## Cómo ejecutar

1. Clonar el repositorio:
   git clone <url-del-repo>

2. Configurar la base de datos en `src/main/resources/application.properties`:
   spring.datasource.url=jdbc:mysql://localhost:3306/beatbase
   spring.datasource.username=root
   spring.datasource.password=

3. Ejecutar el proyecto:
   ./mvnw spring-boot:run

4. Tambien puedes iniciar si tienes el Extension Pack for Java y le das click derecho y run java al archivo BeatbaseApplication.java

5. La API estará disponible en: http://localhost:8080

## Endpoints disponibles


El proyecto expone los siguientes endpoints para interactuar con la base de datos de BeatBase. Las peticiones `POST` y `PUT` requieren enviar un JSON en el cuerpo (`Body`) de la solicitud.

---

### 1. Artistas (`/api/artistas`)

* **Obtener todos los artistas**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/api/artistas`
* **Buscar artista por ID**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/api/artistas/{id}`
* **Crear un artista**
    * **Método:** `POST`
    * **URL:** `http://localhost:8080/api/artistas`
    * **JSON Body:**
        ```json
        {
          "nombre": "Michael Jackson",
          "edad": 67,
          "sexo": "Masculino",
          "genero": "Pop"
        }
        ```
* **Actualizar un artista**
    * **Método:** `PUT`
    * **URL:** `http://localhost:8080/api/artistas/{id}` *(Nota: Si tu controlador no usa ID en la URL para Artista, remover el /{id} al probar)*
    * **JSON Body:**
        ```json
        {
          "id": 1,
          "nombre": "Michael Jackson (Editado)",
          "edad": 50,
          "sexo": "Masculino",
          "genero": "Rock"
        }
        ```
* **Eliminar un artista**
    * **Método:** `DELETE`
    * **URL:** `http://localhost:8080/api/artistas/{id}`

---

### 2. Biografías (`/api/biografias`)

* **Obtener todas las biografías**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/api/biografias`
* **Buscar biografía por ID**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/api/biografias/{id}`
* **Crear una biografía**
    * **Método:** `POST`
    * **URL:** `http://localhost:8080/api/biografias`
    * **JSON Body:** *(Corregido sin la línea del id huérfano)*
        ```json
        {
          "descripcion": "Michael Joseph Jackson (1958-2009) fue un cantante, compositor y bailarín estadounidense, universalmente conocido como el «Rey del Pop»..."
        }
        ```
* **Actualizar biografía**
    * **Método:** `PUT`
    * **URL:** `http://localhost:8080/api/biografias/{id}`
    * **JSON Body:**
        ```json
        {
          "id": 1,
          "descripcion": "Biografía actualizada: Músico, compositor y multiinstrumentista estadounidense de gran trayectoria."
        }
        ```
* **Eliminar biografía**
    * **Método:** `DELETE`
    * **URL:** `http://localhost:8080/api/biografias/{id}`

---

### 3. Proyectos (`/api/proyectos`)

* **Obtener todos los proyectos**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/api/proyectos`
* **Buscar proyecto por ID**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/api/proyectos/{id}`
* **Crear proyecto**
    * **Método:** `POST`
    * **URL:** `http://localhost:8080/api/proyectos`
    * **JSON Body:**
        ```json
        {
          "titulo": "Thriller",
          "tipo": "Álbum de estudio",
          "anioLanzamiento": 1982,
          "discografica": "Epic Records"
        }
        ```
* **Actualizar proyecto**
    * **Método:** `PUT`
    * **URL:** `http://localhost:8080/api/proyectos/{id}`
    * **JSON Body:**
        ```json
        {
          "titulo": "Thriller (Remaster)",
          "tipo": "Álbum",
          "anioLanzamiento": 2021,
          "discografica": "Universal Music"
        }
        ```
* **Eliminar proyecto**
    * **Método:** `DELETE`
    * **URL:** `http://localhost:8080/api/proyectos/{id}`

## Autores
Martin Sandoval
Lukas Vicker
