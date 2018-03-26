package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.entity.Tipo;

import java.text.ParseException;

import static br.com.accounting.core.util.Utils.*;

public class CartaoFactory {
    private static CartaoFactory cartaoFactory;
    private Cartao cartao;

    private CartaoFactory() {
        cartao = new Cartao();
    }

    public static CartaoFactory begin() {
        cartaoFactory = new CartaoFactory();
        return cartaoFactory;
    }

    public CartaoFactory withCodigo(String codigo) {
        cartao.codigo(Long.parseLong(codigo));
        return this;
    }

    public CartaoFactory withNumero(String numero) {
        if (!isBlank(numero)) {
            cartao.numero(numero);
        }
        return this;
    }

    public CartaoFactory withVencimento(String vencimento) {
        if (!isBlank(vencimento)) {
            cartao.vencimento(getDateFromString(vencimento));
        }
        return this;
    }

    public CartaoFactory withDiaMelhorCompra(String diaMelhorCompra) {
        if (!isBlank(diaMelhorCompra)) {
            cartao.diaMelhorCompra(getDateFromString(diaMelhorCompra));
        }
        return this;
    }

    public CartaoFactory withPortador(String portador) {
        if (!isBlank(portador)) {
            cartao.portador(portador);
        }
        return this;
    }

    public CartaoFactory withTipo(String tipo) {
        if (!isBlank(tipo)) {
            cartao.tipo(Tipo.valueOf(tipo));
        }
        return this;
    }

    public CartaoFactory withLimite(String limite) throws ParseException {
        if (!isBlank(limite)) {
            cartao.limite(createDouble(limite));
        }
        return this;
    }

    public Cartao build() {
        return cartao;
    }
}
