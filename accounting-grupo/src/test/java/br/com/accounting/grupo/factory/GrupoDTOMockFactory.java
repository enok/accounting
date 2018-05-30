package br.com.accounting.grupo.factory;

import br.com.accounting.commons.dto.GrupoDTO;

public final class GrupoDTOMockFactory {
    private GrupoDTOMockFactory() {
    }

    public static GrupoDTO grupoMoradia() {
        return GrupoDTOFactory
                .create()
                .withNome("Moradia")
                .withDescricao("Gastos gerais com moradia")
                .withSubGrupo("Aluguel")
                .withSubGrupo("Internet")
                .build();
    }

    public static GrupoDTO grupoMoradiaSemNome() {
        return GrupoDTOFactory
                .create()
                .withDescricao("Gastos gerais com moradia")
                .withSubGrupo("Aluguel")
                .withSubGrupo("Internet")
                .build();
    }

    public static GrupoDTO grupoMoradiaSemDescricao() {
        return GrupoDTOFactory
                .create()
                .withNome("Moradia")
                .withSubGrupo("Aluguel")
                .withSubGrupo("Internet")
                .build();
    }

    public static GrupoDTO grupoMoradiaSemGrupos() {
        return GrupoDTOFactory
                .create()
                .withNome("Moradia")
                .withDescricao("Gastos gerais com moradia")
                .build();
    }

    public static GrupoDTO grupoMoradiaSemCamposObrigatorios() {
        return GrupoDTOFactory
                .create()
                .build();
    }

    public static GrupoDTO grupoTransporte() {
        return GrupoDTOFactory
                .create()
                .withNome("Transporte")
                .withDescricao("Gastos gerais com transporte")
                .withSubGrupo("Combustível")
                .build();
    }
}
