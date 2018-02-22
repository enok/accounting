package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Order.ASC;
import static br.com.accounting.core.entity.Order.DESC;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ContabilidadeServiceOrdenarTest extends ContabilidadeGenericTest {
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
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendenteException() throws IOException, ServiceException {
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
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
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
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("27/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescenteException() throws IOException, ServiceException {
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
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
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
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("15/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoAscendenteException() throws IOException, ServiceException {
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
    public void ordenarRegistrosPorTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
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
    public void ordenarRegistrosBuscadosPorTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoDecrescenteException() throws IOException, ServiceException {
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
    public void ordenarRegistrosPorTipoDePagamentoDecrescente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
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
    public void ordenarRegistrosBuscadosPorTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorSubTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorSubTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroSubTipoPagamento();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipo();

        try {
            contabilidadeService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipo();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipo();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipo();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipo();

        try {
            contabilidadeService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipo();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroTipo();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroTipo();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(FIXO));
    }
}
