package br.com.accounting.core.service;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContaServiceTest extends GenericTest {
    @Autowired
    private ContaService contaService;

    @Test(expected = ServiceException.class)
    public void salvarTest() throws ServiceException {
        contaService.salvar(null);
    }
}
