package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Grupo;

public class GrupoFactoryMock {
    public static Grupo createMoradiaAssinatura() {
        return GrupoFactory
                .begin()
                .withDescricao("MORADIA")
                .withSubGrupo("1", "ASSINATURA")
                .build();
    }

    public static Grupo createMoradiaAluguel() {
        return GrupoFactory
                .begin()
                .withDescricao("MORADIA")
                .withSubGrupo("2", "ALUGUEL")
                .build();
    }

    public static Grupo createMercadoPadaria() {
        return GrupoFactory
                .begin()
                .withDescricao("MERCADO")
                .withSubGrupo("3", "PADARIA")
                .build();
    }
}
