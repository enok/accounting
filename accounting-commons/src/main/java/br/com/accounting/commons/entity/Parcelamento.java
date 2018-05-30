package br.com.accounting.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Parcelamento implements Serializable {
    private Integer parcela;
    private Integer parcelas;
}
