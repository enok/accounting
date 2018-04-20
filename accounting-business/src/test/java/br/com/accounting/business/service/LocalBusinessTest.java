package br.com.accounting.business.service;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.LocalDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class LocalBusinessTest extends GenericTest {
    @Autowired
    private LocalBusiness business;

    @Test(expected = BusinessException.class)
    public void criarUmLocalSemDiretorio() throws BusinessException, IOException {
        try {
            deletarDiretorioEArquivos();
            LocalDTO dto = localDTOCarrefour();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreation(e);
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmLocalSemNome() throws BusinessException {
        try {
            LocalDTO dto = localDTOCarrefourSemNome();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "nome");
        }
    }

    @Test
    public void criarUmLocal() throws BusinessException {
        Long codigo = criarLocalCarrefour();
        assertLocalCarrefour(codigo);
    }

    @Test(expected = BusinessException.class)
    public void criarDoisLocaisComNomesIguais() throws BusinessException {
        try {
            criarLocalCarrefour();
            criarLocalCarrefour();
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Local duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDoisLocais() throws BusinessException {
        Long codigo = criarLocalCarrefour();
        Long codigo2 = criarLocalAmericanas();

        assertThat(codigo, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
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
    public void alterarSemNome() throws BusinessException {
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
    public void alteracaoNaoPermitidaDoCodigo() throws BusinessException {
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
    public void alterarNomeDoLocal() throws BusinessException {
        Long codigo = criarLocalAmericanas();

        LocalDTO dtoBuscado = assertLocalAmericanas(codigo);

        dtoBuscado.nome("Americanas 2");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Americanas 2"));
    }

    @Test(expected = BusinessException.class)
    public void excluirUmLocalException() throws BusinessException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluirUmLocal() throws BusinessException {
        Long codigo = criarLocalCarrefour();
        LocalDTO dtoBuscado = business.buscarPorId(codigo);

        business.excluir(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarLocalPorIdException() throws IOException, BusinessException {
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
    public void buscarLocaisException() throws IOException, BusinessException {
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
    public void buscarLocais() throws BusinessException {
        criarLocalCarrefour();
        criarLocalAmericanas();

        List<LocalDTO> dtos = business.buscarTodas();
        assertThat(dtos.size(), equalTo(2));

        assertLocalAmericanas(dtos.get(0));
        assertLocalCarrefour(dtos.get(1));
    }

    private Long criarLocalCarrefour() throws BusinessException {
        LocalDTO dto = localDTOCarrefour();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarLocalAmericanas() throws BusinessException {
        LocalDTO dto = localAmericanas();
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
