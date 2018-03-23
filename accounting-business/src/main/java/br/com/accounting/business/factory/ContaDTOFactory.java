package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.core.entity.Conta;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ContaDTOFactory extends GenericDTOFactory<ContaDTO, Conta> {
    private static ContaDTOFactory contaDTOFactory;

    private ContaDTO contaDTO;

    private ContaDTOFactory() {
        contaDTO = new ContaDTO();
    }

    public static ContaDTOFactory create() {
        return new ContaDTOFactory();
    }

    @Override
    public ContaDTOFactory begin() {
        contaDTOFactory = new ContaDTOFactory();
        return contaDTOFactory;
    }

    @Override
    public ContaDTOFactory preencherCampos(Conta conta) {
        withCodigo(conta.codigo());
        withNome(conta.nome());
        withDescricao(conta.descricao());
        withSaldo(conta.saldo());
        return this;
    }

    @Override
    public ContaDTO build() {
        return contaDTO;
    }

    public ContaDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            contaDTO.codigo(codigo.toString());
        }
        return this;
    }

    public ContaDTOFactory withNome(String nome) {
        if (!isBlank(nome)) {
            contaDTO.nome(nome);
        }
        return this;
    }

    public ContaDTOFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            contaDTO.descricao(descricao);
        }
        return this;
    }

    public ContaDTOFactory withSaldo(Double saldo) {
        if (saldo != null) {
            contaDTO.saldo(saldo.toString());
        }
        return this;
    }
}
