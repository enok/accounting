package br.com.accounting.commons.service.impl;

import br.com.accounting.commons.entity.Cartao;
import br.com.accounting.commons.entity.TipoCartao;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.ServiceException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.repository.GenericRepository;
import br.com.accounting.commons.repository.impl.GenericAbstractRepository;
import br.com.accounting.commons.test.GenericTest;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static br.com.accounting.commons.util.Utils.getDateFromString;

public class GenericAbstractServiceTest extends GenericTest {
    private GenericRepository repository;

    private GenericAbstractService genericAbstractService;

    @Override
    public void setUp() throws IOException, StoreException, BusinessException, GenericException {
        super.setUp();
        repository = new GenericRepositoryImpl();
        genericAbstractService = new GenericAbstractServiceMock(repository);
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
        Cartao cartao = new Cartao()
                .numero("0744")
                .vencimento(getDateFromString("27/03/2018"))
                .diaMelhorCompra(getDateFromString("17/04/2018"))
                .diaMelhorCompra(getDateFromString("17/04/2018"))
                .portador("Carol")
                .tipo(TipoCartao.valueOf("FISICO"))
                .limite(2000.00);
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

    class GenericRepositoryImpl extends GenericAbstractRepository {

        @Override
        public String getArquivo() {
            return null;
        }

        @Override
        public String getArquivoContagem() {
            return null;
        }

        @Override
        public String criarLinha(Object entity) {
            return null;
        }

        @Override
        public Object criarEntity(String linha) throws ParseException {
            return null;
        }
    }
}
