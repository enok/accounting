package br.com.accounting.business.factory;

import br.com.accounting.business.dto.SubGrupoDTO;

public final class SubGrupoDTOMockFactory {
    private SubGrupoDTOMockFactory() {
    }

    public static SubGrupoDTO subGrupoAluguel() {
        return SubGrupoDTOFactory
                .create()
                .withNome("Aluguel")
                .withDescricao("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio")
                .build();
    }
}
