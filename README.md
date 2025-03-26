# Serviço de Encurtamento de URLs

Este é um serviço web para encurtar URLs longas, tornando-as mais fáceis de compartilhar e gerenciar.

**Nota:** Este projeto é uma solução de exemplo para o desafio do [URL Shortening Service](https://roadmap.sh/projects/url-shortening-service) do [roadmap.sh](https://roadmap.sh/)

## Descrição

O serviço permite criar URLs curtas a partir de URLs longas, recuperar as URLs originais a partir das URLs curtas, obter estatísticas de acesso para as URLs curtas, atualizar as URLs originais e deletar as URLs curtas.

## Tecnologias Utilizadas

* **Java:** Linguagem de programação principal.
* **Spring Boot:** Framework para desenvolvimento rápido de aplicações Java.
* **Spring Web:** Para criação de APIs RESTful.
* **Hibernate Validator:** Para validação de dados de entrada.
* **Swagger/OpenAPI:** Para documentação da API.
* **PostgreSQL:** Banco de dados relacional para armazenamento de dados.
* **MapStruct:** Para mapeamento de objetos.

## Configuração do Banco de Dados

O serviço utiliza um banco de dados PostgreSQL. A configuração do banco de dados está no arquivo `application.properties`:

```properties
spring.application.name=url-shortening-service
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortening_db
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Certifique-se de que o banco de dados PostgreSQL esteja configurado corretamente e acessível nas credenciais especificadas.

## Endpoints da API

A API é acessível através do endpoint `/shorten`.

* **POST /shorten:** Cria uma nova URL curta.
    * Requer um objeto JSON com o campo `url` contendo a URL longa.
    * Retorna um objeto JSON com os campos `id`, `url`, `shortUrl`, `createdAt` e `updatedAt`.
    * Exemplo de requisição:
        ```json
        {
            "url": "https://www.exemplo.com/pagina-muito-longa"
        }
        ```
    * Exemplo de resposta:
        ```json
        {
            "id": 1,
            "url": "https://www.exemplo.com/pagina-muito-longa",
            "shortUrl": "abc123",
            "createdAt": "2025-03-25T00:00:00.000+00:00",
            "updatedAt": "2025-03-25T00:00:00.000+00:00"
        }
        ```
    * Códigos de resposta:
        * 201 Created: URL curta criada com sucesso.
        * 400 Bad Request: Requisição inválida (URL inválida).

* **GET /shorten/{shortUrl}:** Recupera a URL original a partir da URL curta.
    * Requer a URL curta como um parâmetro de caminho.
    * Retorna um objeto JSON com os campos `id`, `url`, `shortUrl`, `createdAt` e `updatedAt`.
    * Exemplo de requisição:
        ```http GET http://localhost:8080/shorten/abc123```
    * Exemplo de resposta:
        ```json
      {
            "id": 1,
            "url": "https://www.exemplo.com/pagina-muito-longa",
            "shortUrl": "abc123",
            "createdAt": "2025-03-25T00:00:00.000+00:00",
            "updatedAt": "2025-03-25T00:00:00.000+00:00"
        }
        ```
    * Códigos de resposta:
        * 200 OK: URL original recuperada com sucesso.
        * 404 Not Found: URL não encontrada.

* **GET /shorten/{shortUrl}/stats:** Obtém estatísticas de acesso para a URL curta.
    * Requer a URL curta como um parâmetro de caminho.
    * Retorna um objeto JSON com os campos `id`, `url`, `shortUrl`, `createdAt`, `updatedAt` e `accessCount`.
    * Exemplo de requisição:
        ```http GET http://localhost:8080/shorten/abc123/stats```
    * Exemplo de resposta:
        ```json
        {
            "id": 1,
            "url": "https://www.exemplo.com/pagina-muito-longa",
            "shortUrl": "abc123",
            "createdAt": "2025-03-25T00:00:00.000+00:00",
            "updatedAt": "2025-03-25T00:00:00.000+00:00",
            "accessCount": 5
        }
        ```
    * Códigos de resposta:
        * 200 OK: Estatísticas da URL recuperadas com sucesso.
        * 404 Not Found: URL não encontrada.

* **PUT /shorten/{shortUrl}:** Atualiza a URL original para a URL curta.
    * Requer a URL curta como um parâmetro de caminho e um objeto JSON com o campo `url` contendo a nova URL longa.
    * Retorna um objeto JSON com os campos `id`, `url`, `shortUrl`, `createdAt` e `updatedAt`.
    * Exemplo de requisição:
        ```json
        {
            "url": "https://www.exemplo.com/pagina-atualizada"
        }
        ```
    * Exemplo de resposta:
        ```json
        {
            "id": 1,
            "url": "https://www.exemplo.com/pagina-atualizada",
            "shortUrl": "abc123",
            "createdAt": "2025-03-25T00:00:00.000+00:00",
            "updatedAt": "2025-03-25T00:00:00.000+00:00"
        }
        ```
    * Códigos de resposta:
        * 200 OK: URL atualizada com sucesso.
        * 404 Not Found: URL não encontrada.

* **DELETE /shorten/{shortUrl}:** Deleta a URL curta.
    * Requer a URL curta como um parâmetro de caminho.
    * Exemplo de requisição:
        ```http DELETE http://localhost:8080/shorten/abc123```
    * Códigos de resposta:
        * 204 No Content: URL deletada com sucesso.
        * 404 Not Found: URL não encontrada.

## Como Executar

1. Certifique-se de ter o Java e o Maven instalados.
2. Configure o banco de dados PostgreSQL.
3. Clone este repositório.
4. Navegue até o diretório do projeto.
5. Execute `mvn spring-boot:run`.
6. A API estará disponível em `http://localhost:8080/shorten` e a documentação do Swagger em `http://localhost:8080/swagger-ui/index.html`.

## Como Usar

Você pode usar ferramentas como `curl`, Postman ou Swagger para interagir com a API.

### Exemplo de Uso (curl)

* Criar uma URL curta:

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"url":"https://www.exemplo.com/pagina-muito-longa"}' http://localhost:8080/shorten
    ```

* Recuperar a URL original:

    ```bash
    curl http://localhost:8080/shorten/abc123
    ```

* Obter estatísticas da URL curta:

    ```bash
    curl http://localhost:8080/shorten/abc123/stats
    ```

* Atualizar a URL original:

    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"url":"https://www.exemplo.com/pagina-atualizada"}' http://localhost:8080/shorten/abc123
    ```

* Deletar a URL curta:

    ```bash
    curl -X DELETE http://localhost:8080/shorten/abc123
    ```

## Dependências

* Spring Web
* Spring Boot Validation
* Spring Data JPA
* PostgreSQL
* Swagger/OpenAPI
* MapStruct

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
