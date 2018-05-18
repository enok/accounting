package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Local;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;

public final class LocalFactory {
    private static LocalFactory factory;
    private Local entity;

    private LocalFactory() {
        entity = new Local();
    }

    public static LocalFactory begin() {
        factory = new LocalFactory();
        return factory;
    }

    public Local build() {
        return entity;
    }

    public LocalFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public LocalFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            entity.nome(nome);
        }
        return this;
    }
}
