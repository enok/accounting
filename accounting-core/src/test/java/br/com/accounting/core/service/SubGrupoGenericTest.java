package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubGrupoFactoryMock;

import java.util.List;

public abstract class SubGrupoGenericTest extends GenericTest {
    private SubGrupoService subGrupoService;

    public void setSubGrupoService(SubGrupoService subGrupoService) {
        this.subGrupoService = subGrupoService;
    }

    protected List<SubGrupo> getSubGrupos() {
        List<SubGrupo> registros = null;
        try {
            registros = subGrupoService.buscarRegistros();
        } catch (ServiceException e) {
        }
        return registros;
    }

    protected void criarVariosSubGrupos() throws ServiceException {
        SubGrupo grupo = SubGrupoFactoryMock.createAssinaturas();
        subGrupoService.salvar(grupo);

        grupo = SubGrupoFactoryMock.createAssinaturas_2();
        subGrupoService.salvar(grupo);

        grupo = SubGrupoFactoryMock.createPadaria();
        subGrupoService.salvar(grupo);
    }
}
