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
public class Conta implements Entity, Serializable {
    private Long codigo;
    private String nome;
    private String descricao;
    private Double saldo;
    private Boolean cumulativo;
    private LocalDate dataAtualizacao;

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

        Conta conta = (Conta) o;

        return new EqualsBuilder()
                .append(nome, conta.nome)
                .isEquals();
    }
}