package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.MissingFieldException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static br.com.accounting.business.factory.ContaDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

public class ContaBusinessTest extends GenericTest {
    @Autowired
    private ContaBusiness contaBusiness;

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNome() throws BusinessException {
        ContaDTO contaDTO = contaDTOSemNome();

        Long codigoConta = null;
        try {
            codigoConta = contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            List<String> erros = e1.getErros();

            assertThat(erros, notNullValue());
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));

            throw e;
        }

        assertThat(codigoConta, notNullValue());
        assertTrue(codigoConta > 0);
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDescricao() throws BusinessException {
        ContaDTO contaDTO = contaDTOSemDescricao();

        Long codigoConta = null;
        try {
            codigoConta = contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            List<String> erros = e1.getErros();

            assertThat(erros, notNullValue());
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo descrição é obrigatório."));

            throw e;
        }

        assertThat(codigoConta, notNullValue());
        assertTrue(codigoConta > 0);
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNomeEDescricao() throws BusinessException {
        ContaDTO contaDTO = contaDTOSemNomeEDescricao();

        Long codigoConta = null;
        try {
            codigoConta = contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            List<String> erros = e1.getErros();

            assertThat(erros, notNullValue());
            assertThat(erros.size(), equalTo(2));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            assertThat(erros.get(1), equalTo("O campo descrição é obrigatório."));

            throw e;
        }

        assertThat(codigoConta, notNullValue());
        assertTrue(codigoConta > 0);
    }

    @Test
    public void criarUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();

        Long codigoConta = contaBusiness.criar(contaDTO);
        assertThat(codigoConta, notNullValue());
        assertTrue(codigoConta > 0);
    }

//    @Test
//    public void criarDuasContas() throws BusinessException {
//        ContaDTO contaDTO = contaDTO();
//
//        Long codigoConta = contaBusiness.criar(contaDTO);
//        assertThat(codigoConta, notNullValue());
//        assertTrue(codigoConta > 0);
//
//        Long codigoConta2 = contaBusiness.criar(contaDTO);
//        assertThat(codigoConta2, notNullValue());
//        assertTrue(codigoConta2 > 0);
//
//        assertThat(codigoConta, not(equalTo(codigoConta2)));
//    }
}
