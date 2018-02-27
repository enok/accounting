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
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ContabilidadeServiceFiltrarTest extends ContabilidadeGenericTest {
    @Autowired
    private ContabilidadeService contabilidadeService;

    @PostConstruct
    public void posConstrucao() {
        setContabilidadeService(contabilidadeService);
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento1Resultado() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento2Resultados() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("25/01/2018", "31/01/2018", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento3Resultados() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("01/01/2018", "31/01/2018", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
    }

    @Test
    public void filtrarRegistrosPorIntervaloDeVencimento1Resultado() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.filtrarPorIntervaloDeVencimento("26/01/2018", "31/01/2018");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoDinheiro() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeCredito() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(CARTAO_CREDITO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeDebito() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(CARTAO_DEBITO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_DEBITO));
    }

    @Test
    public void filtrarRegistrosPorTipoDePagamentoDinheiro() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.filtrarPorTipoDePagamento(DINHEIRO);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamentoValorNulo() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorSubTipoDePagamento("7660", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento7660() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorSubTipoDePagamento("7660", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento744() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorSubTipoDePagamento("744", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test
    public void filtrarRegistrosPorSubTipoDePagamento7660() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorSubTipoDePagamento("7660");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoFixo() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorTipo(FIXO, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipo744() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorTipo(VARIAVEL, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
    }

    @Test
    public void filtrarRegistrosPorTipoFixo() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorTipo(FIXO);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMoradia() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorGrupo("MORADIA", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMercado() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupo("MERCADO", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test
    public void filtrarRegistrosPorGrupoMoradia() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorGrupo("MORADIA");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMoradiaESubGrupoAssinatura() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorGrupoESubGrupo("MORADIA", "ASSINATURA", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMercadoESubGrupoPadaria() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorGrupoESubGrupo("MERCADO", "PADARIA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test
    public void filtrarRegistrosPorGrupoMoradiaESubGrupoAssinatura() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorGrupoESubGrupo("MORADIA", "ASSINATURA");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoSpotify() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorDescricao("spotify", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoPao() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorDescricao("pão", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
    }

    @Test
    public void filtrarRegistrosPorDescricaoSpotify() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorDescricao("spotify");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorParcelamentoPai() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorParcelamentoPai(-1L, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(-1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(-1L));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorParcelamentoPaiException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrarPorParcelamentoPai(-1L, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorParcelamentoPai() throws ServiceException {
        criarVariasContabilidades2();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorParcelamentoPai(-1L);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getParcelamento().getCodigoPai(), equalTo(-1L));
        assertThat(registrosFiltradros.get(1).getParcelamento().getCodigoPai(), equalTo(-1L));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorParcelamentoPaiException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.filtrarPorParcelamentoPai(-1L);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorCategoriaEntrada() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorCategoria(ENTRADA, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorCategoriaSaida() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorCategoria(SAIDA, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
    }

    @Test
    public void filtrarRegistrosPorCategoriaEntrada() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorCategoria(ENTRADA);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorCategoriaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.filtrarPorCategoria(ENTRADA);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorStatusPago() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(PAGO, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorStatusNaoPago() throws ServiceException {
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

        try {
            contabilidadeService.filtrarPorStatus(PAGO, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorStatusPago() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(PAGO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(PAGO));
        assertThat(registrosFiltradros.get(1).getStatus(), equalTo(PAGO));
    }

    @Test
    public void filtrarRegistrosPorStatusNaoPago() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrarPorStatus(NAO_PAGO);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getStatus(), equalTo(NAO_PAGO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorStatusException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            contabilidadeService.filtrarPorStatus(PAGO);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }
}
