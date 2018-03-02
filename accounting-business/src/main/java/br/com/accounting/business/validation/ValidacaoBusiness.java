package br.com.accounting.business.validation;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.InvalidBusinessException;
import br.com.accounting.core.util.Utils;

import java.text.ParseException;

import static br.com.accounting.core.util.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ValidacaoBusiness {

    private ValidacaoBusiness() {
    }

    public static void validaTipoDePagamentoComSubTipoPagamento(ContabilidadeDTO contabilidadeDTO, String subTipoPagamento) throws InvalidBusinessException {
        if (contabilidadeDTO.getTipoPagamento().equals(subTipoPagamento) && isBlank(contabilidadeDTO.getSubTipoPagamento())) {
            throw new InvalidBusinessException("Quando o TipoPagamento é " + subTipoPagamento + "o campo SubTipoPagamento é obrigatório");
        }
    }

    public static void validaTipoComParcelas(ContabilidadeDTO contabilidadeDTO) throws InvalidBusinessException {
        if (contabilidadeDTO.getTipo().equals("FIXO") && isBlank(contabilidadeDTO.getParcelas())) {
            throw new InvalidBusinessException("Quando o Tipo é FIXO o campo Parcelas é obrigatório");
        }
    }

    public static void validaTipoComParcelasComQuantidadeErrada(ContabilidadeDTO contabilidadeDTO) throws InvalidBusinessException {
        if (contabilidadeDTO.getTipo().equals("FIXO") && !contabilidadeDTO.getParcelas().equals("12")) {
            throw new InvalidBusinessException("Quando o Tipo é FIXO o campo Parcelas deve ter o valor 12");
        }
    }

    public static void validaCampoComValorNegativo(ContabilidadeDTO contabilidadeDTO) throws ParseException, InvalidBusinessException {
        if (createDouble(contabilidadeDTO.getValor()) < 0) {
            throw new InvalidBusinessException("O valor não pode ser negativo");
        }
    }

    public static void validaBusiness(ContabilidadeDTO contabilidadeDTO) throws InvalidBusinessException, ParseException {
        validaTipoDePagamentoComSubTipoPagamento(contabilidadeDTO, "CARTAO_CREDITO");
        validaTipoDePagamentoComSubTipoPagamento(contabilidadeDTO, "CARTAO_DEBITO");
        validaTipoComParcelas(contabilidadeDTO);
        validaTipoComParcelasComQuantidadeErrada(contabilidadeDTO);
        validaCampoComValorNegativo(contabilidadeDTO);
    }
}
