package br.com.accounting.cartao.factory;

import br.com.accounting.cartao.dto.CartaoDTO;

public final class CartaoDTOMockFactory {
    private CartaoDTOMockFactory() {
    }

    public static CartaoDTO criarCartaoFisico7660() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico0744() {
        return CartaoDTOFactory
                .create()
                .withNumero("0744")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Carol")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoVirtual7339() {
        return CartaoDTOFactory
                .create()
                .withNumero("7339")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withTipo("DIGITAL")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemNumero() {
        return CartaoDTOFactory
                .create()
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemVencimento() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemDiaMelhorCompra() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withVencimento("27/03/2018")
                .withPortador("Enok")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemPortador() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemTipo() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemLimite() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withTipo("FISICO")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660TipoErrado() {
        return CartaoDTOFactory
                .create()
                .withNumero("7660")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Enok")
                .withTipo("OUTRO")
                .withLimite("2.000,00")
                .build();
    }

    public static CartaoDTO criarCartaoFisico7660SemCamposObrigatorios() {
        return new CartaoDTO();
    }
}
