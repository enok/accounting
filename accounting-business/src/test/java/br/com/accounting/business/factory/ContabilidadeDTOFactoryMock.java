package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContabilidadeDTO;

public class ContabilidadeDTOFactoryMock {
    public static ContabilidadeDTO createContabilidadeDTO1() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withParcelas("12")
                .withCategoria("SAIDA")
                .withValor("26,90")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeDTO2() {
        return new ContabilidadeDTO()
                .withVencimento("27/02/2018")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("VARIAVEL")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("internet")
                .withCategoria("SAIDA")
                .withValor("174,98")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeDTO3() {
        return new ContabilidadeDTO()
                .withVencimento("15/01/2018")
                .withTipoPagamento("CARTAO_DEBITO")
                .withSubTipoPagamento("744")
                .withTipo("VARIAVEL")
                .withGrupo("MERCADO")
                .withSubGrupo("PADARIA")
                .withDescricao("pão")
                .withCategoria("SAIDA")
                .withValor("3,90")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeDTO4() {
        return new ContabilidadeDTO()
                .withVencimento("25/02/2018")
                .withTipoPagamento("DINHEIRO")
                .withSubTipoPagamento("transferencia")
                .withTipo("VARIAVEL")
                .withGrupo("RENDIMENTO")
                .withSubGrupo("SALARIO")
                .withDescricao("sysmap")
                .withCategoria("ENTRADA")
                .withValor("3.000,00")
                .withStatus("NAO_PAGO");
    }

    public static ContabilidadeDTO createContabilidadeDTO5() {
        return new ContabilidadeDTO()
                .withVencimento("28/02/2018")
                .withTipoPagamento("DINHEIRO")
                .withTipo("VARIAVEL")
                .withGrupo("SAUDE")
                .withSubGrupo("FARMACIA")
                .withDescricao("puran t4")
                .withCategoria("SAIDA")
                .withValor("20,00")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemVencimento() {
        return new ContabilidadeDTO();
    }

    public static ContabilidadeDTO createContabilidadeDTOSemTipoPagamento() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017");
    }

    public static ContabilidadeDTO createContabilidadeDTOComTipoPagamentoErrado() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO_2");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemTipo() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660");
    }

    public static ContabilidadeDTO createContabilidadeDTOComTipoErrado() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO_2");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemGrupo() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemSubGrupo() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemDescricao() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemCategoria() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify");
    }

    public static ContabilidadeDTO createContabilidadeDTOComCategoriaErrada() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA_2");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemValor() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA");
    }

    public static ContabilidadeDTO createContabilidadeDTOSemStatus() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA")
                .withValor("26,90");
    }

    public static ContabilidadeDTO createContabilidadeDTOComStatusErrado() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA")
                .withValor("26,90")
                .withStatus("PAGO_2");
    }

    public static ContabilidadeDTO createContabilidadeCartaoDeCreditoSemSubTipoPagamento() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA")
                .withValor("26,90")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeCartaoDeDebitoSemSubTipoPagamento() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_DEBITO")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA")
                .withValor("26,90")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeTipoFixoSemParcelas() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withCategoria("SAIDA")
                .withValor("26,90")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeTipoFixoSemParcelasDiferenteDe12() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withParcelas("11")
                .withCategoria("SAIDA")
                .withValor("26,90")
                .withStatus("PAGO");
    }

    public static ContabilidadeDTO createContabilidadeComValorNegativo() {
        return new ContabilidadeDTO()
                .withVencimento("27/12/2017")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("7660")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURA")
                .withDescricao("spotify")
                .withParcelas("12")
                .withCategoria("SAIDA")
                .withValor("-26,90")
                .withStatus("PAGO");
    }
}
