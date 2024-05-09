DROP TABLE IF EXISTS public.dados_bancarios;
DROP TABLE IF EXISTS public.pessoa;

CREATE TABLE public.pessoa (
	id int8 NOT NULL,
	nome varchar(500) NOT NULL,
	email varchar(500) NOT NULL,
	idade int4 NOT NULL,
	data_nascimento date NOT NULL,
    data_criacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	data_atualizacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

CREATE TABLE public.dados_bancarios (
	id int8 NOT NULL,
	pessoa_id int8 NULL,
	banco int4 NOT NULL,
	agencia int4 NOT NULL,
	conta int4 NOT NULL,
    data_criacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	data_atualizacao timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT dados_bancarios_pkey PRIMARY KEY (id),
	CONSTRAINT pessoa_daos_bancarios_fk FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id)
);