package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
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

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeVencimento("26/01/2018", "31/01/2018");

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

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento2Resultados() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeVencimento("25/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
    }

    @Test
    public void filtrarRegistrosBuscadosPorIntervaloDeVencimento3Resultados() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeVencimento("01/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorIntervaloDeVencimentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeVencimento("26/01/2018", "31/01/2018");

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

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeVencimento("26/01/2018", "31/01/2018");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipoPagamento(DINHEIRO);

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

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipoPagamento(DINHEIRO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeCredito() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipoPagamento(CARTAO_CREDITO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_CREDITO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoDePagamentoCartaoDeDebito() throws ServiceException {
        criarVariasContabilidades();

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipoPagamento(CARTAO_DEBITO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(CARTAO_DEBITO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipoPagamento(DINHEIRO);

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

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipoPagamento(DINHEIRO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getTipoPagamento(), equalTo(DINHEIRO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorSubTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeSubTipoPagamentoDescricao("7660");

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

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeSubTipoPagamentoDescricao("7660");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento7660() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeSubTipoPagamentoDescricao("7660");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorSubTipoDePagamento744() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeSubTipoPagamentoDescricao("744");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorSubTipoDePagamentoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeSubTipoPagamentoDescricao("7660");

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

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeSubTipoPagamentoDescricao("7660");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getSubTipoPagamento().getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorTipoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipo(FIXO);
        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipoFixo() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipo(FIXO);
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
    }

    @Test
    public void filtrarRegistrosBuscadosPorTipo744() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipo(VARIAVEL);
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(VARIAVEL));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorTipoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipo(FIXO);

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorTipoFixo() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeTipo(FIXO);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getTipo(), equalTo(FIXO));
        assertThat(registrosFiltradros.get(1).getTipo(), equalTo(FIXO));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricao("MORADIA");

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMoradia() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricao("MORADIA");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMercado() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricao("MERCADO");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricao("MORADIA");

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorGrupoMoradia() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricao("MORADIA");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorGrupoESubGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMoradiaESubGrupoAssinatura() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorGrupoMercadoESubGrupoPadaria() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricaoSubGrupoDescricao("MERCADO", "PADARIA");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorGrupoESubGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorGrupoMoradiaESubGrupoAssinatura() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeDescricao("spotify");

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoSpotify() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeDescricao("spotify");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoPao() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeDescricao("pão");
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("pão"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeDescricao("spotify");

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricaoSpotify() throws ServiceException {
        criarVariasContabilidades2();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeDescricao("spotify");

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("spotify"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("spotify"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorCategoriaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeCategoria(ENTRADA);

        List<Contabilidade> registros = getContabilidades();

        try {
            contabilidadeService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorCategoriaEntrada() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeCategoria(ENTRADA);
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
    }

    @Test
    public void filtrarRegistrosBuscadosPorCategoriaSaida() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeCategoria(SAIDA);
        List<Contabilidade> registros = contabilidadeService.buscarRegistros();

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(SAIDA));
        assertThat(registrosFiltradros.get(1).getCategoria(), equalTo(SAIDA));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorCategoriaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeCategoria(ENTRADA);

        try {
            contabilidadeService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorCategoriaEntrada() throws ServiceException {
        criarVariasContabilidades();

        CampoFiltro campoFiltro = new CampoFiltroContabilidadeCategoria(ENTRADA);

        List<Contabilidade> registrosFiltradros = contabilidadeService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getCategoria(), equalTo(ENTRADA));
    }
}
