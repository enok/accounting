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
    private SubGrupoBusiness subGrupoBusiness;

    @Test(expected = BusinessException.class)
    public void criarSemDiretorio() throws IOException, BusinessException {
        try {
            deletarDiretorioEArquivos();
            criarSubGrupoAluguel();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o subGrupo."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarSemNome() throws BusinessException {
        try {
            SubGrupoDTO subGrupoDTO = subGrupoAluguelSemNome();
            subGrupoBusiness.criar(subGrupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o subGrupo."));

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
            subGrupoBusiness.criar(subGrupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o subGrupo."));

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
            subGrupoBusiness.criar(subGrupoDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o subGrupo."));

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

            SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
            dto.codigo("");

            subGrupoBusiness.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o subGrupo."));

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

            SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
            dto.nome("");

            subGrupoBusiness.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o subGrupo."));

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

            SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
            dto.descricao("");

            subGrupoBusiness.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar o subGrupo."));

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

        SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
        dto.nome("Internet");
        subGrupoBusiness.atualizar(dto);

        dto = subGrupoBusiness.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio"));
    }

    @Test
    public void alterarDescricao() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);

        SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
        dto.descricao("Outra descrição");
        subGrupoBusiness.atualizar(dto);

        dto = subGrupoBusiness.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Aluguel"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
    }

    @Test
    public void alterarNomeDescricao() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);

        SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
        dto.nome("Internet");
        dto.descricao("Outra descrição");
        subGrupoBusiness.atualizar(dto);

        dto = subGrupoBusiness.buscarPorId(codigo);
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
    }

    @Test(expected = BusinessException.class)
    public void excluirException() throws BusinessException {
        try {
            subGrupoBusiness.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir o subGrupo."));
            throw e;
        }
    }

    @Test
    public void excluir() throws BusinessException {
        Long codigo = criarSubGrupoAluguel();
        SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);

        subGrupoBusiness.excluir(dto);

        dto = subGrupoBusiness.buscarPorId(codigo);
        assertThat(dto, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarPorIdException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            subGrupoBusiness.buscarPorId(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar o subGrupo por id."));
            throw e;
        }
    }


    @Test(expected = BusinessException.class)
    public void buscarTodosException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            subGrupoBusiness.buscarTodos();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar os subGrupos."));
            throw e;
        }
    }

    @Test
    public void buscarTodos() throws BusinessException {
        criarSubGrupoAluguel();
        criarSubGrupoInternet();

        List<SubGrupoDTO> entities = subGrupoBusiness.buscarTodos();
        assertThat(entities.size(), equalTo(2));

        assertSubGrupoAluguel(entities.get(0));
        assertSubGrupoInternet(entities.get(1));
    }

    private Long criarSubGrupoAluguel() throws BusinessException {
        SubGrupoDTO dto = subGrupoAluguel();
        Long codigo = subGrupoBusiness.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarSubGrupoInternet() throws BusinessException {
        SubGrupoDTO dto = subGrupoInternet();
        Long codigo = subGrupoBusiness.criar(dto);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertSubGrupoAluguel(Long codigo) throws BusinessException {
        SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
        assertSubGrupoAluguel(dto);
    }

    private void assertSubGrupoAluguel(SubGrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Aluguel"));
        assertThat(dto.descricao(), equalTo("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio"));
    }

    private void assertSubGrupoInternet(Long codigo) throws BusinessException {
        SubGrupoDTO dto = subGrupoBusiness.buscarPorId(codigo);
        assertSubGrupoInternet(dto);
    }

    private void assertSubGrupoInternet(SubGrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Serviço de internet fibra"));
    }
}
