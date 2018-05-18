package br.com.accounting.core.service;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.exception.ServiceException;
import br.com.accounting.commons.test.GenericTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;

public class ContaServiceTest extends GenericTest {
    @Autowired
    private ContaService contaService;

    @Test(expected = ServiceException.class)
    public void salvarException() throws StoreException, ServiceException {
        contaService.salvar(null);
    }

    @Test(expected = StoreException.class)
    public void buscarPorCodigoException() throws IOException, StoreException, ParseException {
        deletarDiretorioEArquivos();
        contaService.buscarPorCodigo(1L);
    }

    @Test(expected = ServiceException.class)
    public void atualizarSaldoException() throws ServiceException, StoreException {
        contaService.atualizarSaldo(null, null);
    }

    @Test(expected = ServiceException.class)
    public void deletarException() throws ServiceException, StoreException {
        contaService.deletar(null);
    }

    @Test(expected = StoreException.class)
    public void buscarCumulativasException() throws IOException, StoreException, ParseException {
        deletarDiretorioEArquivos();
        contaService.buscarCumulativas();
    }
}
