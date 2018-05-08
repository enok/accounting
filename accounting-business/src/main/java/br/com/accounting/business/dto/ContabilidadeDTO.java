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

    public ContabilidadeDTO setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
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
}
