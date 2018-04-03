package br.com.accounting.business.service;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.SubGrupoDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class SubGrupoBusinessTest extends GenericTest {
    @Autowired
    private SubGrupoBusiness business;

    @Test(expected = BusinessException.class)
    public void criarSemDiretorio() throws IOException, BusinessException {
        try {
            deletarDiretorioEArquivos();
            criarSubGrupoAluguel();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarSemNome() throws BusinessException {
        try {
            SubGrupoDTO subGrupoDTO = subGrupoAluguelSemNome();
            business.criar(subGrupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar."));

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
            SubGrupoDTO subGrupoDTO = subGrupoAluguelSemDescricao();
            business.criar(subGrupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar."));

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
            SubGrupoDTO subGrupoDTO = subGrupoAluguelSemNomeDescricao();
            business.criar(subGrupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(2));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            assertThat(erros.get(1), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test
    public void criar() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);
    }

    @Test(expected = BusinessException.class)
    public void criarDoisComNomesIguais() throws BusinessException {
        try {
            criarSubGrupoAluguel();
            criarSubGrupoAluguel();
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("SubGrupo duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDois() throws BusinessException {
        Long codigo1 = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo1);

        Long codigo2 = criarSubGrupoInternet();
        assertSubGrupoInternet(codigo2);

        assertThat(codigo1, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        try {
            Long codigo = criarSubGrupoAluguel();

            SubGrupoDTO dto = business.buscarPorId(codigo);
            dto.codigo("");

            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));

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
            Long codigo = criarSubGrupoAluguel();

            SubGrupoDTO dto = business.buscarPorId(codigo);
            dto.nome("");

            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));

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
            Long codigo = criarSubGrupoAluguel();

            SubGrupoDTO dto = business.buscarPorId(codigo);
            dto.descricao("");

            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test
    public void alterarNome() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);

        SubGrupoDTO dto = business.buscarPorId(codigo);
        dto.nome("Internet");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio"));
    }

    @Test
    public void alterarDescricao() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);

        SubGrupoDTO dto = business.buscarPorId(codigo);
        dto.descricao("Outra descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Aluguel"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
    }

    @Test
    public void alterarNomeDescricao() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);

        SubGrupoDTO dto = business.buscarPorId(codigo);
        dto.nome("Internet");
        dto.descricao("Outra descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
    }

    @Test(expected = BusinessException.class)
    public void excluirException() throws BusinessException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluir() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        SubGrupoDTO dto = business.buscarPorId(codigo);

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
            assertThat(e.getMessage(), equalTo("Não foi possível buscar por id."));
            throw e;
        }
    }


    @Test(expected = BusinessException.class)
    public void buscarTodosException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarTodas();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar todas."));
            throw e;
        }
    }

    @Test
    public void buscarTodos() throws BusinessException {
        criarSubGrupoAluguel();
        criarSubGrupoInternet();

        List<SubGrupoDTO> entities = business.buscarTodas();
        assertThat(entities.size(), equalTo(2));

        assertSubGrupoAluguel(entities.get(0));
        assertSubGrupoInternet(entities.get(1));
    }

    private Long criarSubGrupoAluguel() throws BusinessException {
        SubGrupoDTO dto = subGrupoAluguel();
        Long codigo = business.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarSubGrupoInternet() throws BusinessException {
        SubGrupoDTO dto = subGrupoInternet();
        Long codigo = business.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertSubGrupoAluguel(Long codigo) throws BusinessException {
        SubGrupoDTO dto = business.buscarPorId(codigo);
        assertSubGrupoAluguel(dto);
    }

    private void assertSubGrupoAluguel(SubGrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Aluguel"));
        assertThat(dto.descricao(), equalTo("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio"));
    }

    private void assertSubGrupoInternet(Long codigo) throws BusinessException {
        SubGrupoDTO dto = business.buscarPorId(codigo);
        assertSubGrupoInternet(dto);
    }

    private void assertSubGrupoInternet(SubGrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Serviço de internet fibra"));
    }
}
