package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class CartaoDTO implements EntityDTO {
    private String codigo;
    private String numero;
    private String vencimento;
    private String diaMelhorCompra;
    private String portador;
    private String tipo;
    private String limite;

    @Override
    public String getCodigo() {
        return codigo;
    }

    public CartaoDTO setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public CartaoDTO setVencimento(String vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public CartaoDTO setDiaMelhorCompra(String diaMelhorCompra) {
        this.diaMelhorCompra = diaMelhorCompra;
        return this;
    }

    public CartaoDTO setPortador(String portador) {
        this.portador = portador;
        return this;
    }

    public CartaoDTO setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public CartaoDTO setLimite(String limite) {
        this.limite = limite;
        return this;
    }
}
