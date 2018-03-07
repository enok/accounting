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
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContabilidadeServiceFiltrarTest extends ContabilidadeGenericTest {
    @Autowired
    private ContabilidadeService contabilidadeService;

    @PostConstruct
    public void posConstrucao() {
        setContabilidadeService(contabilidadeService);
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento1Resultado() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento2Resultados() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("25/01/2018", "31/01/2018", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento3Resultados() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("01/01/2018", "31/01/2018", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
    }

    @Test
    public void filtrarRegistrosPorIntervaloDeVencimento1Resultado() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018");
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorValoresAcimaDoVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorValoresAcimaDoVencimento("26/01/2018", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoDinheiro() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO, registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeCredito() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(CARTAO_CREDITO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeDebito() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(CARTAO_DEBITO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_DEBITO));
    }

    @Test
    public void filtrarRegistrosPorTipoDePagamentoDinheiro() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO);
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamentoValorNulo() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorSubTipoDePagamento("7660", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorSubTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorSubTipoDePagamento("7660", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento7660() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorSubTipoDePagamento("7660", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento744() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorSubTipoDePagamento("744", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test
    public void filtrarRegistrosPorSubTipoDePagamento7660() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorSubTipoDePagamento("7660");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorSubTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorSubTipoDePagamento("7660");
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoFixo() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipo(FIXO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorTipoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorTipo(FIXO, registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipo744() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipo(VARIAVEL, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
    }

    @Test
    public void filtrarRegistrosPorTipoFixo() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipo(FIXO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorTipoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorTipo(FIXO);
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMoradia() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupo("MORADIA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorGrupo("MORADIA", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMercado() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupo("MERCADO", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test
    public void filtrarRegistrosPorGrupoMoradia() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupo("MORADIA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorGrupo("MORADIA");
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMoradiaESubGrupoAssinatura() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupoESubGrupo("MORADIA", "ASSINATURA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorGrupoESubGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorGrupoESubGrupo("MORADIA", "ASSINATURA", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMercadoESubGrupoPadaria() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupoESubGrupo("MERCADO", "PADARIA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test
    public void filtrarRegistrosPorGrupoMoradiaESubGrupoAssinatura() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupoESubGrupo("MORADIA", "ASSINATURA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorGrupoESubGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorGrupoESubGrupo("MORADIA", "ASSINATURA");
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoSpotify() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorDescricao("spotify", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorDescricao("spotify", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoPao() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorDescricao("pão", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
    }

    @Test
    public void filtrarRegistrosPorDescricaoSpotify() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorDescricao("spotify");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorDescricao("spotify");
    }

    @Test
    public void filtrarRegistrosBuscadosPorParcelamentoPai() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorParcelamentoPai(null, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), nullValue());
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), nullValue());
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorParcelamentoPaiException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorParcelamentoPai(-1L, registros);
    }

    @Test
    public void filtrarRegistrosPorParcelamentoPai() throws ServiceException, ParseException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorParcelamentoPai(null);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), nullValue());
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), nullValue());
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorParcelamentoPaiException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorParcelamentoPai(-1L);
    }

    @Test
    public void filtrarRegistrosBuscadosPorCategoriaEntrada() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorCategoria(ENTRADA, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorCategoriaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorCategoria(ENTRADA, registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorCategoriaSaida() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorCategoria(SAIDA, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
    }

    @Test
    public void filtrarRegistrosPorCategoriaEntrada() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorCategoria(ENTRADA);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorCategoriaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorCategoria(ENTRADA);
    }

    @Test
    public void filtrarRegistrosBuscadosPorStatusPago() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(PAGO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorStatusNaoPago() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(NAO_PAGO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(NAO_PAGO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorStatusException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        contabilidadeService.filtrarPorStatus(PAGO, registros);
    }

    @Test
    public void filtrarRegistrosPorStatusPago() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(PAGO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
    }

    @Test
    public void filtrarRegistrosPorStatusNaoPago() throws ServiceException, ParseException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(NAO_PAGO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(NAO_PAGO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorStatusException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        contabilidadeService.filtrarPorStatus(PAGO);
    }
}
