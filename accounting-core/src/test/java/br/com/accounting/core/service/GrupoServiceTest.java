package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroGrupoDescricao;
import br.com.accounting.core.filter.CampoFiltroGrupoDescricaoSubGrupo;
import br.com.accounting.core.filter.CampoFiltroGrupoDescricaoSubGrupoDescricao;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.ordering.CampoOrdemGrupoDescricao;
import br.com.accounting.core.ordering.CampoOrdemGrupoDescricaoSubGrupo;
import br.com.accounting.core.ordering.CampoOrdemGrupoDescricaoSubGrupoDescricao;
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

public class GrupoServiceTest extends GrupoGenericTest {
    @Autowired
    private GrupoService grupoService;

    @PostConstruct
    public void posConstrucao() {
        setGrupoService(grupoService);
    }

    @Test
    public void salvarGrupo() throws ServiceException {
        Grupo grupo = GrupoFactoryMock.createMoradiaAssinatura();

        assertThat(grupo, notNullValue());
        assertThat(grupo.getDescricao(), equalTo("MORADIA"));
        assertThat(grupo.getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        grupoService.salvar(grupo);

        assertThat(grupo.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarGrupoServiceException() throws ServiceException {
        grupoService.salvar(null);
    }

    @Test(expected = ServiceException.class)
    public void salvarGrupoRepositoryException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        Grupo grupo = GrupoFactoryMock.createMoradiaAssinatura();

        try {
            grupoService.salvar(grupo);
        } catch (ServiceException e) {
            Files.createDirectory(Paths.get(DIRETORIO));
            throw e;
        }
    }

    @Test
    public void buscarRegistrosGrupo() throws ServiceException {
        Grupo grupo = GrupoFactoryMock.createMoradiaAssinatura();

        grupoService.salvar(grupo);

        List<Grupo> registros = grupoService.buscarRegistros();
        assertThat(registros, notNullValue());

        Grupo grupoBuscado = registros.get(0);

        assertThat(grupoBuscado, notNullValue());
        assertThat(grupoBuscado.getDescricao(), equalTo("MORADIA"));
        assertThat(grupo.getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosGrupoServiceException() throws ServiceException {
        grupoService.buscarRegistros();
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricao("MORADIA");
        List<Grupo> registros = getGrupos();

        try {
            grupoService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMercado() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricao("MERCADO");

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricao("MORADIA");

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");
        List<Grupo> registros = getGrupos();

        try {
            grupoService.filtrar(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupo("MORADIA");
        List<Grupo> registros = getGrupos();

        try {
            grupoService.filtrarSubGrupos(campoFiltro, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupo() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupo("MORADIA");
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGrupos(campoFiltro, registros);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricao("MORADIA");

        try {
            grupoService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricao("MORADIA");

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoMoradiaSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");

        try {
            grupoService.filtrar(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradiaSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupoDescricao("MORADIA", "ASSINATURA");

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoMoradiaGrupoSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupo("MORADIA");

        try {
            grupoService.filtrarSubGrupos(campoFiltro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradiaGrupoSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoDescricaoSubGrupo("MORADIA");

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGrupos(campoFiltro);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();

        try {
            grupoService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosSubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();

        try {
            grupoService.ordenar(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoMoradiaGrupoSubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();

        try {
            grupoService.ordenarSubGrupos(campoOrdem, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoMoradiaGrupoSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoOrdem, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosSubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();
        List<Grupo> registros = grupoService.buscarRegistros();

        try {
            grupoService.ordenar(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGrupos(campoOrdem, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoOrdem, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();

        try {
            grupoService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosSubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();

        try {
            grupoService.ordenar(campoOrdem, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoMoradiaGrupoSubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGrupos(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoMoradiaGrupoSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricao();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosSubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();
        List<Grupo> registros = grupoService.buscarRegistros();

        try {
            grupoService.ordenar(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupoDescricao();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGrupos(campoOrdem, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoOrdem campoOrdem = new CampoOrdemGrupoDescricaoSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoOrdem, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ALUGUEL"));
    }
}
