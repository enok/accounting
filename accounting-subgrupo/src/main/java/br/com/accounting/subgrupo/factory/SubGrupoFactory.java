package br.com.accounting.subgrupo.factory;

import br.com.accounting.commons.entity.SubGrupo;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;

public final class SubGrupoFactory {
    private static SubGrupoFactory factory;
    private SubGrupo entity;

    private SubGrupoFactory() {
        entity = new SubGrupo();
    }

    public static SubGrupoFactory begin() {
        factory = new SubGrupoFactory();
        return factory;
    }

    public SubGrupo build() {
        return entity;
    }

    public SubGrupoFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public SubGrupoFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            entity.nome(nome);
        }
        return this;
    }

    public SubGrupoFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            entity.descricao(descricao);
        }
        return this;
    }
}
