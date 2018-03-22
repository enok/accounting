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
        assertTrue(codigoConta >= 0);

        ContaDTO conta2DTO = conta2DTO();
        Long codigoConta2 = contaBusiness.criar(conta2DTO);
        assertTrue(codigoConta2 >= 0);

        assertThat(codigoConta, not(equalTo(codigoConta2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarNomeSemNome() throws BusinessException {
        ContaDTO contaDTO = conta2DTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        ContaDTO contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        contaDTOBuscada.nome(null);
        contaBusiness.atualizar(contaDTOBuscada);
    }

    @Test(expected = BusinessException.class)
    public void alterarDescricaoSemDescricao() throws BusinessException {
        ContaDTO contaDTO = conta2DTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        ContaDTO contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        contaDTOBuscada.descricao(null);
        contaBusiness.atualizar(contaDTOBuscada);
    }

    @Test(expected = BusinessException.class)
    public void alterarNomeEDescricaoSemNomeEDescricao() throws BusinessException {
        ContaDTO contaDTO = conta2DTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        ContaDTO contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        contaDTOBuscada.nome(null);
        contaDTOBuscada.descricao(null);
        contaBusiness.atualizar(contaDTOBuscada);
    }

    @Test
    public void alterarNomeDaConta() throws BusinessException {
        ContaDTO contaDTO = conta2DTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        assertTrue(codigoConta >= 0);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        assertThat(contaDTOBuscada.nome(), equalTo("Enok"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));

        contaDTOBuscada.nome("Carol");
        contaBusiness.atualizar(contaDTOBuscada);
        contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        assertThat(contaDTOBuscada.nome(), equalTo("Carol"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test
    public void alterarDescricaoDaConta() throws BusinessException {
        ContaDTO contaDTO = conta2DTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        assertTrue(codigoConta >= 0);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        assertThat(contaDTOBuscada.nome(), equalTo("Enok"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));

        contaDTOBuscada.descricao("Valor separado para a Carol");
        contaBusiness.atualizar(contaDTOBuscada);
        contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        assertThat(contaDTOBuscada.nome(), equalTo("Enok"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test
    public void alterarNomeEDescricao() throws BusinessException {
        ContaDTO contaDTO = conta2DTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        assertTrue(codigoConta >= 0);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        assertThat(contaDTOBuscada.nome(), equalTo("Enok"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));

        contaDTOBuscada.nome("Carol");
        contaDTOBuscada.descricao("Valor separado para a Carol");
        contaBusiness.atualizar(contaDTOBuscada);
        contaDTOBuscada = contaBusiness.buscarContaPorId(codigoConta);

        assertThat(contaDTOBuscada.nome(), equalTo("Carol"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test(expected = BusinessException.class)
    public void buscarContasException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();

        try {
            contaBusiness.buscarContas();
        }
        catch (BusinessException e) {
            ServiceException e1 = (ServiceException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Não foi possível buscar as contas."));
            throw e;
        }
    }

    @Test
    public void buscarContas() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        List<ContaDTO> contasDTO = contaBusiness.buscarContas();

        assertThat(contasDTO.size(), equalTo(1));

        ContaDTO contaDTOBuscada = contasDTO.get(0);
        assertThat(contaDTOBuscada.nome(), equalTo("Salário"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Salário mensal recebido pela Sysmap"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test(expected = BusinessException.class)
    public void adicionarCreditoEmUmaContaSemDiretorio() throws BusinessException, IOException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        ContaDTO contaDTOBuscado = contaBusiness.buscarContas().get(0);

        deletarDiretorioEArquivos();

        try {
            contaBusiness.adicionarCredito(contaDTOBuscado, "500,00");
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível adicionar crédito à conta."));
            throw e;
        }
    }

    @Test
    public void adicionarCreditoEmUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContas().get(0);

        contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        contaDTOBuscada = contaBusiness.buscarContas().get(0);

        assertThat(contaDTOBuscada.saldo(), equalTo("500.0"));
    }

    @Test
    public void adicionarVariosCreditosEmUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContas().get(0);

        contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        contaDTOBuscada = contaBusiness.buscarContas().get(0);

        contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        contaDTOBuscada = contaBusiness.buscarContas().get(0);

        assertThat(contaDTOBuscada.saldo(), equalTo("1000.0"));
    }

    @Test(expected = BusinessException.class)
    public void adicionarDebitoEmUmaContaSemDiretorio() throws BusinessException, IOException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        ContaDTO contaDTOBuscado = contaBusiness.buscarContas().get(0);

        deletarDiretorioEArquivos();

        try {
            contaBusiness.adicionarDebito(contaDTOBuscado, "100,00");
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível adicionar débito à conta."));
            throw e;
        }
    }

    @Test
    public void adicionarDebitoEmUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContas().get(0);

        contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        contaDTOBuscada = contaBusiness.buscarContas().get(0);

        assertThat(contaDTOBuscada.saldo(), equalTo("-100.0"));
    }

    @Test
    public void adicionarVariosDebitosEmUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        ContaDTO contaDTOBuscada = contaBusiness.buscarContas().get(0);

        contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        contaDTOBuscada = contaBusiness.buscarContas().get(0);

        contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        contaDTOBuscada = contaBusiness.buscarContas().get(0);

        assertThat(contaDTOBuscada.saldo(), equalTo("-200.0"));
    }

    @Test(expected = BusinessException.class)
    public void excluirUmaContaException() throws BusinessException {
        try {
            contaBusiness.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir a conta."));
            throw e;
        }
    }

    @Test
    public void excluirUmaConta() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        contaBusiness.criar(contaDTO);

        List<ContaDTO> contasDTO = contaBusiness.buscarContas();
        ContaDTO contaDTOBuscada = contasDTO.get(0);
        assertThat(contasDTO.size(), equalTo(1));

        contaBusiness.excluir(contaDTOBuscada);

        contasDTO = contaBusiness.buscarContas();
        assertThat(contasDTO.size(), equalTo(0));
    }

    @Test(expected = BusinessException.class)
    public void buscarContaPorIdException() throws BusinessException {
        try {
            contaBusiness.buscarContaPorId(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar a conta por id."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraException() throws BusinessException {
        try {
            contaBusiness.transferir(null, null, null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível tranferir o valor entre as contas."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraSaldoInsuficiente() throws BusinessException {
        ContaDTO contaSalario = contaDTO();
        Long contaSalarioId = contaBusiness.criar(contaSalario);
        contaBusiness.adicionarCredito(contaSalario, "500.0");
        contaSalario = contaBusiness.buscarContaPorId(contaSalarioId);

        ContaDTO contaEnok = conta2DTO();
        Long contaEnokId = contaBusiness.criar(contaEnok);
        contaEnok = contaBusiness.buscarContaPorId(contaEnokId);

        try {
            contaBusiness.transferir(contaSalario, contaEnok, "600.0");
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), equalTo("Saldo insuficiente."));
            throw e;
        }
    }

    @Test
    public void transferirSaldoDeUmaContaParaOutra() throws BusinessException {
        ContaDTO contaSalario = contaDTO();
        Long contaSalarioId = contaBusiness.criar(contaSalario);
        contaBusiness.adicionarCredito(contaSalario, "1000.0");
        contaSalario = contaBusiness.buscarContaPorId(contaSalarioId);

        ContaDTO contaEnok = conta2DTO();
        Long contaEnokId = contaBusiness.criar(contaEnok);
        contaEnok = contaBusiness.buscarContaPorId(contaEnokId);

        contaBusiness.transferir(contaSalario, contaEnok, "600.0");

        contaSalario = contaBusiness.buscarContaPorId(contaSalarioId);
        assertThat(contaSalario.saldo(), equalTo("400.0"));

        contaEnok = contaBusiness.buscarContaPorId(contaEnokId);
        assertThat(contaEnok.saldo(), equalTo("600.0"));
    }
}
