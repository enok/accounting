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

    @Override
    public String getCodigo() {
        return codigo;
    }
}
