package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;

public final class ContaDTOMockFactory {
    private ContaDTOMockFactory() {
    }

    public static ContaDTO contaDTOSemNomeDescricaoCumulativo() {
        return new ContaDTO();
    }

    public static ContaDTO contaDTOSemNome() {
        return ContaDTOFactory
                .create()
                .withDescricao("Salário mensal recebido pela Sysmap")
                .withCumulativo("N")
                .build();
    }

    public static ContaDTO contaDTOSemDescricao() {
        return ContaDTOFactory
                .create()
                .withNome("Salário")
                .withCumulativo("N")
                .build();
    }

    public static ContaDTO contaDTOSemCumulativo() {
        return ContaDTOFactory
                .create()
                .withNome("Salário")
                .withDescricao("Salário mensal recebido pela Sysmap")
                .build();
    }

    public static ContaDTO contaDTO() {
        return ContaDTOFactory
                .create()
                .withNome("Salário")
                .withDescricao("Salário mensal recebido pela Sysmap")
                .withCumulativo("N")
                .build();
    }

    public static ContaDTO conta2DTO() {
        return ContaDTOFactory
                .create()
                .withNome("Enok")
                .withDescricao("Valor separado para o Enok")
                .withCumulativo("S")
                .build();
    }
}
