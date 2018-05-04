package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class ContabilidadeDTO implements EntityDTO {
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

    @Override
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

    public ContabilidadeDTO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ContabilidadeDTO setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
        return this;
    }

    public ContabilidadeDTO setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public ContabilidadeDTO setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public ContabilidadeDTO setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public ContabilidadeDTO setRecorrente(String recorrente) {
        this.recorrente = recorrente;
        return this;
    }

    public ContabilidadeDTO setGrupo(String grupo) {
        this.grupo = grupo;
        return this;
    }

    public ContabilidadeDTO setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
        return this;
    }

    public ContabilidadeDTO setLocal(String local) {
        this.local = local;
        return this;
    }

    public ContabilidadeDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ContabilidadeDTO setUsouCartao(String usouCartao) {
        this.usouCartao = usouCartao;
        return this;
    }

    public ContabilidadeDTO setCartao(String cartao) {
        this.cartao = cartao;
        return this;
    }

    public ContabilidadeDTO setParcelado(String parcelado) {
        this.parcelado = parcelado;
        return this;
    }

    public ContabilidadeDTO setParcela(String parcela) {
        this.parcela = parcela;
        return this;
    }

    public ContabilidadeDTO setParcelas(String parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public ContabilidadeDTO setConta(String conta) {
        this.conta = conta;
        return this;
    }

    public ContabilidadeDTO setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContabilidadeDTO setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public ContabilidadeDTO setCodigoPai(String codigoPai) {
        this.codigoPai = codigoPai;
        return this;
    }

    public ContabilidadeDTO setProximoLancamento(String proximoLancamento) {
        this.proximoLancamento = proximoLancamento;
        return this;
    }
}
