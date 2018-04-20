package br.com.accounting.business.factory;

import br.com.accounting.business.dto.LocalDTO;

public final class LocalDTOMockFactory {
    private LocalDTOMockFactory() {
    }

    public static LocalDTO localDTOCarrefour() {
        return LocalDTOFactory
                .create()
                .withNome("Carrefour")
                .build();
    }

    public static LocalDTO localAmericanas() {
        return LocalDTOFactory
                .create()
                .withNome("Americanas")
                .build();
    }

    public static LocalDTO localDTOCarrefourSemNome() {
        return LocalDTOFactory
                .create()
                .build();
    }
}
