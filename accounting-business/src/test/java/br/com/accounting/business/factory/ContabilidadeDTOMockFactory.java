package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContabilidadeDTO;

import static br.com.accounting.core.util.Utils.getStringFromBoolean;

public final class ContabilidadeDTOMockFactory {
    private ContabilidadeDTOMockFactory() {
    }

    public static ContabilidadeDTO contabilidadeDTO() {
        return ContabilidadeDTOFactory
                .create()
                .withDataVencimento("27/04/2018")
                .withDataPagamento("27/04/2018")
                .withRecorrente(getStringFromBoolean(false))
                .withGrupo("Saúde", "Suplementos")
                .withDescricao("Suplementos comprados pela Carol")
                .withUsouCartao(getStringFromBoolean(true))
                .withCartao("0744")
                .withParcelado(getStringFromBoolean(true))
                .withParcelas("7")
                .withConta("CAROL")
                .withTipo("DEBITO")
                .withValor("24,04")
                .build();
    }

    public static ContabilidadeDTO contabilidadeDTOSemDataVencimento() {
        return contabilidadeDTO()
                .dataVencimento(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemDataPagamento() {
        return contabilidadeDTO()
                .dataPagamento(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemRecorrente() {
        return contabilidadeDTO()
                .recorrente(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemGrupo() {
        return contabilidadeDTO()
                .grupo(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemSubGrupo() {
        return contabilidadeDTO()
                .subGrupo(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemDescricao() {
        return contabilidadeDTO()
                .descricao(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemUsouCartao() {
        return contabilidadeDTO()
                .usouCartao("N");
    }

    public static ContabilidadeDTO contabilidadeDTONaoUsouCartao() {
        return contabilidadeDTO()
                .usouCartao("N");
    }

    public static ContabilidadeDTO contabilidadeDTOSemCartao() {
        return contabilidadeDTO()
                .cartao(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemParcelado() {
        return contabilidadeDTO()
                .parcelado(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemParcelas() {
        return contabilidadeDTO()
                .parcelas(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemConta() {
        return contabilidadeDTO()
                .conta(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemTipo() {
        return contabilidadeDTO()
                .tipo(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemValor() {
        return contabilidadeDTO()
                .valor(null);
    }

    public static ContabilidadeDTO contabilidadeDTOSemCamposObrigatorios() {
        return ContabilidadeDTOFactory
                .create()
                .build();
    }
}
