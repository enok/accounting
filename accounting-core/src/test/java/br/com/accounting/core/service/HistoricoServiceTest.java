package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HistoricoServiceTest extends GenericTest {
    @Autowired
    private HistoricoService historicoService;

    @Test(expected = StoreException.class)
    public void salvarException() throws IOException, StoreException, RepositoryException {
        deletarDiretorioEArquivos();
        try {
            historicoService.salvar(null, null);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível ler as linhas do arquivo: D:\\tmp\\arquivos\\historico-contagem.txt"));
            throw e;
        }
    }
}
