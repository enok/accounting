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

    @Override
    public String getCodigo() {
        return codigo;
    }

    public ContaDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ContaDTO setValorDefault(String valorDefault) {
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

    public ContaDTO setCumulativo(String cumulativo) {
        this.cumulativo = cumulativo;
        return this;
    }

    public ContaDTO setAtualizacao(boolean atualizacao) {
        this.atualizacao = atualizacao;
        return this;
    }
}
