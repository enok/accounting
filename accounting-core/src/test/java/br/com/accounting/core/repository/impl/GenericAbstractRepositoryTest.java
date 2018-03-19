package br.com.accounting.core.repository.impl;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.RepositoryException;
import org.junit.Test;

public class GenericAbstractRepositoryTest extends GenericTest {
    private GenericAbstractRepository genericAbstractRepository;

    public GenericAbstractRepositoryTest() {
        genericAbstractRepository = new GenericAbstractRepositoryMock();
    }

    @Test
    public void proximoCodigoNoiFoiPossivelLerAsLinhas() {
        genericAbstractRepository.proximoCodigo();
    }

    @Test(expected = RepositoryException.class)
    public void incrementarCodigoException() throws RepositoryException {
        genericAbstractRepository.incrementarCodigo(null);
    }

    @Test(expected = RepositoryException.class)
    public void salvarException() throws RepositoryException {
        genericAbstractRepository.salvar(null);
    }
}
