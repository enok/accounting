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

    public String getNumero() {
        return numero;
    }

    public String getVencimento() {
        return vencimento;
    }

    public String getDiaMelhorCompra() {
        return diaMelhorCompra;
    }

    public String getPortador() {
        return portador;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLimite() {
        return limite;
    }

    public CartaoDTO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
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
