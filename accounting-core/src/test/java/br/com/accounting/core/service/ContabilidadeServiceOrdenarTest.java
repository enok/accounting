package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContabilidadeFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroTipoPagamento;
import br.com.accounting.core.filter.CampoFiltroVencimento;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Order.ASC;
import static br.com.accounting.core.entity.Order.DESC;
import static br.com.accounting.core.entity.TipoPagamento.CARTAO_CREDITO;
import static br.com.accounting.core.entity.TipoPagamento.CARTAO_DEBITO;
import static br.com.accounting.core.entity.TipoPagamento.DINHEIRO;
import static br.com.accounting.core.service.ServiceUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContabilidadeServiceOrdenarTest {

    @Autowired
    private ContabilidadeService contabilidadeService;

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendenteException() throws ServiceException, IOException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        try {
            contabilidadeService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("27/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoAscendente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("27/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescenteException() throws ServiceException, IOException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        try {
            contabilidadeService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("15/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoDescendente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("15/01/2018"));
    }


    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoAscendenteException() throws ServiceException, IOException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoAscendente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoAscendente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoDecrescenteException() throws ServiceException, IOException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoDecrescente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoDescendente() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    private List<Contabilidade> getContabilidades() {

        List<Contabilidade> registros = null;
        try {
            registros = contabilidadeService.buscarRegistros();
        } catch (ServiceException e) {
        }
        return registros;
    }

    private void criarVariasContabilidades() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createDinheiro();
        contabilidadeService.salvar(contabilidade);
    }
}
