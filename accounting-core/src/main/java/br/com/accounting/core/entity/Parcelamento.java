package br.com.accounting.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Parcelamento {
    private Integer parcela;
    private Integer parcelas;
}
