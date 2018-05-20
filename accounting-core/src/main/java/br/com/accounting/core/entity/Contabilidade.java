package br.com.accounting.core.entity;

import br.com.accounting.commons.entity.Cartao;
import br.com.accounting.commons.entity.Conta;
import br.com.accounting.commons.entity.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Contabilidade implements Entity, Serializable {
    private Long codigo;
    private LocalDate dataLancamento;
    private LocalDate dataAtualizacao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Boolean recorrente;
    private Grupo grupo;
    private Local local;
    private String descricao;
    private Boolean usouCartao;
    private Cartao cartao;
    private Boolean parcelado;
    private Parcelamento parcelamento;
    private Conta conta;
    private TipoContabilidade tipo;
    private Double valor;
    private Long codigoPai;
    private Long proximoLancamento;

    @Override
    public Long getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contabilidade that = (Contabilidade) o;

        Integer parcelasThis = (parcelamento == null) ? -1 : parcelamento.parcelas();
        Integer parcelasThat = (that.parcelamento == null) ? -1 : that.parcelamento.parcelas();

        return new EqualsBuilder()
                .append(dataVencimento, that.dataVencimento)
                .append(recorrente, that.recorrente)
                .append(grupo, that.grupo)
                .append(local, that.local)
                .append(descricao, that.descricao)
                .append(usouCartao, that.usouCartao)
                .append(cartao, that.cartao)
                .append(parcelado, that.parcelado)
                .append(parcelasThis, parcelasThat)
                .append(conta, that.conta)
                .append(tipo, that.tipo)
                .append(valor, that.valor)
                .isEquals();
    }
}
