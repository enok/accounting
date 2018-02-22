package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubGrupo;

public class SubGrupoFactoryMock {
    public static SubGrupo createAssinaturas() {
        return SubGrupoFactory
                .begin()
                .withDescricao("ASSINATURAS")
                .build();
    }

    public static SubGrupo createAssinaturas_2() {
        return SubGrupoFactory
                .begin()
                .withDescricao("ASSINATURAS")
                .build();
    }

    public static SubGrupo createPadaria() {
        return SubGrupoFactory
                .begin()
                .withDescricao("PADARIA")
                .build();
    }
}
