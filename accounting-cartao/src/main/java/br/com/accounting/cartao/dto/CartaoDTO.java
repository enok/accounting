package br.com.accounting.cartao.dto;

import br.com.accounting.commons.dto.EntityDTO;
import lombok.Data;
import lombok.experimental.Accessors;

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
