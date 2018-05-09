package br.com.accounting.rest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ContabilidadeVO {
    private String codigo;
    private String dataLancamento;
    private String dataVencimento;
    private String recorrente;
    private String grupo;
    private String subGrupo;
    private String local;
    private String descricao;
    private String usouCartao;
    private String cartao;
    private String parcelado;
    private String parcela;
    private String parcelas;
    private String conta;
    private String tipo;
    private String valor;
    private String codigoPai;

    public ContabilidadeVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ContabilidadeVO setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
        return this;
    }

    public ContabilidadeVO setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public ContabilidadeVO setRecorrente(String recorrente) {
        this.recorrente = recorrente;
        return this;
    }

    public ContabilidadeVO setGrupo(String grupo) {
        this.grupo = grupo;
        return this;
    }

    public ContabilidadeVO setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
        return this;
    }

    public ContabilidadeVO setLocal(String local) {
        this.local = local;
        return this;
    }

    public ContabilidadeVO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ContabilidadeVO setUsouCartao(String usouCartao) {
        this.usouCartao = usouCartao;
        return this;
    }

    public ContabilidadeVO setCartao(String cartao) {
        this.cartao = cartao;
        return this;
    }

    public ContabilidadeVO setParcelado(String parcelado) {
        this.parcelado = parcelado;
        return this;
    }

    public ContabilidadeVO setParcela(String parcela) {
        this.parcela = parcela;
        return this;
    }

    public ContabilidadeVO setParcelas(String parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public ContabilidadeVO setConta(String conta) {
        this.conta = conta;
        return this;
    }

    public ContabilidadeVO setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContabilidadeVO setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public ContabilidadeVO setCodigoPai(String codigoPai) {
        this.codigoPai = codigoPai;
        return this;
    }
}
