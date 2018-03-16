package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.util.Utils;

import java.text.ParseException;

import static br.com.accounting.core.util.Utils.*;
import static br.com.accounting.core.util.Utils.isBlank;

public final class ContaFactory {
    private static ContaFactory contaFactory;
    private Conta conta;

    private ContaFactory() {
        conta = new Conta();
    }

    public static ContaFactory begin() {
        contaFactory = new ContaFactory();
        return contaFactory;
    }

    public ContaFactory withCodigo(String codigo) {
        if (!isBlank(codigo)) {
            conta.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public ContaFactory withNome(String nome) {
        if (!isBlank(nome)) {
            conta.nome(nome);
        }
        return this;
    }

    public ContaFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            conta.descricao(descricao);
        }
        return this;
    }

    public ContaFactory withSaldo(Double saldo) {
        conta.saldo(saldo);
        return this;
    }

    public ContaFactory withSaldo(String saldo) throws ParseException {
        if (!isBlank(saldo)) {
            conta.saldo(createDouble(saldo));
        }
        return this;
    }

    public Conta build() {
        return conta;
    }
}
