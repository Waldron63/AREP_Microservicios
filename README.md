# Taller Microservicios
Santiago Gualdrón 

Laura Daniela Rodríguez

Juan David Martínez

## Introducción
En este taller experimental se debe diseñar e implementar una aplicación tipo Twitter que permita a los usuarios realizar publicaciones de hasta 140 caracteres, gestionadas dentro de un único stream de posts. Para ello, se construirá un monolito en Spring Boot con tres entidades principales: Usuario, Post y Stream, exponiendo un API RESTful que será consumido por una aplicación web desarrollada en JavaScript y desplegada públicamente en Amazon S3. Posteriormente, se añadirá seguridad con JWT mediante Amazon Cognito u otra tecnología similar, y se realizará la migración del monolito a una arquitectura de microservicios desplegados en AWS Lambda. Finalmente, se deberá entregar el código en GitHub, un reporte de arquitectura, un reporte de pruebas y un video demostrando el funcionamiento completo del sistema.

## Prerequisitos

* java 21
* Maven 3.9+
* Git

## Instalación
Clonar el repositorio
```
git clone https://github.com/Waldron63/AREP_Microservicios.git
cd AREP_Microservicios
```
Ejecutar el proyecto
```
mvn spring-boot:run
```
## Vista de la aplicación

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/4cddc31b-5447-4bdd-a232-df295a7ef975" />

Para evidenciar el funcionamiento local tenemos el siguiente video: [Video de funcionamiento local](atepTareaTwitterLocal.mp4)

## Arquitectura

### Monolito

<img width="532" height="276" alt="image" src="https://github.com/user-attachments/assets/fbec79ad-6471-4fe1-a4bf-a3a2652f8740" />

### Microservicio

<img width="623" height="357" alt="image" src="https://github.com/user-attachments/assets/41927f45-fa80-4b23-8ee4-f4c2496caee8" />

## Componentes utilizados de AWS

### Lambda User

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/de66cd29-b9ee-4657-b6ac-39e909fb5136" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/d5944298-1182-4bee-a097-7c259979285b" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/68cd2ef9-a69c-4c60-952e-3fdd44e702f8" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/aa652516-6029-443b-a08e-8bb7bb5fcd53" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/60d41a61-c523-44d5-a497-bc358587e46d" />

### Lambda Stream

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/8ffc1fbd-0af2-4983-9b48-f90a47283e2e" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/c7ac552e-baaf-4049-91ce-0ec760b24e4c" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/722599ad-86e5-4f21-9807-074247cdb075" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/ed6fc3a7-7e54-4982-a21c-97129437e75c" />

### Lambda Post

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/2999c402-a7f9-46c0-8e1c-1d6099ad4a8d" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/c7642d52-bd22-465f-84fb-0e0e345a97b4" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/a9bd1e1f-f4a1-4121-a468-99bfd0acfd5f" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/27db1275-7f75-4f52-bac3-0047e0ae5fd5" />

### API Gateway

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/94722afa-0ba5-489e-a0f3-16d55590741c" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/62bea2b7-ad1e-455e-8d6e-27a3043b4a0d" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/1a876051-6056-43a4-ad6d-5ba243485f18" />

URL -> https://jtnkb1qozh.execute-api.us-east-1.amazonaws.com/api

### Cógnito

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/b57cc103-3033-41a9-b708-f117bbbca24b" />

### S3

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/4f05cff8-814c-44a7-8069-d4b4914d869f" />

<img width="1600" height="900" alt="image" src="https://github.com/user-attachments/assets/a1651f26-62bd-466c-873c-320e7eb1fcd4" />

URL -> https://twitter-microservices.s3.us-east-1.amazonaws.com/index.html

NOTA: las URL suministradas no funcionan ya que al apagar el laboratorio estas llegan a cambiar.

### Despliegue

Para revisar la configuración del depliegue realizado revisar el siguiente video: [Video de configuración despliegue](arepTareaTwitterConfiguracion.mp4)

## Pruebas

### Unitarias

Se realizaron en total 17 pruebas unitarias las cuales se realizaron sobre los controladores

<img width="960" height="457" alt="image" src="https://github.com/user-attachments/assets/d8dda1a2-fcb1-4a38-b2a8-45a58895aa4d" />

Las pruebas se realizaron sobre las clases

* PostControllerTest
* StreamControllerTest
* UserControllerTest
* UserServiceTest

### Funcional

Para verificar el funcionamiento cuando ya hemos desplegado revisar el siguiente video: [Funcionamiento en despliegue](arepTareaTwitterRemoto.mp4)






 
