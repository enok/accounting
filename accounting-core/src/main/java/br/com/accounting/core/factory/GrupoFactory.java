package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Grupo;

import java.util.List;

import static br.com.accounting.core.util.Utils.isBlank;
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

    public GrupoFactory withCodigo(String codigo) {
        entity.codigo(Long.parseLong(codigo));
        return this;
    }

    public GrupoFactory withNome(String nome) {
        if (!isBlank(nome)) {
            entity.nome(nome);
        }
        return this;
    }

    public GrupoFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            entity.descricao(descricao);
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

    public GrupoFactory withSubGrupo(String subGrupo) {
        entity.addSubGrupo(subGrupo);
        return this;
    }

    public Grupo build() {
        return entity;
    }
}
