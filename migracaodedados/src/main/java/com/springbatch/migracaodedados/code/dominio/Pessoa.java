package com.springbatch.migracaodedados.code.dominio;

import java.util.Date;
import org.apache.logging.log4j.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    private int id;
    private String nome;
    private String email;
    private Date dataNascimento;
    private int idade;

    public boolean isValida() {
        return !Strings.isBlank(nome) && !Strings.isBlank(email) && dataNascimento != null;
    }
}
