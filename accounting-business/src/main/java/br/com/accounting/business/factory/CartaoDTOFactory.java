package br.com.accounting.business.factory;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.entity.Tipo;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.getDoubleFormatted;
import static br.com.accounting.core.util.Utils.getStringFromDate;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class CartaoDTOFactory extends GenericDTOFactory<CartaoDTO, Cartao> {
    private static CartaoDTOFactory cartaoDTOFactory;

    private CartaoDTO cartaoDTO;

    private CartaoDTOFactory() {
        cartaoDTO = new CartaoDTO();
    }

    public static CartaoDTOFactory create() {
        return new CartaoDTOFactory();
    }

    @Override
    public CartaoDTOFactory begin() {
        cartaoDTOFactory = new CartaoDTOFactory();
        return cartaoDTOFactory;
    }

    @Override
    public CartaoDTOFactory preencherCampos(Cartao cartao) {
        if (cartao == null) {
            cartaoDTO = null;
            return this;
        }
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
        return cartaoDTO;
    }

    public CartaoDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            cartaoDTO.codigo(codigo.toString());
        }
        return this;
    }

    public CartaoDTOFactory withNumero(String numero) {
        if (!isBlank(numero)) {
            cartaoDTO.numero(numero);
        }
        return this;
    }

    public CartaoDTOFactory withVencimento(String vencimento) {
        if (!isBlank(vencimento)) {
            cartaoDTO.vencimento(vencimento);
        }
        return this;
    }

    public CartaoDTOFactory withVencimento(LocalDate vencimento) {
        return withVencimento(getStringFromDate(vencimento));
    }

    public CartaoDTOFactory withDiaMelhorCompra(String diaMelhorCompra) {
        if (!isBlank(diaMelhorCompra)) {
            cartaoDTO.diaMelhorCompra(diaMelhorCompra);
        }
        return this;
    }

    public CartaoDTOFactory withDiaMelhorCompra(LocalDate diaMelhorCompra) {
        return withDiaMelhorCompra(getStringFromDate(diaMelhorCompra));
    }

    public CartaoDTOFactory withPortador(String portador) {
        if (!isBlank(portador)) {
            cartaoDTO.portador(portador);
        }
        return this;
    }

    public CartaoDTOFactory withTipo(String tipo) {
        if (!isBlank(tipo)) {
            cartaoDTO.tipo(tipo);
        }
        return this;
    }

    public CartaoDTOFactory withTipo(Tipo tipo) {
        return withTipo(tipo.toString());
    }

    public CartaoDTOFactory withLimite(String limite) {
        if (!isBlank(limite)) {
            cartaoDTO.limite(limite);
        }
        return this;
    }

    public CartaoDTOFactory withLimite(Double limite) {
        return withLimite(getDoubleFormatted(limite));
    }
}
