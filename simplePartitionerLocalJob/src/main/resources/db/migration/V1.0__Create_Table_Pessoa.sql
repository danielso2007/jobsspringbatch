-- DROP TABLE IF EXISTS public.dados_bancarios;
-- DROP TABLE IF EXISTS public.pessoa;

CREATE TABLE public.pessoa (
    id int8 NOT NULL,
    nome varchar(500) NOT NULL,
    email varchar(500) NOT NULL,
    idade int4 NOT NULL,
    data_nascimento date NOT NULL,
    data_criacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_atualizacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);
