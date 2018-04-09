package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class CartaoDTO implements EntityDTO {
    private String codigo;
    private String numero;
    private String vencimento;
    private String diaMelhorCompra;
    private String portador;
    private String tipo;
    private String limite;

    @Override
    public String getCodigo() {
        return codigo;
    }
}
