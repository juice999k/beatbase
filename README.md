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
- Spring Security
- JWT
- Docker
- Swagger

## Requisitos previos

- Java 21 instalado
- Laragon corriendo con MySQL activo
- Docker Desktop instalado en la maquina

## Cómo ejecutar

1. Clonar el repositorio:
   ```
   git clone <url-del-repo>
   ```

2. Configurar la base de datos en `src/main/resources/application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/beatbase
   spring.datasource.username=root
   spring.datasource.password=
   ```

3. Ejecutar el proyecto:
   ```
   .\mvnw spring-boot:run
   ```

4. También puedes iniciarlo desde Visual Studio Code con el Extension Pack for Java: click derecho sobre `BeatbaseApplication.java` → Run Java.

5. La API estará disponible en: `http://localhost:8080`

---

## Cómo ejecutar con Docker Compose
 
También se puede levantar el proyecto completo (API + MySQL) usando Docker, sin necesidad de tener Laragon ni Java instalado localmente.
 
1. Tener Docker Desktop abierto y corriendo.
2. Desde la raíz del proyecto, ejecutar:
```
   docker-compose up --build
```
 
3. Esto levanta dos contenedores:
   - **mysql**: base de datos MySQL 8.4, expuesta en el puerto `3307` de la máquina local (mapeado al `3306` interno del contenedor).
   - **app**: la API Spring Boot, expuesta en `http://localhost:8080`.
4. El contenedor `app` espera a que `mysql` esté saludable (`healthcheck`) antes de iniciar, así se evita que la API intente conectarse antes de que la base de datos esté lista.
5. Para detener y eliminar los contenedores:
```
   docker-compose down
```
   Si además se quiere borrar la base de datos (volumen persistente):
```
   docker-compose down -v
```
 
---

## Autenticación con JWT
 
La API está protegida con JSON Web Tokens (JWT). Para usar los endpoints de artistas, biografías y proyectos es necesario autenticarse primero.
 
### 1. Registrar un usuario
 
- Método: `POST`
- URL: `http://localhost:8080/api/v1/auth/register`
- Body:
```json
    {
      "username": "martin",
      "password": "hola1234"
    }
```
- Por defecto, todo usuario registrado queda con rol `ROLE_USER`.
### 1.1 Registrar un usuario ADMIN
 
- Método: `POST`
- URL: `http://localhost:8080/api/v1/auth/register-admin`
- Body:
```json
    {
      "username": "admin",
      "password": "admin123"
    }
```
- El `username` y `password` pueden ser cualquier valor; lo que define el rol es el endpoint usado, no el contenido del body.
- Este endpoint registra el usuario directamente con rol `ROLE_ADMIN`, sin necesidad de modificar la base de datos manualmente. Pensado para crear el usuario administrador inicial del sistema.
### 2. Iniciar sesión
 
- Método: `POST`
- URL: `http://localhost:8080/api/v1/auth/login`
- Body:
```json
    {
      "username": "admin",
      "password": "admin123"
    }
```
- Respuesta: un JWT válido por 24 horas.
### 3. Usar el token en las peticiones
 
En cada request a los endpoints protegidos (`/api/artistas`, `/api/biografias`, `/api/proyectos`), agregar el header:
 
```
Authorization: Bearer <token>
```

---
## Pruebas funcionales
 
El proyecto incluye pruebas funcionales en `src/test/java/cl/duoc/beatbase/ArtistaControllerTest.java`, usando JUnit 5 y Mockito para simular el servicio sin necesidad de base de datos real.


### Ejecución de pruebas

Ejecutar solo el test principal (recomendado para clase):

```bash
.\mvnw.cmd -Dtest=ArtistaControllerTest test
```

Ejecutar todas las pruebas existentes en el proyecto:

```bash
.\mvnw.cmd test
```

## Orden de creación de datos

Para vincular correctamente las entidades, seguir este orden:

1. Crear una **biografía** → `POST /api/biografias`
2. Crear un **artista** con el `id` de la biografía → `POST /api/artistas`
3. Crear un **proyecto** con el `id` del artista → `POST /api/proyectos`

---

## Swagger / OpenAPI

Swagger UI permite visualizar y probar la API desde el navegador.

