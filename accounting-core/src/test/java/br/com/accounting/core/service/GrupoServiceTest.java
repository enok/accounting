package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GrupoServiceTest extends GenericTest {
    @Autowired
    private GrupoService service;

    @Test(expected = ServiceException.class)
    public void salvarException() throws ServiceException {
        try {
            service.salvar(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível salvar o grupo."));
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void atualizarException() throws ServiceException {
        try {
            service.atualizar(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o grupo."));
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void deletarException() throws ServiceException {
        try {
            service.deletar(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível deletar o grupo."));
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void buscarPorCodigoException() throws IOException, ServiceException {
        try {
            deletarDiretorioEArquivos();
            service.buscarPorCodigo(null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar o registro por código."));
            throw e;
        }
    }
}
