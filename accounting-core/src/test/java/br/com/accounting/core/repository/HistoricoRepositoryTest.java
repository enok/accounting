package br.com.accounting.core.repository;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.entity.Historico;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.HistoricoFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HistoricoRepositoryTest extends GenericTest {
    @Autowired
    private HistoricoRepository historicoRepository;

    @Test
    public void buscarRegistros() throws StoreException, RepositoryException {
        criarHistorico();
        historicoRepository.buscarRegistros();
    }

    private void criarHistorico() throws StoreException {
        Historico historico = HistoricoFactory
                .begin()
                .withCodigo("1")
                .withMetodo("salvar()")
                .withParametro("codigo", "123")
                .build();
        historicoRepository.salvar(historico);
    }
}
