package br.com.accounting.rest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ContaTransferenciaVO {
    private ContaVO origem;
    private ContaVO destino;
    private Double valor;

    public ContaVO getOrigem() {
        return origem;
    }

    public ContaVO getDestino() {
        return destino;
    }

    public Double getValor() {
        return valor;
    }

    public ContaTransferenciaVO setOrigem(ContaVO origem) {
        this.origem = origem;
        return this;
    }

    public ContaTransferenciaVO setDestino(ContaVO destino) {
        this.destino = destino;
        return this;
    }

    public ContaTransferenciaVO setValor(Double valor) {
        this.valor = valor;
        return this;
    }
}
