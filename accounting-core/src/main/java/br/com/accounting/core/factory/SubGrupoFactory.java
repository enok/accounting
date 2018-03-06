package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubGrupo;

import static br.com.accounting.core.util.Utils.isBlank;

public class SubGrupoFactory {
    private static SubGrupoFactory subGrupoFactory;
    private SubGrupo subGrupo;

    private SubGrupoFactory() {
        subGrupo = new SubGrupo();
    }

    public static SubGrupoFactory begin() {
        subGrupoFactory = new SubGrupoFactory();
        return subGrupoFactory;
    }

    public SubGrupoFactory withCodigo(String codigo) {
        if (!isBlank(codigo)) {
            subGrupo.withCodigo(Long.parseLong(codigo));
        }
        return this;
    }

    public SubGrupoFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            subGrupo.withDescricao(descricao);
        }
        return this;
    }

    public SubGrupo build() {
        return subGrupo;
    }
}
