package br.com.accounting.business.service;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.GrupoDTOMockFactory;
import br.com.accounting.core.exception.StoreException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class GrupoBusinessTest extends GenericTest {
    @Autowired
    private GrupoBusiness business;

    @Test(expected = StoreException.class)
    public void criarSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            criarGrupoMoradia();
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao salvar."));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarSemNome() throws StoreException, BusinessException, GenericException {
        try {
            GrupoDTO dto = GrupoDTOMockFactory.grupoMoradiaSemNome();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "nome");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarSemDescricao() throws StoreException, BusinessException, GenericException {
        try {
            GrupoDTO dto = GrupoDTOMockFactory.grupoMoradiaSemDescricao();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "descrição");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarSemGrupo() throws StoreException, BusinessException, GenericException {
        try {
            GrupoDTO dto = GrupoDTOMockFactory.grupoMoradiaSemGrupos();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "subGrupos");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarSemCamposObrigatorios() throws StoreException, BusinessException, GenericException {
        try {
            GrupoDTO dto = GrupoDTOMockFactory.grupoMoradiaSemCamposObrigatorios();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "nome", "descrição", "subGrupos");
        }
    }

    @Test
    public void criar() throws StoreException, BusinessException, GenericException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);
    }

    @Test(expected = ValidationException.class)
    public void criarDoisComNomesIguais() throws StoreException, BusinessException, GenericException {
        try {
            criarGrupoMoradia();
            criarGrupoMoradia();
        }
        catch (ValidationException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e;
            assertThat(e1.getMessage(), equalTo("Grupo duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDois() throws StoreException, BusinessException, GenericException {
        Long codigo1 = criarGrupoMoradia();
        assertGrupoMoradia(codigo1);

        Long codigo2 = criarGrupoTransporte();
        assertGrupoTransporte(codigo2);

        assertThat(codigo1, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarGrupoMoradia();
            GrupoDTO dto = business.buscarPorCodigo(codigo);
            dto.codigo("");
            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNome() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarGrupoMoradia();
            GrupoDTO dto = business.buscarPorCodigo(codigo);
            dto.nome("");
            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "nome");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDescricao() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarGrupoMoradia();
            GrupoDTO dto = business.buscarPorCodigo(codigo);
            dto.descricao("");
            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "descrição");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarGrupoMoradia();
            String codigoAnterior = String.valueOf(codigo);
            String codigoNovo = "10";
            GrupoDTO dtoBuscado = business.buscarPorCodigo(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test
    public void alterarNome() throws StoreException, BusinessException, GenericException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);

        GrupoDTO dto = business.buscarPorCodigo(codigo);
        dto.nome("Transporte");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertThat(dto.nome(), equalTo("Transporte"));
        assertThat(dto.descricao(), equalTo("Gastos gerais com moradia"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    @Test
    public void alterarDescricao() throws StoreException, BusinessException, GenericException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);

        GrupoDTO dto = business.buscarPorCodigo(codigo);
        dto.descricao("Outra descrição");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertThat(dto.nome(), equalTo("Moradia"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    @Test
    public void alterarNomeDescricao() throws StoreException, BusinessException, GenericException {
        Long codigo = criarGrupoMoradia();
        assertGrupoMoradia(codigo);

        GrupoDTO dto = business.buscarPorCodigo(codigo);
        dto.nome("Transporte");
        dto.descricao("Outra descrição");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertThat(dto.nome(), equalTo("Transporte"));
        assertThat(dto.descricao(), equalTo("Outra descrição"));
        assertGrupoMoradiaSubGrupos(dto);
    }

    @Test(expected = GenericException.class)
    public void excluirException() throws BusinessException, StoreException, GenericException {
        business.excluir(null);
    }

    @Test(expected = StoreException.class)
    public void excluirStoreException() throws IOException, BusinessException, GenericException, StoreException {
        try {
            Long codigo = criarGrupoMoradia();
            GrupoDTO dto = business.buscarPorCodigo(codigo);
            deletarDiretorioEArquivos();
            business.excluir(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao excluir."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void excluir() throws StoreException, BusinessException, GenericException {
        Long codigo = criarGrupoMoradia();
        GrupoDTO dto = business.buscarPorCodigo(codigo);

        business.excluir(dto);

        try {
            business.buscarPorCodigo(codigo);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test(expected = StoreException.class)
    public void buscarPorIdException() throws IOException, StoreException, BusinessException, GenericException {
        deletarDiretorioEArquivos();
        business.buscarPorCodigo(null);
    }

    @Test(expected = StoreException.class)
    public void buscarTodosException() throws IOException, StoreException, GenericException {
        deletarDiretorioEArquivos();
        business.buscarTodas();
    }

    @Test
    public void buscarTodos() throws StoreException, BusinessException, GenericException {
        criarGrupoMoradia();
        criarGrupoTransporte();

        List<GrupoDTO> entities = business.buscarTodas();
        assertThat(entities.size(), equalTo(2));

        assertGrupoMoradia(entities.get(0));
        assertGrupoTransporte(entities.get(1));
    }

    private Long criarGrupoMoradia() throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = GrupoDTOMockFactory.grupoMoradia();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarGrupoTransporte() throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = GrupoDTOMockFactory.grupoTransporte();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertGrupoMoradia(Long codigo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = business.buscarPorCodigo(codigo);
        assertGrupoMoradia(dto);
    }

    private void assertGrupoTransporte(Long codigo) throws StoreException, BusinessException, GenericException {
        GrupoDTO dto = business.buscarPorCodigo(codigo);
        assertGrupoTransporte(dto);
    }

    private void assertGrupoMoradia(GrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Moradia"));
        assertThat(dto.descricao(), equalTo("Gastos gerais com moradia"));
        assertGrupoMoradiaSubGrupos(dto);
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
