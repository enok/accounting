package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.core.entity.Conta;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;

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
        dto.dataAtualizacao(getStringFromCurrentDate());
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
        withValorDefault(conta.valorDefault());
        withSaldo(conta.saldo());
        withCumulativo(conta.cumulativo());
        withDataAtualizacao(conta.dataAtualizacao());
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

    public ContaDTOFactory withValorDefault(String valorDefault) {
        if (!isBlankOrNull(valorDefault)) {
            dto.valorDefault(valorDefault);
        }
        return this;
    }

    private ContaDTOFactory withValorDefault(Double valorDefault) {
        if (valorDefault != null) {
            withValorDefault(getStringFromDouble(valorDefault));
        }
        return this;
    }

    public ContaDTOFactory withSaldo(Double saldo) {
        if (saldo != null) {
            dto.saldo(getStringFromDouble(saldo));
        }
        return this;
    }

    public ContaDTOFactory withCumulativo(String cumulativo) {
        if (!isBlankOrNull(cumulativo)) {
            dto.cumulativo(cumulativo);
        }
        return this;
    }

    public ContaDTOFactory withCumulativo(Boolean cumulativo) {
        if (cumulativo != null) {
            withCumulativo(getStringFromBoolean(cumulativo));
        }
        return this;
    }

    public ContaDTOFactory withDataAtualizacao(String dataAtualizacao) {
        if (!isBlankOrNull(dataAtualizacao)) {
            dto.dataAtualizacao(dataAtualizacao);
        }
        return this;
    }

    public ContaDTOFactory withDataAtualizacao(LocalDate dataAtualizacao) {
        if (dataAtualizacao != null) {
            withDataAtualizacao(getStringFromDate(dataAtualizacao));
        }
        return this;
    }
}
