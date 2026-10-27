# Sistema de Gestión de APUs - Embobinados (Backend)

API REST construida con **Spring Boot** para la generación, cálculo y almacenamiento estático de Análisis de Precios Unitarios (APUs) orientados a talleres de reparación y rebobinado de motores eléctricos y componentes.

El sistema utiliza una arquitectura basada en **Catálogos Maestros** y **Snapshots** para garantizar que los cálculos históricos de los presupuestos mantengan su integridad financiera a lo largo del tiempo, independientemente de la fluctuación de los precios base.

## 🛠️ Stack Tecnológico

* **Framework:** Spring Boot 3.x (Java 21)
* **Acceso a Datos:** Spring Data JPA (Hibernate)
* **Base de Datos:** PostgreSQL 16
* **Infraestructura:** Docker & Docker Compose
* **Gestor de Base de Datos:** pgAdmin 4

---

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado en tu sistema (Windows/Linux/macOS):

1. [Java JDK 21](https://adoptium.net/) o superior.
2. [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Corriendo en segundo plano).
3. Un IDE compatible con Java (IntelliJ IDEA, VS Code o Eclipse).

---

## 🚀 Guía de Instalación y Ejecución

### 1. Levantar la Infraestructura (Base de Datos y pgAdmin)

El proyecto incluye un archivo `docker-compose.yml` que configura automáticamente PostgreSQL y la herramienta visual pgAdmin en una red interna de Docker.

Abre una terminal en la raíz del proyecto y ejecuta:

```bash
docker compose up -d
```
### 2. Credenciales

- **PostgreSQL (Puerto: 5432):**
  - **Base de datos:** `embobinados_apu`
  - **Usuario:** `admin`
  - **Contraseña:** `admin`


- **pgAdmin Web (Acceso: http://localhost:5050):**
  - **Email:** `admin@embobinados.com`
  - **Contraseña:** `admin`