# Reservas de espacios API

API REST para gestionar reservas de espacios, construida con Java, Spring Boot, Spring Data JPA y PostgreSQL.

## Requisitos

- JDK compatible con la versión de Java declarada en `pom.xml`.
- PostgreSQL disponible localmente en el puerto `5432` (o ajustar `DB_URL`).
- Maven Wrapper incluido en el proyecto.

## Preparar PostgreSQL

Crea la base de datos una sola vez con un usuario autorizado:

```sql
CREATE DATABASE reservas_espacios_db;
```

La aplicación crea o actualiza la tabla `reservas` al iniciar usando Hibernate. La entidad inicial guarda el identificador, fecha de inicio, fecha de fin y estado (`PENDIENTE`, `CONFIRMADA` o `CANCELADA`).

## Configurar credenciales y ejecutar

Define la contraseña en el entorno local; no la guardes en el repositorio.

PowerShell:

```powershell
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "tu contraseña local"
.\mvnw.cmd spring-boot:run
```

La URL predeterminada es `jdbc:postgresql://localhost:5432/reservas_espacios_db`. Se puede sobrescribir mediante `DB_URL`; el usuario y la contraseña se configuran con `DB_USERNAME` y `DB_PASSWORD`.

Al arrancar, `PersistenciaCheck` ejecuta `reservaRepository.count()` y registra el total. Si la aplicación no inicia, verifica que PostgreSQL esté ejecutándose, que la base exista y que las variables de conexión sean correctas.

## Endpoint existente

```http
GET /api/estado
```

## Flujo Git para revisión

El incremento de persistencia debe quedar en una rama funcional y revisarse antes de integrarse a `main`:

```powershell
git switch main
git pull
git switch -c feature/persistencia-postgresql
git status
git add pom.xml README.md src
git commit -m "feat: configura persistencia con PostgreSQL"
git push -u origin feature/persistencia-postgresql
```

Luego abre un Pull Request en GitHub desde `feature/persistencia-postgresql` hacia `main`. Describe la entidad y el repository, documenta la comprobación de `count()` realizada contra PostgreSQL y deja explícitas como pendientes las relaciones con usuarios y espacios. El merge se hace después de la revisión.
