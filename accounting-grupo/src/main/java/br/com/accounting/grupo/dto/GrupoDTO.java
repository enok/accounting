package br.com.accounting.grupo.dto;

import br.com.accounting.commons.dto.EntityDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class GrupoDTO implements EntityDTO {
    private String codigo;
    private String nome;
    private String descricao;
    private List<String> subGrupos = new ArrayList<>();

    @Override
    public String getCodigo() {
        return codigo;
    }

    public GrupoDTO addSubGrupo(String subGrupo) {
        if (!StringUtils.isBlank(subGrupo)) {
            this.subGrupos.add(subGrupo);
        }
        return this;
    }
}
