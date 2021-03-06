package br.com.accounting.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Cartao implements Entity, Serializable {
    private Long codigo;
    private String numero;
    private LocalDate vencimento;
    private LocalDate diaMelhorCompra;
    private String portador;
    private TipoCartao tipo;
    private Double limite;

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

        Cartao cartao = (Cartao) o;

        return new EqualsBuilder()
                .append(numero, cartao.numero)
                .isEquals();
    }
}
