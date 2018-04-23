package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Conta;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;

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
//        if (conta == null) {
//            return this;
//        }
        atualizacao = true;
        valorDefaultAnterior = conta.valorDefault();
        return this;
    }

    public Conta build() {
        if (!atualizacao) {
            entity.dataAtualizacao(LocalDate.now());
        }
        else {
            if (entity != null) {
                if (atualizacao) {
                    Double valorDefaultNovo = entity.valorDefault();
                    Double diffValorDefault = valorDefaultNovo - valorDefaultAnterior;
                    Double saldo = entity.saldo();
                    Double novoSaldo;
//                    if (saldo == null) {
//                        novoSaldo = diffValorDefault;
//                    }
//                    else {
//                    }
                    novoSaldo = saldo + diffValorDefault;
                    entity.saldo(novoSaldo);
                }
//                else if (entity.saldo() == null) {
//                    entity.saldo(entity.valorDefault());
//                }
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

    public ContaFactory withValorDefault(String valorDefault) {
        if (!isBlankOrNull(valorDefault)) {
            withValorDefault(getDoubleFromString(valorDefault));
        }
        return this;
    }

    public ContaFactory withSaldo(String saldo) {
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
