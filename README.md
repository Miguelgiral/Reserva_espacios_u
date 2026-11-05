# Reservas de espacios API

API REST para gestionar reservas de espacios, construida con Java y Spring Boot.

## Requisitos

- JDK compatible con la versión de Java declarada en `pom.xml`.
- Maven Wrapper incluido en el proyecto.

## Ejecutar

```powershell
.\mvnw.cmd spring-boot:run
```

## Endpoint existente

```http
GET /api/estado
```

## Ramas de trabajo

`develop` es la rama principal de integración durante esta etapa. Las ramas locales son `feature/iteracionGet`, `feature/login` y `feature/persistencia`. En IntelliJ, usa el selector de ramas de la esquina inferior derecha para cambiar entre ellas. Crea ramas nuevas desde `develop` cuando sea necesario:

```powershell
git switch develop
git switch -c feature/nombre
```

Las ramas se encuentran creadas localmente. Aún no se han publicado en el remoto ni se ha cambiado allí la rama predeterminada.
