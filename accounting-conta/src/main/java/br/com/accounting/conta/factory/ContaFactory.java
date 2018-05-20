package br.com.accounting.conta.factory;

import br.com.accounting.commons.entity.Conta;

import java.text.ParseException;
import java.time.LocalDate;

import static br.com.accounting.commons.util.Utils.*;

public final class ContaFactory {
    private static ContaFactory factory;
    private Conta entity;
    private boolean atualizacao = false;
    private Double valorDefaultAnterior;

    private ContaFactory() {
        entity = new Conta();
    }

    public static ContaFactory begin() {
        factory = new ContaFactory();
        return factory;
    }

    public ContaFactory preencherCamposBuscados(Conta conta) {
        atualizacao = true;
        valorDefaultAnterior = conta.valorDefault();
        return this;
    }

    public Conta build() {
        if (!atualizacao) {
            if (entity.dataAtualizacao() == null) {
                entity.dataAtualizacao(LocalDate.now());
            }
            if (entity.saldo() == null) {
                entity.saldo(entity.valorDefault());
            }
        }
        else {
            if (entity != null) {
                if (atualizacao) {
                    Double valorDefaultNovo = entity.valorDefault();
                    Double diffValorDefault = valorDefaultNovo - valorDefaultAnterior;
                    Double saldo = entity.saldo() == null ? 0 : entity.saldo();
                    Double novoSaldo;
                    novoSaldo = saldo + diffValorDefault;
                    entity.saldo(novoSaldo);
                }
            }
        }
        return entity;
    }

    private ContaFactory withCodigo(Long codigo) {
        if (codigo != null) {
            entity.codigo(codigo);
        }
        return this;
    }

    public ContaFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            withCodigo(Long.parseLong(codigo));
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

    public ContaFactory withValorDefault(Double valorDefault) {
        if (valorDefault != null) {
            entity.valorDefault(valorDefault);
        }
        return this;
    }

    public ContaFactory withValorDefault(String valorDefault) throws ParseException {
        if (!isBlankOrNull(valorDefault)) {
            withValorDefault(getDoubleFromString(valorDefault));
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

    private ContaFactory withDataAtualizacao(LocalDate dataAtualizacao) {
        if (dataAtualizacao != null) {
            entity.dataAtualizacao(dataAtualizacao);
        }
        return this;
    }

    public ContaFactory withDataAtualizacao(String dataAtualizacao) {
        if (!isBlankOrNull(dataAtualizacao)) {
            withDataAtualizacao(getDateFromString(dataAtualizacao));
        }
        return this;
    }
}
