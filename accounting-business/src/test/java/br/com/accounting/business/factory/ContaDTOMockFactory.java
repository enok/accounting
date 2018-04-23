package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContaDTO;

public final class ContaDTOMockFactory {
    private ContaDTOMockFactory() {
    }

    public static ContaDTO contaDTOSemCamposObrigatorios() {
        return new ContaDTO();
    }

    public static ContaDTO contaDTOSemNome() {
        return contaSalario()
                .nome(null);
    }

    public static ContaDTO contaDTOSemDescricao() {
        return contaSalario()
                .descricao(null);
    }

    public static ContaDTO contaDTOSemValorDefault() {
        return contaSalario()
                .valorDefault(null);
    }

    public static ContaDTO contaDTOSemCumulativo() {
        return contaSalario()
                .cumulativo(null);
    }

    public static ContaDTO contaSalario() {
        return ContaDTOFactory
                .create()
                .begin()
                .withNome("Salário")
                .withDescricao("Salário mensal recebido pela Sysmap")
                .withValorDefault("1000,00")
                .withCumulativo("N")
                .withDataAtualizacao("15/04/2018")
                .build();
    }

    public static ContaDTO contaEnok() {
        return ContaDTOFactory
                .create()
                .begin()
                .withNome("Enok")
                .withDescricao("Valor separado para o Enok")
                .withValorDefault("500,00")
                .withCumulativo("S")
                .withDataAtualizacao("10/03/2018")
                .build();
    }
}
