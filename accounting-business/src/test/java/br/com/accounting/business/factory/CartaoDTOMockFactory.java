package br.com.accounting.business.factory;

import br.com.accounting.business.dto.CartaoDTO;

public final class CartaoDTOMockFactory {
    private CartaoDTOMockFactory() {
    }

    public static CartaoDTO criarCartaoFisico7660() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("FISICO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico0744() {
        return new CartaoDTO()
                .numero("0744")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Carol")
                .tipo("FISICO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoVirtual7339() {
        return new CartaoDTO()
                .numero("7339")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("DIGITAL")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemNumero() {
        return new CartaoDTO()
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("FISICO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemVencimento() {
        return new CartaoDTO()
                .numero("7660")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("FISICO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemDiaMelhorCompra() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .portador("Enok")
                .tipo("FISICO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemPortador() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .tipo("FISICO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemTipo() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemLimite() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("FISICO");
    }

    public static CartaoDTO criarCartaoFisico7660TipoErrado() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("OUTRO")
                .limite("2.000,00");
    }

    public static CartaoDTO criarCartaoFisico7660SemNumeroVencimentoDiaMelhorCompraPortadorTipoELimite() {
        return new CartaoDTO();
    }
}
