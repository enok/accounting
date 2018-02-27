package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
import static br.com.accounting.core.entity.Order.ASC;
import static br.com.accounting.core.entity.Order.DESC;
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
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

    @Test
    public void ordenarRegistrosBuscadosPorVencimentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorVencimento(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("27/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorVencimentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorVencimento(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }


    @Test
    public void ordenarRegistrosBuscadosPorVencimentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorVencimento(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("15/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorVencimentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorVencimento(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorVencimento(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("27/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorVencimento(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorVencimento(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registrosFiltradros.get(1).getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registrosFiltradros.get(2).getVencimentoFormatado(), equalTo("15/01/2018"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorVencimento(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipoDePagamento(ASC, registros);

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

        try {
            contabilidadeService.ordenarPorTipoDePagamento(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipoDePagamento(DESC, registros);

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

        try {
            contabilidadeService.ordenarPorTipoDePagamento(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipoDePagamento(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorTipoDePagamento(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoDecrescente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipoDePagamento(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(registrosFiltradros.get(1).getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registrosFiltradros.get(2).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDePagamentoDecrescenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorTipoDePagamento(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarSubTipoDePagamento(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarSubTipoDePagamento(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarSubTipoDePagamento(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarSubTipoDePagamento(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarSubTipoDePagamento(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorSubTipoDePagamentoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarSubTipoDePagamento(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarSubTipoDePagamento(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorSubTipoDePagamentoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarSubTipoDePagamento(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipo(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorTipo(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipo(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorTipoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorTipo(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }


    @Test
    public void ordenarRegistrosPorTipoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipo(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorTipo(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorTipoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorTipo(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(2).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorTipoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorTipo(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupo(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorGrupo(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupo(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorGrupo(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupo(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorGrupo(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupo(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorGrupo(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoAscendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupoESubGrupo(ASC, registros);

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

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorGrupoESubGrupo(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoDescendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupoESubGrupo(DESC, registros);

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

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorGrupoESubGrupo(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoESubGrupoAscendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupoESubGrupo(ASC);

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
    public void ordenarRegistrosPorGrupoESubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorGrupoESubGrupo(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorGrupoESubGrupoDescendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorGrupoESubGrupo(DESC);

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
    public void ordenarRegistrosPorGrupoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorGrupoESubGrupo(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorDescricao(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorDescricao(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("pão"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorDescricao(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorDescricao(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorDescricao(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorDescricao(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("pão"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorDescricao(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorParcelamentoPaiAscendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(-1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(1L));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorParcelamentoPaiAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorParcelamentoPai(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorParcelamentoPaiDescendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(-1L));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorParcelamentoPaiDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorParcelamentoPai(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorParcelamentoPaiAscendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(-1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(1L));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorParcelamentoPaiAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorParcelamentoPai(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorParcelamentoPaiDescendente() throws ServiceException {
        criarVariasContabilidades3();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(-1L));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorParcelamentoPaiDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorParcelamentoPai(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorCategoriaAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorCategoria(ASC, registros);

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

        try {
            contabilidadeService.ordenarPorCategoria(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorCategoriaDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorCategoria(DESC, registros);

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

        try {
            contabilidadeService.ordenarPorCategoria(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorCategoriaAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorCategoria(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(2).getCategoria(), equalTo(SAIDA));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorCategoriaAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorCategoria(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorCategoriaDecrescente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorCategoria(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(2).getCategoria(), equalTo(ENTRADA));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorCategoriaDecrescenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorCategoria(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorValorAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorValor(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getValor(), equalTo(18.0));
        assertThat(registrosFiltradros.get(1).getValor(), equalTo(26.90));
        assertThat(registrosFiltradros.get(2).getValor(), equalTo(5000.0));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorValorAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorValor(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorValorDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorValor(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getValor(), equalTo(5000.0));
        assertThat(registrosFiltradros.get(1).getValor(), equalTo(26.90));
        assertThat(registrosFiltradros.get(2).getValor(), equalTo(18.0));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorValorDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorValor(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorValorAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorValor(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getValor(), equalTo(18.0));
        assertThat(registrosFiltradros.get(1).getValor(), equalTo(26.90));
        assertThat(registrosFiltradros.get(2).getValor(), equalTo(5000.0));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorValorAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorValor(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorValorDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorValor(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getValor(), equalTo(5000.0));
        assertThat(registrosFiltradros.get(1).getValor(), equalTo(26.90));
        assertThat(registrosFiltradros.get(2).getValor(), equalTo(18.0));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorValorDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorValor(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorStatusAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorStatus(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(NAO_PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(2).getStatus(), equalTo(PAGO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorStatusAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorStatus(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorStatusDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorStatus(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(2).getStatus(), equalTo(NAO_PAGO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorStatusDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.ordenarPorStatus(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorStatusAscendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorStatus(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(NAO_PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(2).getStatus(), equalTo(PAGO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorStatusAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorStatus(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorStatusDescendente() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorStatus(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(2).getStatus(), equalTo(NAO_PAGO));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorStatusDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.ordenarPorStatus(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }
}
