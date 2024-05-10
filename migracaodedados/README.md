# migracaodedados

Projeto simples de job com spring batch.

Para o banco de dados, usar o projeto abaixo para executar o PostgreSQL em docker.

[postgres_with_pgadmin4_using_docker](https://github.com/danielso2007/postgres_with_pgadmin4_using_docker)

Primeiramente criar os bancos abaixo no Postgresql:

- `migracao_dados`
- `spring_batch`

O banco será criado automaticamente pelo flyway.

### Flyway

Está como padrão `flyway.enable: true` e com isso o banco será criado automaticamente no Postgres.

1. migrate: Aplica todas as migrações pendentes no banco de dados.
2. clean: Limpa o banco de dados, removendo todas as tabelas e objetos de esquema.
3. info: Exibe informações sobre o estado atual do banco de dados e as migrações aplicadas.
4. validate: Valida as migrações no sistema de arquivos em relação ao banco de dados.
5. undo: Desfaz a última migração aplicada no banco de dados.
6. baseline: Marca o banco de dados atual com uma linha de base (baseline) na versão especificada.
7. repair: Repara a tabela schema_version do Flyway se ela estiver corrompida.

#### Para limpar o banco

Executar o maven: `mvn flyway:clean`