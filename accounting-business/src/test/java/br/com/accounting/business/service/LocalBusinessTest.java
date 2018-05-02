package br.com.accounting.business.service;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.LocalDTOMockFactory;
import br.com.accounting.core.exception.StoreException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class LocalBusinessTest extends GenericTest {
    @Autowired
    private LocalBusiness business;

    @Test(expected = StoreException.class)
    public void criarUmLocalSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            LocalDTO dto = LocalDTOMockFactory.localDTOCarrefour();
            business.criar(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar os registros."));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmLocalSemNome() throws StoreException, BusinessException, GenericException {
        try {
            LocalDTO dto = LocalDTOMockFactory.localDTOCarrefourSemNome();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "nome");
        }
    }

    @Test
    public void criarUmLocal() throws StoreException, BusinessException, GenericException {
        Long codigo = criarLocalCarrefour();
        assertLocalCarrefour(codigo);
    }

    @Test(expected = ValidationException.class)
    public void criarDoisLocaisComNomesIguais() throws StoreException, BusinessException, GenericException {
        try {
            criarLocalCarrefour();
            criarLocalCarrefour();
        }
        catch (ValidationException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e;
            assertThat(e1.getMessage(), equalTo("Local duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDoisLocais() throws StoreException, BusinessException, GenericException {
        Long codigo = criarLocalCarrefour();
        Long codigo2 = criarLocalAmericanas();

        assertThat(codigo, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarLocalAmericanas();
            LocalDTO dtoBuscado = business.buscarPorId(codigo);
            dtoBuscado.codigo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNome() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarLocalAmericanas();
            LocalDTO dtoBuscado = business.buscarPorId(codigo);
            dtoBuscado.nome(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "nome");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarLocalAmericanas();
            String codigoAnterior = String.valueOf(codigo);
            String codigoNovo = "10";
            LocalDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "código");
        }
    }

    @Test
    public void alterarNomeDoLocal() throws StoreException, BusinessException, GenericException {
        Long codigo = criarLocalAmericanas();

        LocalDTO dtoBuscado = assertLocalAmericanas(codigo);

        dtoBuscado.nome("Americanas 2");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Americanas 2"));
    }

    @Test(expected = BusinessException.class)
    public void excluirUmLocalException() throws BusinessException, StoreException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluirUmLocal() throws StoreException, BusinessException, GenericException {
        Long codigo = criarLocalCarrefour();
        LocalDTO dtoBuscado = business.buscarPorId(codigo);

        business.excluir(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarLocalPorIdException() throws BusinessException, IOException {
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
    public void buscarLocaisException() throws BusinessException, IOException {
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
    public void buscarLocais() throws StoreException, BusinessException, GenericException {
        criarLocalCarrefour();
        criarLocalAmericanas();

        List<LocalDTO> dtos = business.buscarTodas();
        assertThat(dtos.size(), equalTo(2));

        assertLocalAmericanas(dtos.get(0));
        assertLocalCarrefour(dtos.get(1));
    }

    private Long criarLocalCarrefour() throws StoreException, BusinessException, GenericException {
        LocalDTO dto = LocalDTOMockFactory.localDTOCarrefour();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarLocalAmericanas() throws StoreException, BusinessException, GenericException {
        LocalDTO dto = LocalDTOMockFactory.localAmericanas();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertLocalCarrefour(Long codigo) throws BusinessException {
        LocalDTO dtoBuscado = business.buscarPorId(codigo);
        assertLocalCarrefour(dtoBuscado);
    }

    private void assertLocalCarrefour(LocalDTO dto) {
        assertThat(dto.nome(), equalTo("Carrefour"));
    }

    private LocalDTO assertLocalAmericanas(Long codigo) throws BusinessException {
        LocalDTO dtoBuscado = business.buscarPorId(codigo);
        return assertLocalAmericanas(dtoBuscado);
    }

    private LocalDTO assertLocalAmericanas(LocalDTO dto) {
        assertThat(dto.nome(), equalTo("Americanas"));
        return dto;
    }
}
