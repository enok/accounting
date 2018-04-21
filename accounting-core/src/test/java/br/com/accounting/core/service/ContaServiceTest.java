package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ContaServiceTest extends GenericTest {
    @Autowired
    private ContaService contaService;

    @Test(expected = ServiceException.class)
    public void salvarException() throws ServiceException {
        contaService.salvar(null);
    }

    @Test(expected = ServiceException.class)
    public void buscarPorCodigoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();
        contaService.buscarPorCodigo(null);
    }

    @Test(expected = ServiceException.class)
    public void atualizarSaldoException() throws ServiceException {
        contaService.atualizarSaldo(null, null);
    }

    @Test(expected = ServiceException.class)
    public void deletarException() throws ServiceException {
        contaService.deletar(null);
    }

    @Test(expected = ServiceException.class)
    public void buscarCumulativasException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();
        contaService.buscarCumulativas();
    }
}
