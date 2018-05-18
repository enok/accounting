package br.com.accounting.business.dto;

import br.com.accounting.commons.dto.EntityDTO;
import lombok.Data;
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
