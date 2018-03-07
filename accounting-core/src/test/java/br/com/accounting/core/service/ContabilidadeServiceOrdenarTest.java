package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
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
import static org.hamcrest.Matchers.*;

public class ContabilidadeServiceOrdenarTest extends ContabilidadeGenericTest {
    @Autowired
    private ContabilidadeService contabilidadeService;

    @PostConstruct
    public void posConstrucao() {
        setContabilidadeService(contabilidadeService);
    }

    @Test
    public void ordenarRegistrosBuscadosPorVencimentoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorVencimento(ASC, registros);
    }


    @Test
    public void ordenarRegistrosBuscadosPorVencimentoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorVencimento(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorVencimento(ASC);
    }

    @Test
    public void ordenarRegistrosPorIntervaloDeVencimentoDecrescente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorVencimento(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipoDePagamento(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDePagamentoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipoDePagamento(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipoDePagamento(ASC);
    }

    @Test
    public void ordenarRegistrosPorTipoDePagamentoDecrescente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipoDePagamento(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarSubTipoDePagamento(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorSubTipoDePagamentoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarSubTipoDePagamento(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarSubTipoDePagamento(ASC);
    }

    @Test
    public void ordenarRegistrosPorSubTipoDePagamentoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarSubTipoDePagamento(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipo(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorTipoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipo(DESC, registros);
    }


    @Test
    public void ordenarRegistrosPorTipoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipo(ASC);
    }

    @Test
    public void ordenarRegistrosPorTipoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorTipo(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupo(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupo(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorGrupoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupo(ASC);
    }

    @Test
    public void ordenarRegistrosPorGrupoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupo(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupoESubGrupo(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorGrupoESubGrupoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupoESubGrupo(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorGrupoESubGrupoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupoESubGrupo(ASC);
    }

    @Test
    public void ordenarRegistrosPorGrupoESubGrupoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorGrupoESubGrupo(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorDescricao(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorDescricao(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorDescricao(ASC);
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorDescricao(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorParcelamentoPaiAscendente() throws ServiceException, ParseException {
        criarVariasContabilidades3();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), nullValue());
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(1L));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorParcelamentoPaiAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.ordenarPorParcelamentoPai(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorParcelamentoPaiDescendente() throws ServiceException, ParseException {
        criarVariasContabilidades3();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), nullValue());
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorParcelamentoPaiDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.ordenarPorParcelamentoPai(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorParcelamentoPaiAscendente() throws ServiceException, ParseException {
        criarVariasContabilidades3();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), nullValue());
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(1L));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorParcelamentoPaiAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.ordenarPorParcelamentoPai(ASC);
    }

    @Test
    public void ordenarRegistrosPorParcelamentoPaiDescendente() throws ServiceException, ParseException {
        criarVariasContabilidades3();

        List<Contabilidade> registrosFiltradros = contabilidadeService.ordenarPorParcelamentoPai(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), nullValue());
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorParcelamentoPaiDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.ordenarPorParcelamentoPai(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorCategoriaAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorCategoria(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorCategoriaDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorCategoria(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorCategoriaAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorCategoria(ASC);
    }

    @Test
    public void ordenarRegistrosPorCategoriaDecrescente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorCategoria(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorValorAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorValor(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorValorDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorValor(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorValorAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorValor(ASC);
    }

    @Test
    public void ordenarRegistrosPorValorDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorValor(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorStatusAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorStatus(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorStatusDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorStatus(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorStatusAscendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorStatus(ASC);
    }

    @Test
    public void ordenarRegistrosPorStatusDescendente() throws ServiceException, ParseException {
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

        contabilidadeService.ordenarPorStatus(DESC);
    }
}
