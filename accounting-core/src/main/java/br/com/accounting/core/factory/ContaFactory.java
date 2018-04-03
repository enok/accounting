package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Conta;

import java.text.ParseException;

import static br.com.accounting.core.util.Utils.getDoubleFromString;
import static br.com.accounting.core.util.Utils.isBlankOrNull;

public final class ContaFactory {
    private static ContaFactory factory;
    private Conta entity;

    private ContaFactory() {
        entity = new Conta();
    }

    public static ContaFactory begin() {
        factory = new ContaFactory();
        return factory;
    }

    public Conta build() {
        if (entity.saldo() == null) {
            entity.saldo(0.0);
        }
        return entity;
    }

    public ContaFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public ContaFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            entity.nome(nome);
        }
        return this;
    }

    public ContaFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            entity.descricao(descricao);
        }
        return this;
    }

    public ContaFactory withSaldo(Double saldo) {
        if (saldo != null) {
            entity.saldo(saldo);
        }
        return this;
    }

    public ContaFactory withSaldo(String saldo) throws ParseException {
        if (!isBlankOrNull(saldo)) {
            withSaldo(getDoubleFromString(saldo));
        }
        return this;
    }
}
