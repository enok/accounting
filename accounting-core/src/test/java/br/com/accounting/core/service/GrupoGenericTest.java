package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactoryMock;

import java.util.List;

public abstract class GrupoGenericTest extends GenericTest {
    private GrupoService grupoService;

    public void setGrupoService(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    protected List<Grupo> getGrupos() {
        List<Grupo> registros = null;
        try {
            registros = grupoService.buscarRegistros();
        } catch (ServiceException e) {
        }
        return registros;
    }

    protected void criarVariosGrupos() throws ServiceException {
        Grupo grupo = GrupoFactoryMock.createMoradiaAssinatura();
        grupoService.salvar(grupo);

        grupo = GrupoFactoryMock.createMoradiaAssinatura();
        grupoService.salvar(grupo);

        grupo = GrupoFactoryMock.createMoradiaAluguel();
        grupoService.salvar(grupo);

        grupo = GrupoFactoryMock.createMercadoPadaria();
        grupoService.salvar(grupo);
    }
}
