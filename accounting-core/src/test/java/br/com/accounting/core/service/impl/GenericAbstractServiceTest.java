package br.com.accounting.core.service.impl;

import br.com.accounting.core.GenericTest;
import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.CartaoFactory;
import br.com.accounting.core.repository.CartaoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;

public class GenericAbstractServiceTest extends GenericTest {
    @Autowired
    private CartaoRepository cartaoRepository;

    private GenericAbstractService genericAbstractService;

    @Override
    public void setUp() throws IOException {
        super.setUp();
        genericAbstractService = new GenericAbstractServiceMock(cartaoRepository);
    }

    @Test(expected = StoreException.class)
    public void salvarException() throws IOException, StoreException, ServiceException {
        deletarDiretorioEArquivos();
        Cartao cartao = new Cartao();
        genericAbstractService.salvar(cartao);
    }

    @Test(expected = StoreException.class)
    public void deletarException() throws IOException, StoreException, ServiceException, ParseException {
        deletarDiretorioEArquivos();
        Cartao cartao = CartaoFactory
                .begin()
                .withNumero("0744")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Carol")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
        genericAbstractService.deletar(cartao);
    }

    @Test(expected = StoreException.class)
    public void buscarPorCodigoException() throws IOException, StoreException, ParseException {
        deletarDiretorioEArquivos();
        genericAbstractService.buscarPorCodigo(null);
    }

    @Test(expected = StoreException.class)
    public void buscarTodasException() throws IOException, StoreException, ParseException {
        deletarDiretorioEArquivos();
        genericAbstractService.buscarTodas();
    }
}
