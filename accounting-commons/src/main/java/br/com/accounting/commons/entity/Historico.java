package br.com.accounting.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Historico {
    private Long codigo;
    private String metodo;
    private Map<String, Object> parametros;
}
