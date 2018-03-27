package br.com.accounting.business.service;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.GrupoDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertTrue;

public class GrupoBusinessTest extends GenericTest {
    @Autowired
    private GrupoBusiness business;

    @Test(expected = BusinessException.class)
    public void criarSemDiretorio() throws IOException, BusinessException {
        try {
            deletarDiretorioEArquivos();
            criarGrupoMoradia();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o grupo."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarSemNome() throws BusinessException {
        try {
            GrupoDTO grupoDTO = grupoMoradiaSemNome();
            business.criar(grupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o grupo."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarSemDescricao() throws BusinessException {
        try {
            GrupoDTO grupoDTO = grupoMoradiaSemDescricao();
            business.criar(grupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o grupo."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarSemNomeDescricao() throws BusinessException {
        try {
            GrupoDTO grupoDTO = grupoMoradiaSemNomeDescricao();
            business.criar(grupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o grupo."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(2));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            assertThat(erros.get(1), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test
    public void criarSemGrupo() throws BusinessException {
        Long codigo = criarGrupoMoradiaSemGrupos();
        assertGrupoMoradiaSemGrupos(codigo);
    }

    @Test
    public void criar() throws BusinessException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);
    }

    @Test(expected = BusinessException.class)
    public void criarDoisComNomesIguais() throws BusinessException {
        try {
            criarGrupoMoradia();
            criarGrupoMoradia();
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Grupo duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDois() throws BusinessException {
        Long codigo1 = criarGrupoMoradia();
        assertGrupoMoradia(codigo1);

        Long codigo2 = criarGrupoTransporte();
        assertGrupoTransporte(codigo2);

        assertThat(codigo1, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        try {
            Long codigo = criarGrupoMoradia();

            GrupoDTO dto = business.buscarPorId(codigo);
            dto.codigo("");

            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o grupo."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo código é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNome() throws BusinessException {
        try {
            Long codigo = criarGrupoMoradia();

            GrupoDTO dto = business.buscarPorId(codigo);
            dto.nome("");

            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o grupo."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDescricao() throws BusinessException {
        try {
            Long codigo = criarGrupoMoradia();

            GrupoDTO dto = business.buscarPorId(codigo);
            dto.descricao("");

            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o grupo."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test
    public void alterarNome() throws BusinessException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);

        GrupoDTO dto = business.buscarPorId(codigo);
        dto.nome("Transporte");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Transporte"));
        assertThat(dto.descricao(), equalTo("Gastos gerais com moradia"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    @Test
    public void alterarDescricao() throws BusinessException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);

        GrupoDTO dto = business.buscarPorId(codigo);
        dto.descricao("Outra descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Moradia"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    @Test
    public void alterarNomeDescricao() throws BusinessException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);

        GrupoDTO dto = business.buscarPorId(codigo);
        dto.nome("Transporte");
        dto.descricao("Outra descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Transporte"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    @Test(expected = BusinessException.class)
    public void excluirException() throws BusinessException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir o grupo."));
            throw e;
        }
    }

    @Test
    public void excluir() throws BusinessException {
        Long codigo = criarGrupoMoradia();
        GrupoDTO dto = business.buscarPorId(codigo);

        business.excluir(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarPorIdException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarPorId(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar o grupo por id."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void buscarTodosException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarTodos();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar os grupos."));
            throw e;
        }
    }

    @Test
    public void buscarTodos() throws BusinessException {
        criarGrupoMoradia();
        criarGrupoTransporte();

        List<GrupoDTO> entities = business.buscarTodos();
        assertThat(entities.size(), equalTo(2));

        assertGrupoMoradia(entities.get(0));
        assertGrupoTransporte(entities.get(1));
    }

    private Long criarGrupoMoradia() throws BusinessException {
        GrupoDTO dto = grupoMoradia();
        Long codigo = business.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarGrupoMoradiaSemGrupos() throws BusinessException {
        GrupoDTO dto = grupoMoradiaSemGrupos();
        Long codigo = business.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarGrupoTransporte() throws BusinessException {
        GrupoDTO dto = grupoTransporte();
        Long codigo = business.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertGrupoMoradia(Long codigo) throws BusinessException {
        GrupoDTO dto = business.buscarPorId(codigo);
        assertGrupoMoradia(dto);
    }

    private void assertGrupoMoradiaSemGrupos(Long codigo) throws BusinessException {
        GrupoDTO dto = business.buscarPorId(codigo);
        assertGrupoMoradiaSemGrupos(dto);
    }

    private void assertGrupoTransporte(Long codigo) throws BusinessException {
        GrupoDTO dto = business.buscarPorId(codigo);
        assertGrupoTransporte(dto);
    }

    private void assertGrupoMoradia(GrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Moradia"));
        assertThat(dto.descricao(), equalTo("Gastos gerais com moradia"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    private void assertGrupoMoradiaSemGrupos(GrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Moradia"));
        assertThat(dto.descricao(), equalTo("Gastos gerais com moradia"));
        assertThat(dto.subGrupos().size(), equalTo(0));
    }

    private void assertGrupoTransporte(GrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Transporte"));
        assertThat(dto.descricao(), equalTo("Gastos gerais com transporte"));
        assertThat(dto.subGrupos().size(), equalTo(1));
        assertThat(dto.subGrupos().get(0), equalTo("Combustível"));
    }

    private void assertGrupoMoradiaSubGrupos(GrupoDTO dto) {
        assertThat(dto.subGrupos().size(), equalTo(2));
        assertThat(dto.subGrupos().get(0), equalTo("Aluguel"));
        assertThat(dto.subGrupos().get(1), equalTo("Internet"));
    }
}
