package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Conta;

import java.text.ParseException;
import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;

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
        entity.dataAtualizacao(LocalDate.now());
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

    public ContaFactory withSaldo(String saldo) throws ParseException {
        if (!isBlankOrNull(saldo)) {
            withSaldo(getDoubleFromString(saldo));
        }
        return this;
    }

    public ContaFactory withSaldo(Double saldo) {
        if (saldo != null) {
            entity.saldo(saldo);
        }
        return this;
    }

    public ContaFactory withCumulativo(String cumulativo) {
        if (!isBlankOrNull(cumulativo)) {
            withCumulativo(getBooleanFromString(cumulativo));
        }
        return this;
    }

    private ContaFactory withCumulativo(Boolean cumulativo) {
        if (cumulativo != null) {
            entity.cumulativo(cumulativo);
        }
        return this;
    }

    public ContaFactory withDataAtualizacao(String dataAtualizacao) {
        if (!isBlankOrNull(dataAtualizacao)) {
            entity.dataAtualizacao(getDateFromString(dataAtualizacao));
        }
        return this;
    }
}
