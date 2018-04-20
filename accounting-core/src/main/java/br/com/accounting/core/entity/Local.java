package br.com.accounting.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Local implements Entity {
    private Long codigo;
    private String nome;

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

        Local local = (Local) o;

        return new EqualsBuilder()
                .append(nome, local.nome)
                .isEquals();
    }
}