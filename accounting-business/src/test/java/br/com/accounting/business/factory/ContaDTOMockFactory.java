package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;

public final class ContaDTOMockFactory {
    private ContaDTOMockFactory() {
    }

    public static ContaDTO contaDTOSemCamposObrigatorios() {
        return new ContaDTO();
    }

    public static ContaDTO contaDTOSemNome() {
        return contaDTO()
                .nome(null);
    }

    public static ContaDTO contaDTOSemDescricao() {
        return contaDTO()
                .descricao(null);
    }

    public static ContaDTO contaDTOSemValorDefault() {
        return contaDTO()
                .valorDefault(null);
    }

    public static ContaDTO contaDTOSemCumulativo() {
        return contaDTO()
                .cumulativo(null);
    }

    public static ContaDTO contaDTO() {
        return ContaDTOFactory
                .create()
                .withNome("Salário")
                .withDescricao("Salário mensal recebido pela Sysmap")
                .withValorDefault("1000,00")
                .withCumulativo("N")
                .build();
    }

    public static ContaDTO contaEnok() {
        return ContaDTOFactory
                .create()
                .withNome("Enok")
                .withDescricao("Valor separado para o Enok")
                .withValorDefault("500,00")
                .withCumulativo("S")
                .withDataAtualizacao("10/03/2018")
                .build();
    }
}
