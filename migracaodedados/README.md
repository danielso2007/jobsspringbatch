# migracaodedados

Projeto simples de job com spring batch.

Usar o projeto abaixo para executar o PostgreSQL em docker.

[postgres_with_pgadmin4_using_docker](https://github.com/danielso2007/postgres_with_pgadmin4_using_docker)

Primeiramente criar os bancos abaixo no Postgresql:

- `migracao_dados`
- `spring_batch`

No esquema `public` do banco `migracao_dados`, executar o script abaixo:

`migracaodedados/src/main/resources/schema-all.sql`

O spring batch irá criar as tabelas no banco `spring_batch` automaticamente.
