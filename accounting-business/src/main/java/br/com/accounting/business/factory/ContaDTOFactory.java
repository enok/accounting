package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.core.entity.Conta;

import static br.com.accounting.core.util.Utils.isBlankOrNull;

public final class ContaDTOFactory extends GenericDTOFactory<ContaDTO, Conta> {
    private static ContaDTOFactory factory;

    private ContaDTO dto;

    private ContaDTOFactory() {
        dto = new ContaDTO();
    }

    public static ContaDTOFactory create() {
        return new ContaDTOFactory();
    }

    @Override
    public ContaDTOFactory begin() {
        factory = new ContaDTOFactory();
        return factory;
    }

    @Override
    public ContaDTOFactory preencherCampos(Conta conta) {
        if (conta == null) {
            dto = null;
            return this;
        }
        withCodigo(conta.codigo());
        withNome(conta.nome());
        withDescricao(conta.descricao());
        withSaldo(conta.saldo());
        return this;
    }

    @Override
    public ContaDTO build() {
        return dto;
    }

    public ContaDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            dto.codigo(codigo.toString());
        }
        return this;
    }

    public ContaDTOFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            dto.nome(nome);
        }
        return this;
    }

    public ContaDTOFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            dto.descricao(descricao);
        }
        return this;
    }

    public ContaDTOFactory withSaldo(Double saldo) {
        if (saldo != null) {
            dto.saldo(saldo.toString());
        }
        return this;
    }
}
