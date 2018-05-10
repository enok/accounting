package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class SubGrupoDTO implements EntityDTO {
    private String codigo;
    private String nome;
    private String descricao;

    @Override
    public String getCodigo() {
        return codigo;
    }
}
