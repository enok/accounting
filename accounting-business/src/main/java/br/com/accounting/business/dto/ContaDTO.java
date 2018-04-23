package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class ContaDTO implements EntityDTO {
    private String codigo;
    private String nome;
    private String descricao;
    private String valorDefault;
    private String saldo;
    private String cumulativo;
    private String dataAtualizacao;

    @Override
    public String getCodigo() {
        return codigo;
    }
}
