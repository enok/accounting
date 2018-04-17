package br.com.accounting.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Contabilidade implements Entity {
    private Long codigo;
    private LocalDate dataLancamento;
    private LocalDate dataAtualizacao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Boolean recorrente;
    private Grupo grupo;
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
}
