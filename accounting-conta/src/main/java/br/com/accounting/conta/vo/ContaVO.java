package br.com.accounting.conta.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ContaVO {
    private String codigo;
    private String nome;
    private String descricao;
    private String valorDefault;
    private String saldo;
    private String cumulativo;
    private String dataAtualizacao;

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getValorDefault() {
        return valorDefault;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getCumulativo() {
        return cumulativo;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public ContaVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ContaVO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaVO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ContaVO setValorDefault(String valorDefault) {
        this.valorDefault = valorDefault;
        return this;
    }

    public ContaVO setSaldo(String saldo) {
        this.saldo = saldo;
        return this;
    }

    public ContaVO setCumulativo(String cumulativo) {
        this.cumulativo = cumulativo;
        return this;
    }

    public ContaVO setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }
}
