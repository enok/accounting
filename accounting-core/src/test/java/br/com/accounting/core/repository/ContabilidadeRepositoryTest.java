package br.com.accounting.core.repository;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.RepositoryException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContabilidadeRepositoryTest extends GenericTest {

    @Autowired
    protected ContabilidadeRepository contabilidadeRepository;

    @Test(expected = RepositoryException.class)
    public void atualizar() throws RepositoryException {
        contabilidadeRepository.atualizar(null, null);
    }
}
