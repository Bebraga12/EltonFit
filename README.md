# `EltonFit` Backend

API do projeto `EltonFit`, construída com `Java 21` e `Spring Boot`.

## Como rodar

No diretório `EltonFit`, execute:

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

Documentação Swagger:

- `http://localhost:8080/swagger-ui.html`

## Configuração local com arquivo de usuário

Crie o arquivo `src/main/resources/application-user.properties`.

Depois rode com perfil `user`:

```bash
SPRING_PROFILES_ACTIVE=user ./mvnw spring-boot:run
```

## Conteúdo para copiar e colar em `application-user.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eltonfit
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

security.jwt.secret=COLE_UM_SECRET_BASE64_AQUI
security.jwt.expiration-ms=86400000

app.bootstrap.admin.email=adm@email.com
app.bootstrap.admin.password=admin123
```

## Login admin inicial

O bootstrap cria o admin inicial com:

- email: `adm@email.com`
- senha: `admin123`

Você pode trocar no arquivo `application-user.properties`.

## Comandos úteis

Build:

```bash
./mvnw -DskipTests package
```

Rodar testes:

```bash
./mvnw test
```
