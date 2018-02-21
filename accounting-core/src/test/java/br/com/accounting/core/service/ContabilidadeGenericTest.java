package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContabilidadeFactoryMock;

import java.util.List;

public abstract class ContabilidadeGenericTest extends GenericTest {
    private ContabilidadeService contabilidadeService;

    protected List<Contabilidade> getContabilidades() {
        List<Contabilidade> registros = null;
        try {
            registros = contabilidadeService.buscarRegistros();
        } catch (ServiceException e) {
        }
        return registros;
    }

    protected void criarVariasContabilidades() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito744();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito7660();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createDinheiro();
        contabilidadeService.salvar(contabilidade);
    }

    protected void criarVariasContabilidades2() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito744();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoCredito7660();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito7660();
        contabilidadeService.salvar(contabilidade);
    }

    protected void setContabilidadeService(ContabilidadeService contabilidadeService) {
        this.contabilidadeService = contabilidadeService;
    }
}
