package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.entity.TipoCartao;

import java.text.ParseException;

import static br.com.accounting.core.util.Utils.*;

public class CartaoFactory {
    private static CartaoFactory factory;
    private Cartao entity;

    private CartaoFactory() {
        entity = new Cartao();
    }

    public static CartaoFactory begin() {
        factory = new CartaoFactory();
        return factory;
    }

    public Cartao build() {
        return entity;
    }

    public CartaoFactory withCodigo(String codigo) {
        if (!isBlankOrNull(codigo)) {
            entity.codigo(Long.parseLong(codigo));
        }
        return this;
    }

    public CartaoFactory withNumero(String numero) {
        if (!isBlankOrNull(numero)) {
            entity.numero(numero);
        }
        return this;
    }

    public CartaoFactory withVencimento(String vencimento) {
        if (!isBlankOrNull(vencimento)) {
            entity.vencimento(getDateFromString(vencimento));
        }
        return this;
    }

    public CartaoFactory withDiaMelhorCompra(String diaMelhorCompra) {
        if (!isBlankOrNull(diaMelhorCompra)) {
            entity.diaMelhorCompra(getDateFromString(diaMelhorCompra));
        }
        return this;
    }

    public CartaoFactory withPortador(String portador) {
        if (!isBlankOrNull(portador)) {
            entity.portador(portador);
        }
        return this;
    }

    public CartaoFactory withTipo(String tipo) {
        if (!isBlankOrNull(tipo)) {
            entity.tipo(TipoCartao.valueOf(tipo));
        }
        return this;
    }

    public CartaoFactory withLimite(String limite) throws ParseException {
        if (!isBlankOrNull(limite)) {
            entity.limite(getDoubleFromString(limite));
        }
        return this;
    }
}
