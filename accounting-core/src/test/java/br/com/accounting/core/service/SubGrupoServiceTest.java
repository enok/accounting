package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubGrupoFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroSubGrupoDescricao;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.ordering.CampoOrdemSubGrupoDescricao;
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

public class SubGrupoServiceTest extends SubGrupoGenericTest {
    @Autowired
    private SubGrupoService subGrupoService;

    @PostConstruct
    public void posConstrucao() {
        setSubGrupoService(subGrupoService);
    }

    @Test
    public void salvarGrupo() throws ServiceException {
        SubGrupo subGrupo = SubGrupoFactoryMock.createAssinatura();

        assertThat(subGrupo, notNullValue());
        assertThat(subGrupo.getDescricao(), equalTo("ASSINATURA"));

        subGrupoService.salvar(subGrupo);

        assertThat(subGrupo.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarSubGrupoServiceException() throws ServiceException {
        subGrupoService.salvar(null);
    }

    @Test(expected = ServiceException.class)
    public void salvarGrupoRepositoryException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        SubGrupo subGrupo = SubGrupoFactoryMock.createAssinatura();

        try {
            subGrupoService.salvar(subGrupo);
        } catch (ServiceException e) {
            Files.createDirectory(Paths.get(DIRETORIO));
            throw e;
        }
    }

    @Test
    public void buscarRegistrosGrupo() throws ServiceException {
        SubGrupo subGrupo = SubGrupoFactoryMock.createAssinatura();

        subGrupoService.salvar(subGrupo);

        List<SubGrupo> registros = subGrupoService.buscarRegistros();
        assertThat(registros, notNullValue());

        SubGrupo grupoBuscado = registros.get(0);

        assertThat(grupoBuscado, notNullValue());
        assertThat(grupoBuscado.getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosSubGrupoServiceException() throws ServiceException {
        subGrupoService.buscarRegistros();
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoAssinaturas() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrarPorDescricao("ASSINATURA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.filtrarPorDescricao("ASSINATURA", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoPadaria() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrarPorDescricao("PADARIA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
    }

    @Test
    public void filtrarRegistrosPorDescricaoAssinaturas() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrarPorDescricao("ASSINATURA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            subGrupoService.filtrarPorDescricao("ASSINATURA");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenarPorDescricao(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.ordenarPorDescricao(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenarPorDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.ordenarPorDescricao(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenarPorDescricao(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            subGrupoService.ordenarPorDescricao(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenarPorDescricao(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            subGrupoService.ordenarPorDescricao(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }
}
