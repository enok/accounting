package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HistoricoServiceTest extends GenericTest {
    @Autowired
    private HistoricoService historicoService;

    @Test(expected = ServiceException.class)
    public void salvarException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();
        try {
            historicoService.salvar(null, null);
        }
        catch (ServiceException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível salvar o histórico."));
            throw e;
        }
    }
}
