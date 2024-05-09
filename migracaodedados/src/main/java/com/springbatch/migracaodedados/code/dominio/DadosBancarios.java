package com.springbatch.migracaodedados.code.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DadosBancarios {
	private int id;
	private int pessoaId;
	private int agencia;
	private int conta;
	private int banco;
}
