# Banco de dados

Criar um novo banco com o nome `migracao_dados2`;

## Executar o script

`V1.0__Create_Table_Pessoa.sql` e `V1.1__Create_Table_DadosBancarios.sql`.

O motivo é que no paralelismo, não teremos integridade de banco, pois os arquivos serão executados paralelamente.


# Usando o banco de dados posteriormente

Toda vez que for executar, delete os dados antes;

Deletar os dados antigos:

```sql
delete from dados_bancarios;
delete from pessoa p;
```

## Limpar as tabelas do spring batch (se necessário):

```sql
DELETE FROM public.batch_job_execution_context;
DELETE FROM public.batch_job_execution_params;

DELETE FROM public.batch_step_execution_context;
DELETE FROM public.batch_step_execution;

DELETE FROM public.batch_job_execution;
DELETE FROM public.batch_job_instance;
```

# Referências:

[Scalability Parallel Steps](https://docs.spring.io/spring-batch/reference/#scalabilityParallelSteps)

[Split Flows](https://docs.spring.io/spring-batch/reference/#split-flows)