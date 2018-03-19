package br.com.accounting.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Conta implements Cloneable {
    private Long codigo;
    private String nome;
    private String descricao;
    private Double saldo;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Conta conta = (Conta) o;

        return new EqualsBuilder()
                .append(nome, conta.nome)
                .append(descricao, conta.descricao)
                .isEquals();
    }

    @Override
    public Conta clone() throws CloneNotSupportedException {
        return (Conta) super.clone();
    }
}