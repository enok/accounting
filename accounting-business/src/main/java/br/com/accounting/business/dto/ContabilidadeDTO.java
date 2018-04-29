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

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setRecorrente(String recorrente) {
        this.recorrente = recorrente;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUsouCartao(String usouCartao) {
        this.usouCartao = usouCartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public void setParcelado(String parcelado) {
        this.parcelado = parcelado;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public void setParcelas(String parcelas) {
        this.parcelas = parcelas;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setCodigoPai(String codigoPai) {
        this.codigoPai = codigoPai;
    }

    public void setProximoLancamento(String proximoLancamento) {
        this.proximoLancamento = proximoLancamento;
    }
}
