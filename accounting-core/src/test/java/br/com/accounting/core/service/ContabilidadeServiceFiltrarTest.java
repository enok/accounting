package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroSubTipoPagamento;
import br.com.accounting.core.filter.CampoFiltroTipoPagamento;
import br.com.accounting.core.filter.CampoFiltroVencimento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.TipoPagamento.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContabilidadeServiceFiltrarTest extends ContabilidadeGenericTest {
    @Autowired
    private ContabilidadeService contabilidadeService;

    @PostConstruct
    public void posConstrucao() {
        setContabilidadeService(contabilidadeService);
    }

    @Before
    public void setUp() throws IOException {
        deletarArquivosDoDiretorio();
    }

    @After
    public void after() throws IOException {
        deletarArquivosDoDiretorio();
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento1Resultado() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento2Resultados() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("25/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento3Resultados() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("01/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorIntervaloDeVencimento1Resultado() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(DINHEIRO);

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoDinheiro() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(DINHEIRO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeCredito() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(CARTAO_CREDITO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeDebito() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(CARTAO_DEBITO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_DEBITO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(DINHEIRO);

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorTipoDePagamentoDinheiro() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(DINHEIRO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorSubTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento("7660");

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamentoValorNulo() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento("7660");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento7660() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento("7660");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento744() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento("744");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorSubTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento("7660");

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorSubTipoDePagamento7660() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento("7660");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }
}
