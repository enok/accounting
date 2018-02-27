package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactoryMock;
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

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMercado() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricao("MERCADO", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.filtrarPorDescricao("MERCADO", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricao("MORADIA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricao("MORADIA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.filtrarPorDescricao("MORADIA");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradiaSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(1));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoMoradiaSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarSubGruposBuscadosPorGrupoDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA", registros);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarSubGruposBuscadosPorGrupoDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA", registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void filtrarSubGruposPorGrupoDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA");

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarSubGruposPorGrupoDescricaoMoradiaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA");
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(ASC, registros);

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
    public void ordenarRegistrosBuscadosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarPorDescricao(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(DESC, registros);

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
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarPorDescricao(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(ASC);

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
    public void ordenarRegistrosPorDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.ordenarPorDescricao(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(DESC);

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
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.ordenarPorDescricao(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(ASC, registros);

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
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarPorDescricaoESubGrupo(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(DESC, registros);

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
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarPorDescricaoESubGrupo(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoESubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(ASC);

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
    public void ordenarRegistrosPorDescricaoESubGrupoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.ordenarPorDescricaoESubGrupo(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarRegistrosPorDescricaoESubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(DESC);

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
    public void ordenarRegistrosPorDescricaoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.ordenarPorDescricaoESubGrupo(DESC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarSubGruposBuscadosPorGrupoDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposBuscadosPorGrupoDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGruposPorGrupoDescricao(ASC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarSubGruposBuscadosPorGrupoDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposBuscadosPorGrupoDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarSubGruposPorGrupoDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposPorGrupoDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        try {
            grupoService.ordenarSubGruposPorGrupoDescricao(ASC);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }

    @Test
    public void ordenarSubGruposPorGrupoDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposPorGrupoDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        try {
            grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }
}
