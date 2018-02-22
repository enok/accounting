package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactoryMock;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.filter.CampoFiltroDescricaoGrupo;
import br.com.accounting.core.filter.CampoFiltroGrupoSubGrupos;
import br.com.accounting.core.filter.CampoFiltroSubGrupo;
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

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo("MORADIA");
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

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo("MERCADO");

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo("MORADIA");

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo("MORADIA", "ASSINATURA");
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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo("MORADIA", "ASSINATURA");
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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos("MORADIA");
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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos("MORADIA");
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGrupos(campoFiltro, registros);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo("MORADIA");

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

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo("MORADIA");

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo("MORADIA", "ASSINATURA");

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo("MORADIA", "ASSINATURA");

        List<Grupo> registrosFiltradros = grupoService.filtrar(campoFiltro);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoMoradiaGrupoSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos("MORADIA");

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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos("MORADIA");

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGrupos(campoFiltro);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();

        try {
            grupoService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, ASC);

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();

        try {
            grupoService.ordenar(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, ASC);

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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();

        try {
            grupoService.ordenarSubGrupos(campoFiltro, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoMoradiaGrupoSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoFiltro, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, registros, ASC);

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        try {
            grupoService.ordenar(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, registros, ASC);

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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGrupos(campoFiltro, registros, ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoFiltro, registros, ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();

        try {
            grupoService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, DESC);

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();

        try {
            grupoService.ordenar(campoFiltro, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, DESC);

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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGrupos(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoMoradiaGrupoSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroDescricaoGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, registros, DESC);

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

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        try {
            grupoService.ordenar(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroSubGrupo();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenar(campoFiltro, registros, DESC);

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

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();
        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGrupos(campoFiltro, registros, DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoMoradiaGrupoSubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        CampoFiltro campoFiltro = new CampoFiltroGrupoSubGrupos();
        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGrupos(campoFiltro, registros, DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ALUGUEL"));
    }
}
