# Particionamento Remoto

## Sugestão de execução

### ActiveMQ

Usar o docker composer local: `docker compose up -d`

Endereço: [http://localhost:8161/](http://localhost:8161/)

- Login: admin
- Senha: admin

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

Executando o worker:  `clear && mvn package -Dmaven.test.skip=true && mvn spring-boot:run -Dspring-boot.run.profiles=worker`

Executando o manager:  `clear && mvn package -Dmaven.test.skip=true && mvn spring-boot:run -Dspring-boot.run.profiles=manager`

Você pode usar o profile do maven também:

Executando o worker:  `clear && mvn package -Dmaven.test.skip=true && mvn spring-boot:run -Pworker`

Executando o manager:  `clear && mvn package -Dmaven.test.skip=true && mvn spring-boot:run -Pmanager`

## Genrenciando o banco no vscode com flyway

Pressione `Ctrl+Shift+P` (ou `Cmd+Shift+P`) para abrir o Command Palette.

Digite `Run Task` e selecione a tarefa:

- `run-maven-command-flyway-clean` : Limpa o banco de dados
- `run-maven-command-flyway-migrate` : Cria as tabelas e dados
- `run-maven-command` : Clean e package da aplicação


## Testes unitários

Não há testes unitários para as classes e serviços, mas é executado um teste de integração. Um banco é criado pelo [testcontainers](https://testcontainers.com/) e a aplicação executada. Vejo que o próprio teste já é válido para validar e estudar a aplicação.
