CREATE TABLE public.dados_bancarios (
    id int8 NOT NULL,
    pessoa_id int8 NULL,
    banco int4 NOT NULL,
    agencia int4 NOT NULL,
    conta int4 NOT NULL,
    data_criacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_atualizacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);
