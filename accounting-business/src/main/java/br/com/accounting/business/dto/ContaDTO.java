package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class ContaDTO {
    private String codigo;
    private String nome;
    private String descricao;
    private String saldo;
}
