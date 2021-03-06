package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CartaoServiceTest extends GenericTest {
    @Autowired
    private CartaoService cartaoService;

    @Test(expected = ServiceException.class)
    public void salvarException() throws StoreException, ServiceException {
        try {
            cartaoService.salvar(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível salvar."));
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void atualizarException() throws ServiceException, StoreException {
        try {
            cartaoService.atualizar(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void deletarException() throws ServiceException, StoreException {
        try {
            cartaoService.deletar(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível deletar."));
            throw e;
        }
    }
}
