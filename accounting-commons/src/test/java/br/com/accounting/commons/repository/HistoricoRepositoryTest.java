package br.com.accounting.commons.repository;

import br.com.accounting.commons.entity.Historico;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.factory.HistoricoFactory;
import br.com.accounting.commons.test.GenericTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public class HistoricoRepositoryTest extends GenericTest {
    @Autowired
    private HistoricoRepository historicoRepository;

    @Test
    public void buscarRegistros() throws StoreException, ParseException {
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
