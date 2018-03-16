package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.ContaDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class ContaBusinessTest extends GenericTest {
    @Autowired
    private ContaBusiness contaBusiness;

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDiretorio() throws BusinessException, IOException {
        deletarDiretorioEArquivos();
        ContaDTO contaDTO = contaDTO();

        try {
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            ServiceException e1 = (ServiceException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Não foi possível buscar a conta."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNome() throws BusinessException {
        ContaDTO contaDTO = contaDTOSemNome();

        try {
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros, notNullValue());
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));

            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDescricao() throws BusinessException {
        ContaDTO contaDTO = contaDTOSemDescricao();

        try {
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros, notNullValue());
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo descrição é obrigatório."));

            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNomeEDescricao() throws BusinessException {
        ContaDTO contaDTO = contaDTOSemNomeEDescricao();

        try {
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros, notNullValue());
            assertThat(erros.size(), equalTo(2));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            assertThat(erros.get(1), equalTo("O campo descrição é obrigatório."));

            throw e;
        }
    }

    @Test
    public void criarUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();

        Long codigoConta = contaBusiness.criar(contaDTO);
        assertThat(codigoConta, notNullValue());
        assertTrue(codigoConta >= 0);
    }

    @Test(expected = BusinessException.class)
    public void criarDuasContasComNomeEDescricaoIguais() throws BusinessException {
        ContaDTO contaDTO = contaDTO();

        try {
            contaBusiness.criar(contaDTO);
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Conta duplicada."));

            throw e;
        }
    }

    @Test
    public void criarDuasContas() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        assertThat(codigoConta, notNullValue());
        assertTrue(codigoConta >= 0);

        ContaDTO conta2DTO = conta2DTO();
        Long codigoConta2 = contaBusiness.criar(conta2DTO);
        assertThat(codigoConta2, notNullValue());
        assertTrue(codigoConta2 >= 0);

        assertThat(codigoConta, not(equalTo(codigoConta2)));
    }
}
