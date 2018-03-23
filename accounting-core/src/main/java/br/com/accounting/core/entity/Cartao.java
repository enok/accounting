package br.com.accounting.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Cartao implements Entity, Cloneable {
    private Long codigo;
    private String numero;
    private LocalDate vencimento;
    private LocalDate diaMelhorCompra;
    private String portador;
    private Tipo tipo;
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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cartao cartao = (Cartao) o;

        return new EqualsBuilder()
                .append(numero, cartao.numero)
                .isEquals();
    }

    @Override
    public Conta clone() throws CloneNotSupportedException {
        return (Conta) super.clone();
    }
}
