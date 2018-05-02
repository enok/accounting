package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ContaServiceTest extends GenericTest {
    @Autowired
    private ContaService contaService;

    @Test(expected = ServiceException.class)
    public void salvarException() throws StoreException, ServiceException {
        contaService.salvar(null);
    }

    @Test(expected = StoreException.class)
    public void buscarPorCodigoException() throws IOException, ServiceException, StoreException {
        deletarDiretorioEArquivos();
        contaService.buscarPorCodigo(null);
    }

    @Test(expected = ServiceException.class)
    public void atualizarSaldoException() throws ServiceException {
        contaService.atualizarSaldo(null, null);
    }

    @Test(expected = ServiceException.class)
    public void deletarException() throws ServiceException, StoreException {
        contaService.deletar(null);
    }

    @Test(expected = StoreException.class)
    public void buscarCumulativasException() throws IOException, ServiceException, StoreException {
        deletarDiretorioEArquivos();
        contaService.buscarCumulativas();
    }
}
