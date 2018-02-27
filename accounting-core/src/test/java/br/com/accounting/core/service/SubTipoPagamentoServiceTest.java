package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubTipoPagamentoFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroSubTipoPagamentoDescricao;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.ordering.CampoOrdemSubTipoPagamentoDescricao;
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

    @Test
    public void filtrarRegistrosBuscadosPorDescricao744() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.filtrarPorDescricao("744", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricao7660() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.filtrarPorDescricao("7660", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<SubTipoPagamento> registros = getSubTipoPagamentos();

        try {
            subTipoPagamentoService.filtrarPorDescricao("744", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricao744() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.filtrarPorDescricao("744");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            subTipoPagamentoService.filtrarPorDescricao("744");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenarPorDescricao(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<SubTipoPagamento> registros = getSubTipoPagamentos();

        try {
            subTipoPagamentoService.ordenarPorDescricao(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenarPorDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<SubTipoPagamento> registros = getSubTipoPagamentos();

        try {
            subTipoPagamentoService.ordenarPorDescricao(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenarPorDescricao(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("7660"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            subTipoPagamentoService.ordenarPorDescricao(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariasSubTipoPagamentos();

        List<SubTipoPagamento> registrosFiltradros = subTipoPagamentoService.ordenarPorDescricao(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("7660"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("744"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            subTipoPagamentoService.ordenarPorDescricao(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }
}
