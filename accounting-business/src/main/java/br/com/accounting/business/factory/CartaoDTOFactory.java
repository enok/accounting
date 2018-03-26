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

    public CartaoDTOFactory withVencimento(LocalDate vencimento) {
        if (vencimento != null) {
            cartaoDTO.vencimento(getStringFromDate(vencimento));
        }
        return this;
    }

    public CartaoDTOFactory withDiaMelhorCompra(LocalDate diaMelhorCompra) {
        if (diaMelhorCompra != null) {
            cartaoDTO.diaMelhorCompra(getStringFromDate(diaMelhorCompra));
        }
        return this;
    }

    public CartaoDTOFactory withPortador(String portador) {
        if (!isBlank(portador)) {
            cartaoDTO.portador(portador);
        }
        return this;
    }

    public CartaoDTOFactory withTipo(Tipo tipo) {
        if (tipo != null) {
            cartaoDTO.tipo(tipo.toString());
        }
        return this;
    }

    public CartaoDTOFactory withLimite(Double limite) {
        if (limite != null) {
            cartaoDTO.limite(getDoubleFormatted(limite));
        }
        return this;
    }
}
