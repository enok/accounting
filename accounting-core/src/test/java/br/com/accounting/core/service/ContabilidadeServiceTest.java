package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContabilidadeFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroVencimento;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static br.com.accounting.core.service.ServiceUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContabilidadeServiceTest {

    @Autowired
    private ContabilidadeService contabilidadeService;

    @Test
    public void salvarContabilidadeCartaoCredito() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();

        assertThat(contabilidade, notNullValue());
        assertThat(contabilidade.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidade.getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(contabilidade.getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(contabilidade.getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(contabilidade.getTipo(), equalTo(FIXO));
        assertThat(contabilidade.getGrupo(), equalTo("MORADIA"));
        assertThat(contabilidade.getSubGrupo(), equalTo("ASSINATURAS"));
        assertThat(contabilidade.getDescricao(), equalTo("spotify"));
        assertThat(contabilidade.getParcelamento().getParcela(), equalTo(1));
        assertThat(contabilidade.getParcelamento().getParcelas(), equalTo(12));
        assertThat(contabilidade.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidade.getValor(), equalTo(26.90));
        assertThat(contabilidade.getStatus(), equalTo(PAGO));

        contabilidadeService.salvar(contabilidade);

        assertThat(contabilidade.getCodigo(), notNullValue());
    }

    @Test
    public void salvarContabilidadeCartaoDebito() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoDebito();

        assertThat(contabilidade, notNullValue());
        assertThat(contabilidade.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidade.getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(contabilidade.getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(contabilidade.getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(contabilidade.getTipo(), equalTo(VARIAVEL));
        assertThat(contabilidade.getGrupo(), equalTo("MERCADO"));
        assertThat(contabilidade.getSubGrupo(), equalTo("PADARIA"));
        assertThat(contabilidade.getDescricao(), equalTo("pão"));
        assertThat(contabilidade.getParcelamento(), nullValue());
        assertThat(contabilidade.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidade.getValor(), equalTo(18.0));
        assertThat(contabilidade.getStatus(), equalTo(PAGO));

        contabilidadeService.salvar(contabilidade);

        assertThat(contabilidade.getCodigo(), notNullValue());
    }

    @Test
    public void salvarContabilidadeDinheiro() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createDinheiro();

        assertThat(contabilidade, notNullValue());
        assertThat(contabilidade.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidade.getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(contabilidade.getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(contabilidade.getSubTipoPagamento(), nullValue());
        assertThat(contabilidade.getTipo(), equalTo(FIXO));
        assertThat(contabilidade.getGrupo(), equalTo("RENDIMENTOS"));
        assertThat(contabilidade.getSubGrupo(), equalTo("SALARIO"));
        assertThat(contabilidade.getDescricao(), equalTo("sysmap"));
        assertThat(contabilidade.getParcelamento(), nullValue());
        assertThat(contabilidade.getCategoria(), equalTo(ENTRADA));
        assertThat(contabilidade.getValor(), equalTo(5000.0));
        assertThat(contabilidade.getStatus(), equalTo(NAO_PAGO));

        contabilidadeService.salvar(contabilidade);

        assertThat(contabilidade.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarContabilidadeServiceException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        Contabilidade registro = ContabilidadeFactoryMock.createDinheiro();

        try {
            contabilidadeService.salvar(registro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void buscarRegistrosContabilidade() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();

        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();
        assertThat(registros, notNullValue());

        Contabilidade contabilidadeBuscada = registros.get(0);

        assertThat(contabilidadeBuscada, notNullValue());
        assertThat(contabilidadeBuscada.getCodigo(), notNullValue());
        assertThat(contabilidadeBuscada.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidadeBuscada.getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(contabilidadeBuscada.getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(contabilidadeBuscada.getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(contabilidadeBuscada.getTipo(), equalTo(FIXO));
        assertThat(contabilidadeBuscada.getGrupo(), equalTo("MORADIA"));
        assertThat(contabilidadeBuscada.getSubGrupo(), equalTo("ASSINATURAS"));
        assertThat(contabilidadeBuscada.getDescricao(), equalTo("spotify"));
        assertThat(contabilidadeBuscada.getParcelamento().getParcela(), equalTo(1));
        assertThat(contabilidadeBuscada.getParcelamento().getParcelas(), equalTo(12));
        assertThat(contabilidadeBuscada.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidadeBuscada.getValor(), equalTo(26.90));
        assertThat(contabilidadeBuscada.getStatus(), equalTo(PAGO));
    }

    @Test
    public void buscarRegistrosContabilidadeSemParcelamento() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoDebito();

        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();
        assertThat(registros, notNullValue());

        Contabilidade contabilidadeBuscada = registros.get(0);

        assertThat(contabilidadeBuscada, notNullValue());
        assertThat(contabilidadeBuscada.getCodigo(), notNullValue());
        assertThat(contabilidadeBuscada.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidadeBuscada.getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(contabilidadeBuscada.getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(contabilidadeBuscada.getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(contabilidadeBuscada.getTipo(), equalTo(VARIAVEL));
        assertThat(contabilidadeBuscada.getGrupo(), equalTo("MERCADO"));
        assertThat(contabilidadeBuscada.getSubGrupo(), equalTo("PADARIA"));
        assertThat(contabilidadeBuscada.getDescricao(), equalTo("pão"));
        assertThat(contabilidadeBuscada.getParcelamento(), nullValue());
        assertThat(contabilidadeBuscada.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidadeBuscada.getValor(), equalTo(18.0));
        assertThat(contabilidadeBuscada.getStatus(), equalTo(PAGO));
    }

    @Test
    public void buscarRegistrosContabilidadeSemSubTipoParcelamentoSemParcelamento() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createDinheiro();

        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();
        assertThat(registros, notNullValue());

        Contabilidade contabilidadeBuscada = registros.get(0);

        assertThat(contabilidadeBuscada, notNullValue());
        assertThat(contabilidadeBuscada.getCodigo(), notNullValue());
        assertThat(contabilidadeBuscada.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidadeBuscada.getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(contabilidadeBuscada.getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(contabilidadeBuscada.getSubTipoPagamento(), nullValue());
        assertThat(contabilidadeBuscada.getTipo(), equalTo(FIXO));
        assertThat(contabilidadeBuscada.getGrupo(), equalTo("RENDIMENTOS"));
        assertThat(contabilidadeBuscada.getSubGrupo(), equalTo("SALARIO"));
        assertThat(contabilidadeBuscada.getDescricao(), equalTo("sysmap"));
        assertThat(contabilidadeBuscada.getParcelamento(), nullValue());
        assertThat(contabilidadeBuscada.getCategoria(), equalTo(ENTRADA));
        assertThat(contabilidadeBuscada.getValor(), equalTo(5000.0));
        assertThat(contabilidadeBuscada.getStatus(), equalTo(NAO_PAGO));

    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosContabilidadeServiceException() throws IOException, ServiceException {
        deletarArquivosDoDiretorio();

        contabilidadeService.buscarRegistros();
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentosException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registros = null;
        try {
            registros = contabilidadeService.buscarRegistros();
        } catch (ServiceException e) {
        }

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentos1Resultado() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createDinheiro();
        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentos2Resultados() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createDinheiro();
        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("25/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentos3Resultados() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createDinheiro();
        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento("01/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorIntervaloDeVencimentosException() throws IOException, ServiceException {
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
    public void filtrarRegistrosPorIntervaloDeVencimentos1Resultado() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createCartaoDebito();
        contabilidadeService.salvar(contabilidade);

        contabilidade = ContabilidadeFactoryMock.createDinheiro();
        contabilidadeService.salvar(contabilidade);

        CampoFiltro campoFiltro = new CampoFiltroVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }
}
