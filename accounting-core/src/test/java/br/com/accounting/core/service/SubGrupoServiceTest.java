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

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupoDescricao("ASSINATURA");

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupoDescricao("ASSINATURA");

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoPadaria() throws ServiceException {
        criarVariosSubGrupos();

        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupoDescricao("PADARIA");

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupoDescricao("ASSINATURA");

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupoDescricao("ASSINATURA");

        List<SubGrupo> registrosFiltradros = subGrupoService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();

        try {
            subGrupoService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();
        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();
        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();

        try {
            subGrupoService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();
        List<SubGrupo> registros = getSubGrupos();

        try {
            subGrupoService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosSubGrupos();

        CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();
        List<SubGrupo> registros = subGrupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = subGrupoService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
    }
}
