package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.TipoPagamento;
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

import static br.com.accounting.core.entity.TipoPagamento.CARTAO_CREDITO;
import static br.com.accounting.core.entity.TipoPagamento.CARTAO_DEBITO;
import static br.com.accounting.core.entity.TipoPagamento.DINHEIRO;
import static br.com.accounting.core.service.ServiceUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContabilidadeServiceFiltrarTest {

    @Autowired
    private ContabilidadeService contabilidadeService;

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
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento1Resultado() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento2Resultados() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("25/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento3Resultados() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

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
    public void filtrarRegistrosPorIntervaloDeVencimento1Resultado() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

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
    public void filtrarRegistrosBuscadosPorTipoDePagamentoDinheiro() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(DINHEIRO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeCredito() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(CARTAO_CREDITO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeDebito() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

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
    public void filtrarRegistrosPorTipoDePagamentoDinheiro() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento(DINHEIRO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
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
