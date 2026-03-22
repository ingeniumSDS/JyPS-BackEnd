# Contexto del Proyecto: JyPS (Sistema de Justificantes y Pases de Salida)

## 1. Descripción General
El proyecto **JyPS** es una solución tecnológica integral desarrollada para la **Universidad Tecnológica del Estado de Morelos (UTEZ)**. Su objetivo principal es la digitalización y automatización de los procesos relacionados con las incidencias del personal, específicamente el control de salidas temporales durante la jornada laboral y la justificación de inasistencias o retardos.

Este sistema sustituye los procesos manuales por un flujo de trabajo optimizado que garantiza la transparencia, seguridad y el control administrativo eficiente.

---

## 2. Características de la Aplicación
La aplicación se distingue por las siguientes funcionalidades clave:

* **Control de Acceso Basado en Roles (RBAC):** El sistema adapta sus funciones según el perfil del usuario (Administrador, Empleado, Jefe de Departamento, Guardia y Auditor).
* **Validación mediante Código QR:** Al aprobarse un pase de salida, se genera un código QR único que el personal de vigilancia escanea para registrar el egreso físico de forma rápida.
* **Gestión Documental de Evidencias:** Permite a los empleados adjuntar archivos (PDF, JPG, PNG) para sustentar justificantes médicos o legales.
* **Seguridad y Autenticación:** Acceso restringido a cuentas con dominio institucional `@utez.edu.mx` y cifrado de datos sensibles.
* **Sincronización en Tiempo Real:** Comunicación fluida entre la plataforma web y la aplicación móvil, asegurando que las aprobaciones se reflejen instantáneamente.
* **Interfaz Multiplataforma:** Diseñada para ser utilizada tanto en entornos de oficina (web) como en puntos de control (móvil).

---

## 3. Procesos Administrativos Detallados
La aplicación desempeña y automatiza los siguientes procesos críticos para la organización:

### A. Gestión de Estructura y Usuarios
* **Administración de Personal:** Registro, actualización e inactivación de cuentas de usuario. El sistema mantiene la integridad de los datos evitando la eliminación física de registros.
* **Configuración Departamental:** Gestión de las áreas de la universidad y asignación de empleados a sus respectivos jefes inmediatos para establecer la jerarquía de aprobación.

### B. Flujo de Incidencias (Pases y Justificantes)
* **Tramitación de Pases de Salida:** Proceso mediante el cual un empleado solicita permiso para retirarse de las instalaciones, especificando motivos y horarios.
* **Gestión de Justificantes:** Los empleados cuentan con un plazo de **3 días hábiles** para reportar y justificar una falta o retardo, subiendo las evidencias necesarias.
* **Ciclo de Aprobación:** Los Jefes de Departamento actúan como validadores, revisando las solicitudes y decidiendo su aprobación o rechazo (con retroalimentación obligatoria).

### C. Control Operativo y Vigilancia
* **Registro de Movimientos (Checkout):** El personal de guardia supervisa las salidas mediante el escaneo de códigos QR, asegurando que solo el personal autorizado abandone el recinto.
* **Protocolos de Contingencia:** Capacidad de validar salidas de forma manual mediante códigos alfanuméricos en caso de fallos en la lectura del QR.

### D. Auditoría y Cumplimiento
* **Monitoreo Histórico:** Los auditores pueden generar reportes detallados sobre el comportamiento de asistencia y cumplimiento de horarios.
* **Seguimiento de Acciones:** Registro de bitácoras (logs) para verificar quién autorizó cada incidencia, facilitando revisiones de nómina y cumplimiento de políticas internas.