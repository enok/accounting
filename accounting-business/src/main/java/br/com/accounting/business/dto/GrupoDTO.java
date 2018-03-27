package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class GrupoDTO {
    private String codigo;
    private String nome;
    private String descricao;
    private List<String> subGrupos = new ArrayList<>();

    public void addSubGrupo(String subGrupo) {
        if (!StringUtils.isBlank(subGrupo)) {
            this.subGrupos.add(subGrupo);
        }
    }
}
