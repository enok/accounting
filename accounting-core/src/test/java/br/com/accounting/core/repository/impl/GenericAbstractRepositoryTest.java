package br.com.accounting.core.repository.impl;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.RepositoryException;
import org.junit.Test;

import java.io.IOException;

public class GenericAbstractRepositoryTest extends GenericTest {
    private GenericAbstractRepository genericAbstractRepository;

    public GenericAbstractRepositoryTest() {
        genericAbstractRepository = new GenericAbstractRepositoryMock();
    }

    @Test(expected = StoreException.class)
    public void proximoCodigoNaoFoiPossivelLerAsLinhas() throws StoreException {
        genericAbstractRepository.proximoCodigo();
    }

    @Test(expected = RepositoryException.class)
    public void incrementarCodigoException() throws StoreException, RepositoryException {
        genericAbstractRepository.incrementarCodigo(null);
    }

    @Test(expected = StoreException.class)
    public void incrementarCodigoExceptionSemDiretorio() throws IOException, StoreException, RepositoryException {
        deletarDiretorioEArquivos();
        genericAbstractRepository.incrementarCodigo(1L);
    }

    @Test(expected = StoreException.class)
    public void salvarException() throws StoreException {
        genericAbstractRepository.salvar(null);
    }

    @Test(expected = StoreException.class)
    public void deletarException() throws StoreException {
        genericAbstractRepository.deletar(null);
    }
}
