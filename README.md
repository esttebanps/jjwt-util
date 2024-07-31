# JwtUtil

JwtUtil es una utilidad de Java para manejar JSON Web Tokens (JWT). Proporciona funciones para crear, validar y extraer información de tokens JWT.

## Características

- Creación de tokens JWT con roles de usuario y fecha de expiración personalizable
- Validación de tokens JWT
- Extracción de nombre de usuario y roles de tokens JWT
- Manejo de errores y registro de excepciones

## Requisitos

- Java 8 o superior
- Biblioteca `io.jsonwebtoken`

## Uso

### Crear un token

```java
String[] roles = {"USER", "ADMIN"};
SecretKey secretKey = // tu clave secreta
String token = JwtUtil.createToken("username", roles, secretKey, 7); // token válido por 7 días
```

### Validar un token

```java
Boolean isValid = JwtUtil.isTokenValid(token, secretKey);
```

### Obtener el nombre de usuario de un token

```java
String username = JwtUtil.getUsernameFromToken(token, secretKey);
```

### Obtener los roles de un token

```java
List<String> roles = JwtUtil.getRolesFromToken(token, secretKey);
```

## Manejo de errores

JwtUtil registra los siguientes errores:

- Token expirado
- Token no soportado
- Token malformado
- Argumentos ilegales

## Contribuir

Las contribuciones son bienvenidas. Por favor, abre un issue para discutir los cambios propuestos antes de enviar un pull request.
