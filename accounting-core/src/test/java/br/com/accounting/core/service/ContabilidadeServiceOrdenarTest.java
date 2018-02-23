package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.ordering.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
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

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

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

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("27/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

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

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorIntervaloDeVencimentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("15/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

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

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoDecrescenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoDecrescente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

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

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorSubTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorSubTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoDescendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorGrupoESubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoESubGrupoAscendente() throws ServiceException {
        criarVariasContabilidades3();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoAscendente() throws ServiceException {
        criarVariasContabilidades3();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorGrupoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoESubGrupoDescendente() throws ServiceException {
        criarVariasContabilidades3();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(2).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoDescendente() throws ServiceException {
        criarVariasContabilidades3();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(2).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariasContabilidades2();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariasContabilidades2();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariasContabilidades2();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("pão"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariasContabilidades2();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("pão"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorCategoriaAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        try {
            contabilidadeService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorCategoriaAscendente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(2).getCategoria(), equalTo(SAIDA));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorCategoriaAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorCategoriaAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(2).getCategoria(), equalTo(SAIDA));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorCategoriaDecrescenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        try {
            contabilidadeService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorCategoriaDecrescente() throws ServiceException {
        criarVariasContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(2).getCategoria(), equalTo(ENTRADA));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorCategoriaDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        try {
            contabilidadeService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorCategoriaDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(2).getCategoria(), equalTo(ENTRADA));
    }
}
