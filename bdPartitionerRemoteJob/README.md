# Particionamento Remoto

## Sugestão de execução

#### Usar docker para o banco de dados:

Ver: [postgres_with_pgadmin4_using_docker](https://github.com/danielso2007/postgres_with_pgadmin4_using_docker)


Criar os bancos de dados:

- migracao_dados3
- spring_batch_bdPartitionerRemoteJob

### Criar as tabelas da aplciação

Executar: `mvn flyway:migrate`

### Limpando o banco de dados

Executar: `mvn flyway:clean`

## Executando a aplicação

Executar: `mvn flyway:clean && clear && mvn package && mvn spring-boot:run`

## Testes unitários

Não há testes unitários para as classes e serviços, mas é executado um teste de integração. Um banco é criado pelo [testcontainers](https://testcontainers.com/) e a aplicação executada. Vejo que o próprio teste já é válido para validar e estudar a aplicação.
