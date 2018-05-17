package br.com.accounting.business.factory;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.entity.TipoCartao;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;

public final class CartaoDTOFactory extends GenericDTOFactory<CartaoDTO, Cartao> {
    private static CartaoDTOFactory factory;

    private CartaoDTO dto;

    private CartaoDTOFactory() {
        dto = new CartaoDTO();
    }

    public static CartaoDTOFactory create() {
        return new CartaoDTOFactory();
    }

    @Override
    public CartaoDTOFactory begin() {
        factory = new CartaoDTOFactory();
        return factory;
    }

    @Override
    public CartaoDTOFactory preencherCampos(Cartao cartao) {
        withCodigo(cartao.codigo());
        withNumero(cartao.numero());
        withVencimento(cartao.vencimento());
        withDiaMelhorCompra(cartao.diaMelhorCompra());
        withPortador(cartao.portador());
        withTipo(cartao.tipo());
        withLimite(cartao.limite());
        return this;
    }

    @Override
    public CartaoDTO build() {
        return dto;
    }

    public CartaoDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            dto.codigo(codigo.toString());
        }
        return this;
    }

    public CartaoDTOFactory withNumero(String numero) {
        if (!isBlankOrNull(numero)) {
            dto.numero(numero);
        }
        return this;
    }

    public CartaoDTOFactory withVencimento(String vencimento) {
        if (!isBlankOrNull(vencimento)) {
            dto.vencimento(vencimento);
        }
        return this;
    }

    public CartaoDTOFactory withVencimento(LocalDate vencimento) {
        return withVencimento(getStringFromDate(vencimento));
    }

    public CartaoDTOFactory withDiaMelhorCompra(String diaMelhorCompra) {
        if (!isBlankOrNull(diaMelhorCompra)) {
            dto.diaMelhorCompra(diaMelhorCompra);
        }
        return this;
    }

    public CartaoDTOFactory withDiaMelhorCompra(LocalDate diaMelhorCompra) {
        return withDiaMelhorCompra(getStringFromDate(diaMelhorCompra));
    }

    public CartaoDTOFactory withPortador(String portador) {
        if (!isBlankOrNull(portador)) {
            dto.portador(portador);
        }
        return this;
    }

    public CartaoDTOFactory withTipo(String tipo) {
        if (!isBlankOrNull(tipo)) {
            dto.tipo(tipo);
        }
        return this;
    }

    public CartaoDTOFactory withTipo(TipoCartao tipoCartao) {
        return withTipo(tipoCartao.toString());
    }

    public CartaoDTOFactory withLimite(String limite) {
        if (!isBlankOrNull(limite)) {
            dto.limite(limite);
        }
        return this;
    }

    public CartaoDTOFactory withLimite(Double limite) {
        return withLimite(getStringFromDouble(limite));
    }
}
