package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
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

    public GrupoDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GrupoDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public GrupoDTO setSubGrupos(List<String> subGrupos) {
        this.subGrupos = subGrupos;
        return this;
    }

    public GrupoDTO addSubGrupo(String subGrupo) {
        if (!StringUtils.isBlank(subGrupo)) {
            this.subGrupos.add(subGrupo);
        }
        return this;
    }
}
