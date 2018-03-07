package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactoryMock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Order.ASC;
import static br.com.accounting.core.entity.Order.DESC;
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

        grupoService.salvar(grupo);
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

        grupoService.filtrarPorDescricao("MERCADO", registros);
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricao("MORADIA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricao("MORADIA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(3));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        grupoService.filtrarPorDescricao("MORADIA");
    }

    @Test
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA", registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosBuscadosPorDescricaoMoradiaSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA", registros);
    }

    @Test
    public void filtrarRegistrosPorDescricaoMoradiaSubGrupoAssinatura() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA");

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(2));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarRegistrosPorDescricaoMoradiaSubGrupoAssinaturaException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        grupoService.filtrarPorDescricaoESubGrupo("MORADIA", "ASSINATURA");
    }

    @Test
    public void filtrarSubGruposBuscadosPorGrupoDescricaoMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA", registros);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(3));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ASSINATURA"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(subGruposFiltrados.get(2).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarSubGruposBuscadosPorGrupoDescricaoException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA", registros);
    }

    @Test
    public void filtrarSubGruposBuscadosPorGrupoDescricaoSemDuplicidadeMoradia() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> subGruposFiltrados = grupoService.filtrarSubGruposPorGrupoDescricaoSemDuplicidade("MORADIA", registros);

        assertThat(subGruposFiltrados, notNullValue());
        assertThat(subGruposFiltrados.size(), equalTo(2));

        assertThat(subGruposFiltrados.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(subGruposFiltrados.get(1).getDescricao(), equalTo("ASSINATURA"));
    }

    @Test(expected = ServiceException.class)
    public void filtrarSubGruposBuscadosPorGrupoDescricaoSemDuplicidadeException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.filtrarSubGruposPorGrupoDescricaoSemDuplicidade("MORADIA", registros);
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

        grupoService.filtrarSubGruposPorGrupoDescricao("MORADIA");
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

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

        grupoService.ordenarPorDescricao(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(3).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.ordenarPorDescricao(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

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

        grupoService.ordenarPorDescricao(ASC);
    }

    @Test
    public void ordenarRegistrosPorDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricao(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(3).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        grupoService.ordenarPorDescricao(DESC);
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

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

        grupoService.ordenarPorDescricaoESubGrupo(ASC, registros);
    }

    @Test
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(3).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosBuscadosPorDescricaoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.ordenarPorDescricaoESubGrupo(DESC, registros);
    }

    @Test
    public void ordenarRegistrosPorDescricaoESubGrupoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

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

        grupoService.ordenarPorDescricaoESubGrupo(ASC);
    }

    @Test
    public void ordenarRegistrosPorDescricaoESubGrupoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registrosFiltradros = grupoService.ordenarPorDescricaoESubGrupo(DESC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));

        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(0).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(1).getSubGrupo().getDescricao(), equalTo("ASSINATURA"));

        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("MORADIA"));
        assertThat(registrosFiltradros.get(2).getSubGrupo().getDescricao(), equalTo("ALUGUEL"));

        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("MERCADO"));
        assertThat(registrosFiltradros.get(3).getSubGrupo().getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarRegistrosPorDescricaoESubGrupoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        grupoService.ordenarPorDescricaoESubGrupo(DESC);
    }

    @Test
    public void ordenarSubGruposBuscadosPorGrupoDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(ASC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposBuscadosPorGrupoDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.ordenarSubGruposPorGrupoDescricao(ASC, registros);
    }

    @Test
    public void ordenarSubGruposBuscadosPorGrupoDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposBuscadosPorGrupoDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);
    }

    @Test
    public void ordenarSubGruposPorGrupoDescricaoAscendente() throws ServiceException {
        criarVariosGrupos();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(ASC);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("ALUGUEL"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("PADARIA"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposPorGrupoDescricaoAscendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        grupoService.ordenarSubGruposPorGrupoDescricao(ASC);
    }

    @Test
    public void ordenarSubGruposPorGrupoDescricaoDescendente() throws ServiceException {
        criarVariosGrupos();

        List<Grupo> registros = grupoService.buscarRegistros();

        List<SubGrupo> registrosFiltradros = grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);

        assertThat(registrosFiltradros, notNullValue());
        assertThat(registrosFiltradros.size(), equalTo(4));
        assertThat(registrosFiltradros.get(0).getDescricao(), equalTo("PADARIA"));
        assertThat(registrosFiltradros.get(1).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(2).getDescricao(), equalTo("ASSINATURA"));
        assertThat(registrosFiltradros.get(3).getDescricao(), equalTo("ALUGUEL"));
    }

    @Test(expected = ServiceException.class)
    public void ordenarSubGruposPorGrupoDescricaoDescendenteException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        List<Grupo> registros = getGrupos();

        grupoService.ordenarSubGruposPorGrupoDescricao(DESC, registros);
    }
}
