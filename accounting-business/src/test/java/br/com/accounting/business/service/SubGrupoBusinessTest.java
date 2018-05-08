package br.com.accounting.business.service;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.SubGrupoDTOMockFactory;
import br.com.accounting.core.exception.StoreException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class SubGrupoBusinessTest extends GenericTest {
    @Autowired
    private SubGrupoBusiness business;

    @Test(expected = StoreException.class)
    public void criarSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            criarSubGrupoAluguel();
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao salvar."));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarSemNome() throws StoreException, BusinessException, GenericException {
        try {
            SubGrupoDTO dto = SubGrupoDTOMockFactory.subGrupoAluguelSemNome();
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
            SubGrupoDTO dto = SubGrupoDTOMockFactory.subGrupoAluguelSemDescricao();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "descrição");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarSemCamposObrigatorios() throws StoreException, BusinessException, GenericException {
        try {
            SubGrupoDTO dto = SubGrupoDTOMockFactory.subGrupoAluguelSemCamposObrigatorios();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "nome", "descrição");
        }
    }

    @Test
    public void criar() throws StoreException, BusinessException, GenericException {
        Long codigo = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo);
    }

    @Test(expected = ValidationException.class)
    public void criarDoisComNomesIguais() throws StoreException, BusinessException, GenericException {
        try {
            criarSubGrupoAluguel();
            criarSubGrupoAluguel();
        }
        catch (ValidationException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e;
            assertThat(e1.getMessage(), equalTo("SubGrupo duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDois() throws StoreException, BusinessException, GenericException {
        Long codigo1 = criarSubGrupoAluguel();
        assertSubGrupoAluguel(codigo1);

        Long codigo2 = criarSubGrupoInternet();
        assertSubGrupoInternet(codigo2);

        assertThat(codigo1, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarSubGrupoAluguel();
            SubGrupoDTO dto = business.buscarPorId(codigo);
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
            Long codigo = criarSubGrupoAluguel();
            SubGrupoDTO dto = business.buscarPorId(codigo);
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
            Long codigo = criarSubGrupoAluguel();
            SubGrupoDTO dto = business.buscarPorId(codigo);
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
            Long codigo = criarSubGrupoAluguel();
            String codigoAnterior = String.valueOf(codigo);
            String codigoNovo = "10";
            SubGrupoDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "código");
        }
    }

    @Test
    public void alterarNome() throws StoreException, BusinessException, GenericException {
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
    public void alterarDescricao() throws StoreException, BusinessException, GenericException {
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
    public void alterarNomeDescricao() throws StoreException, BusinessException, GenericException {
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
    public void excluirException() throws BusinessException, StoreException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluir() throws StoreException, BusinessException, GenericException {
        Long codigo = criarSubGrupoAluguel();
        SubGrupoDTO dto = business.buscarPorId(codigo);

        business.excluir(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto, nullValue());
    }

    @Test(expected = StoreException.class)
    public void buscarPorIdException() throws IOException, StoreException, BusinessException {
        deletarDiretorioEArquivos();
        business.buscarPorId(null);
    }


    @Test(expected = StoreException.class)
    public void buscarTodosException() throws IOException, StoreException, BusinessException {
        deletarDiretorioEArquivos();
        business.buscarTodas();
    }

    @Test
    public void buscarTodos() throws StoreException, BusinessException, GenericException {
        criarSubGrupoAluguel();
        criarSubGrupoInternet();

        List<SubGrupoDTO> entities = business.buscarTodas();
        assertThat(entities.size(), equalTo(2));

        assertSubGrupoAluguel(entities.get(0));
        assertSubGrupoInternet(entities.get(1));
    }

    private Long criarSubGrupoAluguel() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO dto = SubGrupoDTOMockFactory.subGrupoAluguel();
        List<Long> codigos = business.criar(dto);

        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarSubGrupoInternet() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO dto = SubGrupoDTOMockFactory.subGrupoInternet();
        List<Long> codigos = business.criar(dto);

        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertSubGrupoAluguel(Long codigo) throws StoreException, BusinessException {
        SubGrupoDTO dto = business.buscarPorId(codigo);
        assertSubGrupoAluguel(dto);
    }

    private void assertSubGrupoAluguel(SubGrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Aluguel"));
        assertThat(dto.descricao(), equalTo("Aluguel pago todo mês, incluindo no valor iptu, seguro e condomínio"));
    }

    private void assertSubGrupoInternet(Long codigo) throws StoreException, BusinessException {
        SubGrupoDTO dto = business.buscarPorId(codigo);
        assertSubGrupoInternet(dto);
    }

    private void assertSubGrupoInternet(SubGrupoDTO dto) {
        assertThat(dto.nome(), equalTo("Internet"));
        assertThat(dto.descricao(), equalTo("Serviço de internet fibra"));
    }
}
