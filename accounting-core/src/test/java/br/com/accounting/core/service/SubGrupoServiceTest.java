package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubGrupoFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroDescricaoSubGrupo;
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

public class SubGrupoServiceTest extends SubGrupoGenericTest {
    @Autowired
    private SubGrupoService subGrupoService;

    @PostConstruct
    public void posConstrucao() {
        setSubGrupoService(subGrupoService);
    }

    @Before
    public void setUp() throws IOException {
        deletarArquivosDoDiretorio();
    }

    @After
    public void after() throws IOException {
        deletarArquivosDoDiretorio();
    }

    @Test
    public void salvarGrupo() throws ServiceException {
        SubGrupo subGrupo = SubGrupoFactoryMock.createAssinaturas();

        assertThat(subGrupo, notNullValue());
        assertThat(subGrupo.getDescricao(), equalTo("ASSINATURAS"));

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

        SubGrupo subGrupo = SubGrupoFactoryMock.createAssinaturas();

        try {
            subGrupoService.salvar(subGrupo);
        } catch (ServiceException e) {
            Files.createDirectory(Paths.get(DIRETORIO));
            throw e;
        }
    }

    @Test
    public void buscarRegistrosGrupo() throws ServiceException {
        SubGrupo subGrupo = SubGrupoFactoryMock.createAssinaturas();

        subGrupoService.salvar(subGrupo);

        List<SubGrupo> registros = subGrupoService.buscarRegistros();
        assertThat(registros, notNullValue());

        SubGrupo grupoBuscado = registros.get(0);

        assertThat(grupoBuscado, notNullValue());
        assertThat(grupoBuscado.getDescricao(), equalTo("ASSINATURAS"));
    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosSubGrupoServiceException() throws ServiceException {
        subGrupoService.buscarRegistros();
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo("ASSINATURAS");

        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoAssinaturas() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo("ASSINATURAS");

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURAS"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoPadaria() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo("PADARIA");

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo("ASSINATURAS");

        try {
            subGrupoService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricaoAssinaturas() throws ServiceException {
        criarVariosSubGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo("ASSINATURAS");

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURAS"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();

        try {
            subGrupoService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();
        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();
        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();

        try {
            subGrupoService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoFiltro, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURAS"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();
        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoSubGrupo();
        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURAS"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURAS"));
    }
}