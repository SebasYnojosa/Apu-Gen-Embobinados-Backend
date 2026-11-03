# APU-Gen Embobinados - Backend ⚙️

API RESTful robusta desarrollada para la gestión, cálculo y persistencia de Análisis de Precios Unitarios (APUs), orientada a talleres de rebobinado de motores y reparación de componentes. 

Este backend actúa como el motor de cálculo financiero y gestor de base de datos relacional para el sistema APU-Gen, proporcionando endpoints seguros y estructurados para el consumo desde el frontend.

## 🚀 Stack Tecnológico

El proyecto está construido sobre las versiones más recientes y estables del ecosistema Java:

* **Lenguaje:** [Java 21](https://jdk.java.net/21/) (Uso de `records` y características modernas)
* **Framework Core:** [Spring Boot 3.x](https://spring.io/projects/spring-boot)
* **Persistencia (ORM):** Spring Data JPA / [Hibernate 6](https://hibernate.org/)
* **Base de Datos:** [PostgreSQL 16](https://www.postgresql.org/)
* **Manejo de Errores:** `@ControllerAdvice` global para respuestas JSON estandarizadas
* **Cálculos Matemáticos:** API `BigDecimal` con política de redondeo estricta (`RoundingMode.HALF_UP`)
* **Búsquedas Avanzadas:** Consultas JPQL dinámicas y funciones SQL nativas personalizadas (`FunctionContributor`)

## 📋 Requisitos Previos

Para levantar este proyecto en tu entorno local necesitas:

* **Java Development Kit (JDK) 21** instalado en tu sistema.
* **Docker y Docker Compose** (para levantar la base de datos PostgreSQL y pgAdmin de forma aislada).
* **Maven** (Opcional, el proyecto incluye Maven Wrapper `mvnw`).

## 🛠️ Instalación y Ejecución Local

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/TU_USUARIO/embobinados-backend.git
   cd embobinados-backend
   ```

2. **Levantar la Base de Datos (Docker Compose):**
   Ejecuta el siguiente comando para iniciar el contenedor de PostgreSQL 16 y pgAdmin 4:
   ```bash
   docker-compose up -d
   ```
   *Nota: La base de datos se expone en el puerto `5432`.*

3. **Configurar la extensión de PostgreSQL:**
   Para permitir las búsquedas ignorando acentos, debes acceder a la base de datos `embobinados_apu` (vía pgAdmin o CLI) y ejecutar:
   ```sql
   CREATE EXTENSION IF NOT EXISTS unaccent;
   ```

4. **Ejecutar la aplicación Spring Boot:**
   En la raíz del proyecto, utiliza el wrapper de Maven para iniciar el servidor:
   ```bash
   ./mvnw spring-boot:run
   ```
   El backend estará disponible en `http://localhost:8080`.

## 📂 Arquitectura del Proyecto

El código fuente sigue un patrón de diseño en capas limpio y mantenible:

```text
src/main/java/com/embobinados/apugen_backend/
├── config/        # Configuración global (CORS, Hibernate FunctionContributor)
├── controllers/   # Endpoints REST (ApuController, CatalogController)
├── dto/           # Objetos de Transferencia de Datos para evitar recursividad en JSON
├── entity/        # Modelos de Base de Datos (@Entity, @ManyToOne, etc.)
├── exceptions/    # Manejo global de errores (GlobalExceptionHandler, ErrorResponse Record)
├── repository/    # Interfaces de Spring Data JPA (Consultas dinámicas)
└── service/       # Lógica de negocio, cálculos financieros y transacciones
```

## 🔌 Características Destacadas de la API

* **CORS Global:** Pre-configurado para aceptar peticiones del frontend en `http://localhost:5173`.
* **Manejo de Errores Limpio:** Cualquier excepción (ej. ID no encontrado, listas vacías) es capturada e inyectada en un JSON estandarizado, evitando la exposición de StackTraces al cliente.
* **Buscador Universal de APUs:** Endpoint avanzado capaz de filtrar por 6 parámetros opcionales, ignorando mayúsculas, minúsculas y acentos, soportando coincidencias exactas o parciales (`LIKE`).
* **Protección de Datos:** Operaciones de borrado en cascada (Cascade Delete) controladas desde la capa de servicio para prevenir la orfandad de registros y errores de llaves foráneas.
* **Modo Update de Hibernate:** La base de datos está configurada en modo `update` (`spring.jpa.hibernate.ddl-auto=update`), lo que permite que el esquema evolucione sin borrar la data existente tras cada reinicio.

## 📡 Endpoints Principales (Resumen)

* `GET /api/apus`: Lista todas las cabeceras de los APUs.
* `GET /api/apus/buscar`: Búsqueda avanzada de APUs con múltiples filtros.
* `GET /api/apus/{id}`: Obtiene el APU completo y sus detalles empaquetados en un DTO.
* `POST /api/apus`: Calcula y persiste un nuevo APU con todos sus cálculos matemáticos.
* `DELETE /api/apus/{id}`: Borra un APU de forma segura eliminando primero sus dependencias.
* `GET, POST, PUT, DELETE /api/catalogos/*`: CRUD completo para las entidades maestras (Empresas, Materiales, Equipos, Mano de Obra).