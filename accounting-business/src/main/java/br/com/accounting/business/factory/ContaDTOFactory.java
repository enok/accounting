package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ContaDTOFactory {

    private static ContaDTOFactory contaDTOFactory;
    private ContaDTO contaDTO;

    private ContaDTOFactory() {
        contaDTO = new ContaDTO();
    }

    public static ContaDTOFactory begin() {
        contaDTOFactory = new ContaDTOFactory();
        return contaDTOFactory;
    }

    public ContaDTOFactory withNome(String nome) {
        if (!isBlank(nome)) {
            contaDTO.nome(nome);
        }
        return contaDTOFactory;
    }

    public ContaDTOFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            contaDTO.descricao(descricao);
        }
        return contaDTOFactory;
    }

    public ContaDTOFactory withSaldo(Double saldo) {
        if (saldo != null) {
            contaDTO.saldo(saldo.toString());
        }
        return contaDTOFactory;
    }

    public ContaDTO build() {
        return contaDTO;
    }
}
