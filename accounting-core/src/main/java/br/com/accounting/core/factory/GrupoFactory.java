package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;

import static br.com.accounting.core.util.Utils.isBlank;

public class GrupoFactory {
    private static GrupoFactory grupoFactory;
    private Grupo grupo;

    private GrupoFactory() {
        grupo = new Grupo();
    }

    public static GrupoFactory begin() {
        grupoFactory = new GrupoFactory();
        return grupoFactory;
    }

    public GrupoFactory withCodigo(String codigo) {
        if (!isBlank(codigo)) {
            grupo.withCodigo(Long.parseLong(codigo));
        }
        return this;
    }

    public GrupoFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            grupo.withDescricao(descricao);
        }
        return this;
    }

    public GrupoFactory withSubGrupo(String subGrupoCodigo, String subGrupoDescricao) {
        if (!isBlank(subGrupoCodigo)) {
            SubGrupo subGrupo = SubGrupoFactory
                    .begin()
                    .withCodigo(subGrupoCodigo)
                    .withDescricao(subGrupoDescricao)
                    .build();
            grupo.withSubGrupo(subGrupo);
        }
        return this;
    }

    public GrupoFactory withSubGrupo(SubGrupo subGrupo) {
        grupo.withSubGrupo(subGrupo);
        return this;
    }

    public Grupo build() {
        return grupo;
    }
}
