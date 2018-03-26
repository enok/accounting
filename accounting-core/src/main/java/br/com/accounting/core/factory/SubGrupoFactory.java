package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubGrupo;

import static br.com.accounting.core.util.Utils.isBlank;

public final class SubGrupoFactory {
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
        if (isBlank(codigo)) {
            subGrupo.codigo(0L);
        }
        else {
            subGrupo.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public SubGrupoFactory withNome(String nome) {
        if (!isBlank(nome)) {
            subGrupo.nome(nome);
        }
        return this;
    }

    public SubGrupoFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            subGrupo.descricao(descricao);
        }
        return this;
    }

    public SubGrupo build() {
        return subGrupo;
    }
}