### Dependencia necesaria en `pom.xml`

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.8.9</version>
</dependency>
```

### Qué hay que permitir en Security

Para que Swagger se pueda abrir sin JWT, hay que dejar públicas estas rutas en `SecurityConfig`:

```java
.requestMatchers(
  "/swagger-ui/**",
  "/swagger-ui.html",
  "/v3/api-docs/**"
).permitAll()
```

Eso permite entrar a la documentación sin autenticarse primero.

### URL de acceso

```text
http://localhost:8080/swagger-ui/index.html
```

### Qué aporta Swagger en este proyecto

1. Lista automáticamente los endpoints disponibles.
2. Permite probar requests desde el navegador.
3. Muestra los modelos y códigos HTTP.

---


## Endpoints disponibles

El proyecto expone los siguientes endpoints. Las peticiones `POST` y `PUT` requieren enviar un JSON en el cuerpo de la solicitud.

---

### 1. Artistas (`/api/artistas`)

**Obtener todos los artistas**
- Método: `GET`
- URL: `http://localhost:8080/api/artistas`

**Buscar artista por ID**
- Método: `GET`
- URL: `http://localhost:8080/api/artistas/{id}`

**Artistas con sus proyectos (DTO)**
- Método: `GET`
- URL: `http://localhost:8080/api/artistas/proyectos`

**Crear un artista** (requiere biografía creada previamente)
- Método: `POST`
- URL: `http://localhost:8080/api/artistas`
- Body:
    ```json
    {
      "nombre": "Michael Jackson",
      "edad": 50,
      "sexo": "Masculino",
      "genero": "Pop",
      "biografia": {
        "id": 1
      }
    }
    ```

**Actualizar un artista**
- Método: `PUT`
- URL: `http://localhost:8080/api/artistas/{id}`
- Body:
    ```json
    {
      "nombre": "Michael Jackson (Editado)",
      "edad": 55,
      "sexo": "Masculino",
      "genero": "Rock",
      "biografia": {
        "id": 1
      }
    }
    ```

**Eliminar un artista**
- Método: `DELETE`
- URL: `http://localhost:8080/api/artistas/{id}`

---

### 2. Biografías (`/api/biografias`)

**Obtener todas las biografías**
- Método: `GET`
- URL: `http://localhost:8080/api/biografias`

**Buscar biografía por ID**
- Método: `GET`
- URL: `http://localhost:8080/api/biografias/{id}`

**Crear una biografía**
- Método: `POST`
- URL: `http://localhost:8080/api/biografias`
- Body:
    ```json
    {
      "descripcion": "Michael Joseph Jackson (1958-2009) fue un cantante, compositor y bailarín estadounidense, universalmente conocido como el Rey del Pop."
    }
    ```

**Actualizar biografía**
- Método: `PUT`
- URL: `http://localhost:8080/api/biografias/{id}`
- Body:
    ```json
    {
      "descripcion": "Biografía actualizada: Músico, compositor y multiinstrumentista estadounidense de gran trayectoria."
    }
    ```

**Eliminar biografía**
- Método: `DELETE`
- URL: `http://localhost:8080/api/biografias/{id}`

---

### 3. Proyectos (`/api/proyectos`)

**Obtener todos los proyectos**
- Método: `GET`
- URL: `http://localhost:8080/api/proyectos`

**Buscar proyecto por ID**
- Método: `GET`
- URL: `http://localhost:8080/api/proyectos/{id}`

**Crear proyecto** (requiere artista creado previamente)
- Método: `POST`
- URL: `http://localhost:8080/api/proyectos`
- Body:
    ```json
    {
      "titulo": "Thriller",
      "tipo": "Album de estudio",
      "anioLanzamiento": 1982,
      "discografica": "Epic Records",
      "artista": {
        "id": 1
      }
    }
    ```

**Actualizar proyecto**
- Método: `PUT`
- URL: `http://localhost:8080/api/proyectos/{id}`
- Body:
    ```json
    {
      "titulo": "Thriller (Remaster)",
      "tipo": "Album",
      "anioLanzamiento": 2021,
      "discografica": "Universal Music",
      "artista": {
        "id": 1
      }
    }
    ```

**Eliminar proyecto**
- Método: `DELETE`
- URL: `http://localhost:8080/api/proyectos/{id}`

---

### 4. Clima (`/api/v1/clima`)

**Clima actual - Santiago (default)**
- Método: `GET`
- URL: `http://localhost:8080/api/v1/clima`

**Clima actual - coordenadas personalizadas**
- Método: `GET`
- URL: `http://localhost:8080/api/v1/clima?lat=-41.47&lon=-72.94`

---

## Autores

- **Martin Sandoval** - mart.sandovalb@duocuc.cl
- **Lukas Vicker** - lu.vicker@duocuc.cl
