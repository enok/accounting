package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;

public final class ContaDTOMockFactory {
    private ContaDTOMockFactory() {
    }

    public static ContaDTO contaDTOSemNomeEDescricao() {
        return new ContaDTO();
    }

    public static ContaDTO contaDTOSemNome() {
        return new ContaDTO()
                .descricao("Salário mensal recebido pela Sysmap");
    }

    public static ContaDTO contaDTOSemDescricao() {
        return new ContaDTO()
                .nome("Salário");
    }

    public static ContaDTO contaDTO() {
        return new ContaDTO()
                .nome("Salário")
                .descricao("Salário mensal recebido pela Sysmap");
    }

    public static ContaDTO conta2DTO() {
        return new ContaDTO()
                .nome("Enok")
                .descricao("Valor separado para o Enok");
    }
}
