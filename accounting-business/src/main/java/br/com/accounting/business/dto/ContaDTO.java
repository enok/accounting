package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static br.com.accounting.core.util.Utils.isBlankOrNull;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class ContaDTO implements EntityDTO {
    private String codigo;
    private String nome;
    private String descricao;
    private String valorDefault;
    private String valorDefaultAnterior;
    private String saldo;
    private String cumulativo;
    private String dataAtualizacao;
    private boolean atualizacao = false;

    public ContaDTO valorDefault(String valorDefault) {
        if (isBlankOrNull(this.valorDefault)) {
            this.valorDefault = valorDefault;
        }
        else {
            atualizacao = true;
            this.valorDefaultAnterior = this.valorDefault;
            this.valorDefault = valorDefault;
        }
        return this;
    }

    public ContaDTO saldo(String saldo) {
        this.saldo = saldo;
        return this;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }
}
