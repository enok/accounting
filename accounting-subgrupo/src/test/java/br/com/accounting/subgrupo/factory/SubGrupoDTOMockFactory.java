package br.com.accounting.subgrupo.factory;

import br.com.accounting.subgrupo.dto.SubGrupoDTO;
import br.com.accounting.subgrupo.factory.SubGrupoDTOFactory;

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

    public static SubGrupoDTO subGrupoAluguelSemNome() {
        return SubGrupoDTOFactory
                .create()
                .withDescricao("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio")
                .build();
    }

    public static SubGrupoDTO subGrupoAluguelSemDescricao() {
        return SubGrupoDTOFactory
                .create()
                .withNome("Aluguel")
                .build();
    }

    public static SubGrupoDTO subGrupoAluguelSemCamposObrigatorios() {
        return SubGrupoDTOFactory
                .create()
                .build();
    }

    public static SubGrupoDTO subGrupoInternet() {
        return SubGrupoDTOFactory
                .create()
                .withNome("Internet")
                .withDescricao("Serviço de internet fibra")
                .build();
    }
}
