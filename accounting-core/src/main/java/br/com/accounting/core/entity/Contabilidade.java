package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.DATE_FORMATTER;
import static br.com.accounting.core.util.Utils.getDoubleFormatted;

public class Contabilidade implements Entity {
    private Long codigo;
    private LocalDate dataLancamento;
    private LocalDate vencimento;
    private TipoPagamento tipoPagamento;
    private SubTipoPagamento subTipoPagamento;
    private Tipo tipo;
    private Grupo grupo;
    private String descricao;
    private Parcelamento parcelamento;
    private Categoria categoria;
    private Double valor;
    private Status status;

    public Long getCodigo() {
        return codigo;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public String getDataLancamentoFormatada() {
        return getDataLancamento().format(DATE_FORMATTER);
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        return getVencimento().format(DATE_FORMATTER);
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public String getTipoPagamentoValue() {
        return tipoPagamento.toString();
    }

    public SubTipoPagamento getSubTipoPagamento() {
        return subTipoPagamento;
    }

    public String getSubTipoPagamentoDescricao() {
        if (subTipoPagamento == null) {
            return null;
        }
        return subTipoPagamento.getDescricao();
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getTipoValue() {
        return tipo.toString();
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public String getGrupoDescricao() {
        return grupo.getDescricao();
    }

    public String getSubGrupoDescricao() {
        return grupo.getSubGrupo().getDescricao();
    }

    public String getDescricao() {
        return descricao;
    }

    public Parcelamento getParcelamento() {
        return parcelamento;
    }

    public String getParcela() {
        if ((parcelamento == null) || (parcelamento.getParcela() == null)) {
            return null;
        }
        return parcelamento.getParcela().toString();
    }

    public String getParcelas() {
        if ((parcelamento == null) || (parcelamento.getParcelas() == null)) {
            return null;
        }
        return parcelamento.getParcelas().toString();
    }

    public String getParcelaCodigoPai() {
        if ((parcelamento == null) || (parcelamento.getCodigoPai() == null)) {
            return null;
        }
        return parcelamento.getCodigoPai().toString();
    }

    public Long getParcelamentoCodigoPai() {
        return parcelamento.getCodigoPai();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getCategoriaValue() {
        return categoria.toString();
    }

    public Double getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return getDoubleFormatted(valor);
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusValue() {
        return status.toString();
    }

    public Contabilidade withCodigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public Contabilidade withDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
        return this;
    }

    public Contabilidade withVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public Contabilidade withTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        return this;
    }

    public Contabilidade withSubTipoPagamento(SubTipoPagamento subTipoPagamento) {
        this.subTipoPagamento = subTipoPagamento;
        return this;
    }

    public Contabilidade withTipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public Contabilidade withGrupo(Grupo grupo) {
        this.grupo = grupo;
        return this;
    }

    public Contabilidade withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Contabilidade withParcelamento(Parcelamento parcelamento) {
        this.parcelamento = parcelamento;
        return this;
    }

    public Contabilidade withCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public Contabilidade withValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public Contabilidade withStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
