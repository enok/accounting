package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubTipoPagamentoFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroDescricaoSubTipoPagamento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static br.com.accounting.core.entity.Order.ASC;
import static br.com.accounting.core.entity.Order.DESC;
import static br.com.accounting.core.repository.impl.GenericRepository.DIRETORIO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SubTipoPagamentoServiceTest extends SubTipoPagamentoGenericTest {
    @Autowired
    private SubTipoPagamentoService subTipoPagamentoService;

    @PostConstruct
    public void posConstrucao() {
        setSubTipoPagamentoService(subTipoPagamentoService);
    }

    @Test
    public void salvarSubTipoPagamento() throws ServiceException {
        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create744();

        assertThat(subTipoPagamento, notNullValue());
        assertThat(subTipoPagamento.getDescricao(), equalTo("744"));

        subTipoPagamentoService.salvar(subTipoPagamento);

        assertThat(subTipoPagamento.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarSubTipoPagamentoServiceException() throws ServiceException {
        subTipoPagamentoService.salvar(null);
    }

    @Test(expected = ServiceException.class)
    public void salvarSubTipoPagamentoRepositoryException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create744();

        try {
            subTipoPagamentoService.salvar(subTipoPagamento);
        } catch (ServiceException e) {
            Files.createDirectory(Paths.get(DIRETORIO));
            throw e;
        }
    }

    @Test
    public void buscarRegistrosSubTipoPagamento() throws ServiceException {
        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create744();

        subTipoPagamentoService.salvar(subTipoPagamento);

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();
        assertThat(registros, notNullValue());

        SubTipoPagamento subTipoPagamentoBuscado = registros.get(0);

        assertThat(subTipoPagamentoBuscado, notNullValue());
        assertThat(subTipoPagamentoBuscado.getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosSubTipoPagamentoServiceException() throws ServiceException {
        subTipoPagamentoService.buscarRegistros();
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento("744");

        List<SubTipoPagamento> registros = getSubTipoPagamentos();

        try {
            subTipoPagamentoService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricao744() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento("744");

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricao7660() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento("7660");

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento("744");

        try {
            subTipoPagamentoService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricao744() throws ServiceException {
        criarVariasSubTipoPagamentos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento("744");

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();

        try {
            subTipoPagamentoService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();
        List<SubTipoPagamento> registros = getSubTipoPagamentos();

        try {
            subTipoPagamentoService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();
        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();

        try {
            subTipoPagamentoService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();
        List<SubTipoPagamento> registros = getSubTipoPagamentos();

        try {
            subTipoPagamentoService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubTipoPagamento();
        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("744"));
    }
}
