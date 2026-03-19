# 🏢 JyPS - Sistema de Gestión de Usuarios e Incidencias

## 📖 Descripción General
JyPS es una API RESTful diseñada para gestionar el personal de la institución/empresa, su asignación a departamentos y el control de sus incidencias laborales (pases de salida y justificantes).

El sistema está construido bajo los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)** y **Diseño Guiado por el Dominio (DDD)**, garantizando un alto nivel de desacoplamiento, mantenibilidad y escalabilidad.

---

## 🏗️ Arquitectura del Sistema (Hexagonal)

El proyecto se divide estrictamente en tres capas principales:

1. **Capa de Dominio (`domain`)**: El corazón del sistema. Contiene las reglas de negocio, Modelos puros (Entities y Value Objects) y los Puertos de Salida (Interfaces). No tiene dependencias de ningún framework externo.
2. **Capa de Aplicación (`application`)**: Orquesta los casos de uso. Contiene los Puertos de Entrada (`UseCases`), los `Commands` (DTOs inmutables de entrada) y los `Services` que implementan la lógica de negocio.
3. **Capa de Infraestructura (`infrastructure`)**: Se comunica con el mundo exterior. Contiene los adaptadores REST (`Controllers`), la persistencia de base de datos (`Entities` de Spring Data JPA, `RepositoryAdapters`) y configuraciones globales (CORS, Seguridad).

---

## 🧩 Módulos Principales (Bounded Contexts)

### 1. 👥 Módulo de Usuarios (`users`)
Gestiona el ciclo de vida de los empleados y su acceso al sistema.
* **Modelo Principal:** `Usuario` (Aggregate Root).
* **Objetos de Valor:** `Cuenta` (Maneja tokens, bloqueos y expiración).
* **Relaciones:** Todo usuario pertenece a un Departamento y tiene una lista de `Roles` (ej. EMPLEADO, ADMIN).

### 2. 🏢 Módulo de Departamentos (`departamentos`)
Gestiona las áreas organizacionales de la empresa.
* **Modelo Principal:** `Departamento`.
* **Características:** Tienen un estado de actividad y actúan como agrupación obligatoria para los usuarios.

### 3. ⏱️ Módulo de Incidencias (`incidencias`)
Gestiona los permisos y ausencias del personal.
* **Modelos Principales:** * `PaseDeSalida`: Permisos por horas específicas durante la jornada.
    * `Justificante`: Ausencias por días completos (con soporte para archivos adjuntos).
* **Reglas de negocio:** Involucran a un empleado (solicitante) y a un jefe (aprobador), y transicionan entre distintos estados (`PENDIENTE`, `APROBADO`, `RECHAZADO`).

---

## 🔄 Flujo de Datos Estándar (Ejemplo de Petición)

1. **Cliente / Front-End** envía un JSON vía HTTP.
2. **Controller (Infraestructura)** recibe el JSON, lo mapea a un `Command` (Record) y llama a la interfaz del `UseCase`.
3. **Service (Aplicación)** ejecuta las validaciones de negocio e interactúa con el Modelo de `Dominio`.
4. **Adapter (Infraestructura)** recibe la orden del Servicio, traduce el Modelo de Dominio a una `Entity` de JPA y lo guarda en la base de datos Oracle.

---

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java
* **Framework:** Spring Boot
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** Oracle DB
* **Herramientas Adicionales:** Lombok, ngrok (para pruebas locales de integración).