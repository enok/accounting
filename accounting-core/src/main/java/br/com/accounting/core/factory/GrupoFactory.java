package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Grupo;

import java.util.List;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public final class GrupoFactory {
    private static GrupoFactory factory;
    private Grupo entity;

    private GrupoFactory() {
        entity = new Grupo();
    }

    public static GrupoFactory begin() {
        factory = new GrupoFactory();
        return factory;
    }

    public Grupo build() {
        return entity;
    }

    public GrupoFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public GrupoFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            entity.nome(nome);
        }
        return this;
    }

    public GrupoFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            entity.descricao(descricao);
        }
        return this;
    }

    public GrupoFactory withSubGrupo(String subGrupo) {
        if (!isBlankOrNull(subGrupo)) {
            entity.addSubGrupo(subGrupo);
        }
        return this;
    }

    public GrupoFactory withSubGrupos(List<String> subGrupos) {
        if (!isEmpty(subGrupos)) {
            for (String subGrupo : subGrupos) {
                entity.addSubGrupo(subGrupo);
            }
        }
        return this;
    }
}
