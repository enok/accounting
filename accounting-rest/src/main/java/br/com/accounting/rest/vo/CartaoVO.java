package br.com.accounting.rest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class CartaoVO {
    private String codigo;
    private String numero;
    private String vencimento;
    private String diaMelhorCompra;
    private String portador;
    private String tipo;
    private String limite;

    public CartaoVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public CartaoVO setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public CartaoVO setVencimento(String vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public CartaoVO setDiaMelhorCompra(String diaMelhorCompra) {
        this.diaMelhorCompra = diaMelhorCompra;
        return this;
    }

    public CartaoVO setPortador(String portador) {
        this.portador = portador;
        return this;
    }

    public CartaoVO setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public CartaoVO setLimite(String limite) {
        this.limite = limite;
        return this;
    }
}
