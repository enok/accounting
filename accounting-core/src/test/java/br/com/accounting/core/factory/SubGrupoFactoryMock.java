package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubGrupo;

public class SubGrupoFactoryMock {
    public static SubGrupo createAssinatura() {
        return SubGrupoFactory
                .begin()
                .withDescricao("ASSINATURA")
                .build();
    }

    public static SubGrupo createAssinatura_2() {
        return SubGrupoFactory
                .begin()
                .withDescricao("ASSINATURA")
                .build();
    }

    public static SubGrupo createPadaria() {
        return SubGrupoFactory
                .begin()
                .withDescricao("PADARIA")
                .build();
    }
}
