package br.com.accounting.rest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ContabilidadeVO {
    private String codigo;
    private String dataLancamento;
    private String dataAtualizacao;
    private String dataVencimento;
    private String dataPagamento;
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
    private String proximoLancamento;

    public String getCodigo() {
        return codigo;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public String getRecorrente() {
        return recorrente;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getSubGrupo() {
        return subGrupo;
    }

    public String getLocal() {
        return local;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUsouCartao() {
        return usouCartao;
    }

    public String getCartao() {
        return cartao;
    }

    public String getParcelado() {
        return parcelado;
    }

    public String getParcela() {
        return parcela;
    }

    public String getParcelas() {
        return parcelas;
    }

    public String getConta() {
        return conta;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public String getCodigoPai() {
        return codigoPai;
    }

    public String getProximoLancamento() {
        return proximoLancamento;
    }

    public ContabilidadeVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ContabilidadeVO setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
        return this;
    }

    public ContabilidadeVO setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public ContabilidadeVO setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public ContabilidadeVO setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
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

    public ContabilidadeVO setProximoLancamento(String proximoLancamento) {
        this.proximoLancamento = proximoLancamento;
        return this;
    }
}
